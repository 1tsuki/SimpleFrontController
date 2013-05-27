<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<div id="errorMessages">
<% ArrayList<String> errorMessage = (ArrayList<String>) session.getAttribute("errorMessage");%>
<% if (errorMessage != null) {%>
    <%session.removeAttribute("errorMessages");%>
    <ul>
    <%for (String message : errorMessage) {%>
        <li class="errorMessage"><%=message%></li>
    <% } %>
    </ul>
<% } %>
</div>