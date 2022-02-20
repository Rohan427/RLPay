<%--
    Document   : error
    Created on : Nov 5, 2018, 10:17:33 AM
    Author     : Paul.Allen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <%@ include file="header.html" %>
    <%@ include file="menu.html" %>

<!-- ++++++++++++++++++++++ Begin Page Content ++++++++++++++++++++++++ -->

        <jsp:useBean id="errorMsg"
                     scope="session"
                     class="error.ErrorBean"
        />

        <div class="div_header">
            <h1>Error</h1>
        </div>
        <div class="res_div_header">
                Error Data
        </div>
        <div class="res_div_main">
            <p>
                <h3>Caller:</h3> <c:out value="${errorMsg.callingPage}" />
            </p>
            <p>
                <h3>Operation:</h3> <c:out value="${errorMsg.operation}" />
            </p>
            <p>
                <h3>Error Type:</h3> <c:out value="${errorMsg.errorStrings}" />
            </p>
            <p>
                <h3>Description:</h3> <c:out value="${errorMsg.errorMessage}" />
            </p>
            <p>
                <h3>Stack Trace:</h3> <c:out value="${errorMsg.stackTrace}" />
            </p>
            <p>
                <h3>SQL Error:</h3> <c:out value="${errorMsg.sqlError}" />
            </p>
            <p>
                <h3>Exception:</h3> <c:out value="${errorMsg.javaException}" />
            </p>
        </div>
        <div class="res_div_main">
            &nbsp;<br />
        </div>
        <p>
            <small>&nbsp;</small>
        </p>
<!-- ++++++++++++++++++++++ Footer Begins Here ++++++++++++++++++++++++ -->
        <%@ include file="/common/footer.html" %>
<!-- +++++++++++++++++++++++ Footer Ends Here +++++++++++++++++++++++++ -->
</html>
