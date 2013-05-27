<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.astrider.sfc.helper.StringUtils"%>
<%  
    String title = getServletContext().getServletContextName();
    String pageTitle = (String) session.getAttribute("pageTitle"); // this parameter will be removed at page footer
    if (StringUtils.isNotEmpty(pageTitle)) {
       title = title + " | " + pageTitle;
    }
%>
<%= title %>