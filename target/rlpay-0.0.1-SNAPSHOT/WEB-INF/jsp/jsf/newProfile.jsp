<%-- 
    Document   : newProfile
    Created on : Nov 5, 2018, 10:17:33 AM
    Author     : Paul.Allen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <%@ include file="common/header.html" %>
    <%@ include file="common/menu.html" %>

<!--  ++++++++++++++++++++++++ Page Content +++++++++++++++++++++++++++ -->

        <%@ include file="view/profile/menu.html" %>
        <%@ include file="view/profile/profile_form.html" %>
        <%@ include file="view/profile/billing_form.html" %>
        <%@ include file="view/profile/payment_form.html" %>
        
<!-- ++++++++++++++++++++++ Footer Begins Here ++++++++++++++++++++++++ -->
        <%@ include file="common/footer.html" %>
<!-- +++++++++++++++++++++++ Footer Ends Here +++++++++++++++++++++++++ -->
</html>
