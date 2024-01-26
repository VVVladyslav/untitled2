package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("UTF-8");

        String timeParamm = httpServletRequest.getParameter("timezone");
        if (timeParamm == null){
            timeParamm = "UTC";
        }
        String timeParam = timeParamm.replace("UTC ", "UTC+");
        timeParam = timeParamm.replace("UTC", "GMT");
        TimeZone timeZone;

        timeZone = TimeZone.getTimeZone(timeParam);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(timeZone);
        String currentTime = dateFormat.format(currentDate);

        httpServletResponse.getWriter().write("<h1>Current Time:</h1>");
        httpServletResponse.getWriter().write("<p>" + currentTime + "</p>");
        httpServletResponse.getWriter().close();
    }
}
