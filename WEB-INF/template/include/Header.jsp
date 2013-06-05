<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.astrider.sfc.lib.helper.StringUtils"%>
<%@ page import="com.astrider.sfc.lib.helper.ViewUtils"%>
<%	
    request.setCharacterEncoding("UTF-8");
    ViewUtils v = new ViewUtils(pageContext, session, request);
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title><% v.getTitle(); %></title>
    <link href="<%v.getPath("/asset/css/normalize.css");%>" rel="stylesheet" type="text/css">
    <link href="<%v.getCssPath();%>" rel="stylesheet" type="text/css">
  </head>

  <body>
    <img alt="背景画像" src="<%v.getPath("/asset/img/topimage.jpg");%>" class="background-image">

    <header>
      <div id="header">
        <section id="header-lower">
          <div class="header-inner">
            <div class="header-logo">
              <a href="<%v.getPath("/Index");%>">santé</a><p>-自炊を楽しむすべての人に、手軽で健康的な料理生活を-</p>
            </div>
          </div>
        </section>
      </div>
      <div id="header-lower-dummy"></div>
    </header>
    
    <div id="wrapper">