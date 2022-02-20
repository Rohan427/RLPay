<%--
    Document   : login
    Created on : Feb 9, 2022, 10:03:29 AM
    Author     : Paul G. Allen <pgallen@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<title>First Web Application</title>
</head>

<body>
    <font color="red">${errorMessage}</font>
    <form method="post">
        Name : <input type="text" name="name" />
        Password : <input type="password" name="password" />
        <input type="submit" />
    </form>
</body>

</html>
