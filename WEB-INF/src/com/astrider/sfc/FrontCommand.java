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

public abstract class FrontCommand {
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

    protected void processGet() throws ServletException, IOException {
        redirect("/Unknown");
    }

    protected void processPost() throws ServletException, IOException {
        redirect("/Unknown");
    }

    protected void registerError(String message) {
        errorMessage.add(message);
    }

    protected boolean hasError() {
        return 0 < errorMessage.size();
    }

    protected boolean displayError(String previousCommand) throws IOException {
        if (hasError()) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", errorMessage);
            redirect(previousCommand);
            return true;
        }
        return false;
    }

    protected void render(String target) throws ServletException, IOException {
        RequestDispatcher rd = context.getRequestDispatcher(VIEW_BASEPATH
                + target);
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
