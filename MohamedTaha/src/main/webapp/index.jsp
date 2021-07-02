<%@ page import="data.Smile" %>
<%@ page import="smile.data.DataFrame" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Java Final Project</title>
</head>
<body>
<h1><%= "Hello World! This is like a starting page" %>
</h1>
<br/>
<h1><%= getTrainDataSummery() %>
</h1>



<br/>
<img src="resources/visuals/b2.jpg" alt="Picture2">
<br/>
<a href="hello-servlet">Hello Servlet</a>
<br/>
<a href="api/hello-world">Hello-world</a>
<br/>


</body>
</html>
<%!
    public String getTrainDataSummery(DataFrame data) {
    DataFrame summary = data.summary ();
    DataFrame selectedColumns = data.select ("battery_power", "n_cores");
    /**System.out.println (summary);
     System.out.println (data.slice (0, 5));
     System.out.println (data.select (5));
     System.out.println (selectedColumns.slice (0, 10));**/
    String theString = summary.toString() + data.slice (0, 5).toString() +data.select (5).toString() + selectedColumns.slice (0, 10);
    return theString;}
%>