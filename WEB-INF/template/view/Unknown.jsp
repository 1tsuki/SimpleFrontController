<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>

    <div id="content">
        <section class="notification">
            <h1>お探しのページは見つかりませんでした。</h1>
            <p>ご迷惑をお掛けして申し訳ありません。お客様の指定されたページは見つかりませんでした。<br>お手数ですが、<a href="<% v.getPath("/Index"); %>">トップページ</a>にお戻り下さい。</p>
        </section>
    </div>
    
    <section id="sidebar">
        <aside>
          <h2>広告</h2>
          <img alt="広告" src="<% v.getPath("/asset/img/ad.jpg"); %>">
        </aside>
    </section>
	
<%@ include file="/WEB-INF/template/include/Footer.jsp" %>