package com.example.Project1;

import data.Smile;
import smile.data.DataFrame;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World! + ";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<img src=\"resources/visuals/b2.jpg\" alt=\"Picture2\">");

        out.println("<h1>" + message + "</h1>");
        out.println("<h1>" + printing() + "</h1>");

        out.println("</body></html>");
    }
public  String printing(){
    return "Printing from a method from the Hello-servlet Class";
}
    public void destroy() {
    }

}