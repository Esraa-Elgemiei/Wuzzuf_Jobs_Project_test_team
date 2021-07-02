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


<h1><% Smile s = new Smile ();
        DataFrame trainData = s.readCSV (s.trainPath);
        DataFrame testData = s.readCSV (s.testPath);
    System.out.println(s.getTrainDataSummery (trainData));
%> </h1>

<br/>
<img src="resources/visuals/b2.jpg" alt="Picture2">
<br/>
<a href="hello-servlet">Hello Servlet</a>
<br/>
<a href="api/hello-world">Hello-world</a>
<br/>


</body>
</html>