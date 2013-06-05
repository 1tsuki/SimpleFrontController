package com.astrider.sfc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * Front Command
 *
 * 概要
 *  スクラッチのservletに相当。doGet,doPostメソッドに加えて、redirectやforwardを簡易化するメソッド等を用意。
 *
 * 機能
 *  主要機能
 *      ・doGet()    標準ではUnknown.jspにforwardする。Overrideすべし。
 *      ・doPost()   標準ではUnknown.jspにforwardする。Overrideすべし。
 *
 *  副次機能
 *      ・addError()     表示するエラー文字列を登録
 *      ・hasError()     エラーの登録有無を確認
 *      ・showError()    エラーが存在した場合、指定したUrlにredirect
 *      ・render()       jspを描画、引数なしで自動的に名前で対応するjspを呼び出し
 *      ・redirect()     requestDispatcherを実行
 */

public abstract class Command {
    protected static final String VIEW_BASEPATH = "/WEB-INF/template/view";
    protected HttpServletRequest  request;
    protected HttpServletResponse response;
    protected ServletContext      context;
    protected ArrayList<String>   errorMessage  = new ArrayList<String>();

    public void init(HttpServletRequest request, HttpServletResponse response,
            ServletContext context) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        this.request = request;
        this.response = response;
        this.context = context;
    }

    protected void doGet() throws ServletException, IOException {
        redirect("/Unknown");
    }

    protected void doPost() throws ServletException, IOException {
        redirect("/Unknown");
    }

    protected void addError(String message) {
        errorMessage.add(message);
    }

    protected boolean hasError() {
        return 0 < errorMessage.size();
    }

    protected boolean showError(String redirectTarget) throws IOException {
        if (hasError()) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", errorMessage);
            redirect(redirectTarget);
            return true;
        }
        return false;
    }

    protected void render(String target) throws ServletException, IOException {
        RequestDispatcher rd = context.getRequestDispatcher(VIEW_BASEPATH + target);
        rd.forward(request, response);
    }

    protected void render() throws ServletException, IOException {
        render(request.getServletPath() + ".jsp");
    }

    protected void redirect(String target) throws IOException {
        String paramString = request.getContextPath() + target;
        response.sendRedirect(paramString);
    }
}
