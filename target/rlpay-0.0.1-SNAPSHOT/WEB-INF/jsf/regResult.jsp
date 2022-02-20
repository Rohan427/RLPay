<%-- 
    Document   : regResult
    Created on : Nov 5, 2018, 10:17:33 AM
    Author     : Paul.Allen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <%@ include file="/common/header.html" %>
    <%@ include file="/common/menu.html" %>

<!-- ++++++++++++++++++++++ Page Content ++++++++++++++++++++++++ -->

<c:choose>
    <c:when test="${registered eq 'registered'}" >
        <%@ include file="/view/login/reg_failed.html" %>
    </c:when>
    <c:when test="${registered eq 'invalid'}" >
        <%@ include file="/view/login/del_invalid.html" %>
    </c:when>
    <c:when test="${registered eq 'deleted'}" >
        <%@ include file="/view/login/del_deleted.html" %>
    </c:when>
    <c:when test="${registered eq 'failed'}" >
        <%@ include file="/view/login/del_failed.html" %>
    </c:when>
    <c:otherwise>
        <%@ include file="/view/login/reg_successful.html" %>
    </c:otherwise>
</c:choose>
        
<!-- ++++++++++++++++++++++ Footer Begins Here ++++++++++++++++++++++++ -->
        <%@ include file="/common/footer.html" %>
<!-- +++++++++++++++++++++++ Footer Ends Here +++++++++++++++++++++++++ -->
</html>
