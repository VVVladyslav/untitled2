package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String timeParamm = httpRequest.getParameter("timezone");
        timeParamm = timeParamm.replace("UTC ", "").trim();

        if (!isValidTimezone(timeParamm)) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isValidTimezone(String timezone) {
        try {
            if (!timezone.matches("\\d+") || Integer.parseInt(timezone) > 23) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @WebServlet(value = "/time")
    public static class TimeServlet extends HttpServlet {

        protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
                throws IOException {
            httpServletResponse.setContentType("text/html");
            httpServletResponse.setCharacterEncoding("UTF-8");

            String timeParamm = httpServletRequest.getParameter("timezone");
            TimeZone timeZone;
            String timeParam = timeParamm.replace("UTC ", "");

            timeZone = TimeZone.getTimeZone("GMT+" + timeParam);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            dateFormat.setTimeZone(timeZone);
            String currentTime = dateFormat.format(currentDate);

            httpServletResponse.getWriter().write("<h1>Current Time:</h1>");
            httpServletResponse.getWriter().write("<p>" + currentTime + "</p>");
            httpServletResponse.getWriter().close();
        }
    }
}