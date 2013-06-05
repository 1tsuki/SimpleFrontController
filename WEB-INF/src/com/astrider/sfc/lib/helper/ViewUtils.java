package com.astrider.sfc.lib.helper;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/*
 * jsp���ŗ��p����w���p�[�N���X
 */
public class ViewUtils {
    private PageContext context;
    private HttpSession session;
    private HttpServletRequest request;

    public ViewUtils(PageContext context, HttpSession session, HttpServletRequest request) {
        this.context = context;
        this.session = session;
        this.request = request;
    }

    /*
     * ���΃p�X���擾(contextPath��擪�ɕt��)
     */
    public void getPath(String target) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getContextPath());
        sb.append(target);
        print(sb.toString());
    }

    /*
     * url�ɑΉ�����css�����[�h
     * frontController��invalid���R�[������Ă����ꍇ��Unknown.css�����[�h
     */
    public void getCssPath() {
        String fileName = getCssFileName();
        StringBuilder sb = new StringBuilder();
        sb.append("/asset/css");
        sb.append(fileName);

        getPath(sb.toString());
    }

    private String getCssFileName() {
        String replaced = request.getServletPath().replace("/WEB-INF/template/view", "").replace(".jsp", "");
        String[] splitted = replaced.split("/");

        StringBuilder sb = new StringBuilder();
        for (int i=0; i < splitted.length; i++) {
            String item = splitted[i].toLowerCase();
            sb.append(item);
            if (i != splitted.length - 1) {
                sb.append("/");
            }
        }
        sb.append(".css");

        return sb.toString();
    }

    /*
     * session�Ɋi�[���ꂽ�y�[�W�^�C�g����񂩂瓮�I�Ƀ^�C�g�����o��
     */
    public void getTitle() {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getServletContext().getServletContextName());
        String pageTitle = (String) session.getAttribute("pageTitle");
        if (StringUtils.isNotEmpty(pageTitle)) {
            sb.append(" | ");
            sb.append(pageTitle);
        }

        String title = sb.toString();
        print(title);
    }

    /*
     * session�Ɋi�[���ꂽerrorMessages��flashMessage�Ƃ��ĕ\��
     */
    public void getFlashMessages() {
        @SuppressWarnings("unchecked")
        ArrayList<String> messages = (ArrayList<String>) session.getAttribute("flashMessages");
        if (messages != null && 0 < messages.size()) {
            print("<ul>");
            for (String message : messages) {
                print("<li>" + message + "</li>");
            }
            print("</ul>");
        }
    }

    /*
     * print���ȈՉ�
     */
    private void print(String arg) {
        try {
            context.getOut().print(arg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
