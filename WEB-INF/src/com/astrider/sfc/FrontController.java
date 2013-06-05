package com.astrider.sfc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.command.UnknownCommand;
import com.astrider.sfc.lib.helper.StringUtils;
import com.astrider.sfc.lib.helper.annotation.Title;

/*
 * Front Controller
 *
 * 概要
 *  全Commandに対するアクセスは全てこのクラスから振り分けられる
 *
 * 機能
 *  主要機能
 *      ・doGet()    通常のdoGetメソッド
 *      ・doPost()   通常のdoPostメソッド
 *
 *  副次機能
 *      ・setPageTitle()     CommandクラスにTitleアノテーションが付加されていた場合、sessionにpageTitle要素を格納
 *      ・setServletPath()   sessionにservletpathを格納。jsp転送後のviewUtils用
 *      ・getCommandName()   リクエストURLをCommand名に変換
 *      ・getCommand()       Commandクラスのインスタンスを生成、Command不在の場合はUnknownCommandにリダイレクト
 */

public class FrontController extends HttpServlet {
    private static final long serialVersionUID = -7412673150826768532L;

    /*
     * getリクエスト時
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = getCommand(request);
        setPageTitle(request, command);
        setServletPath(request);
        command.init(request, response, getServletContext());
        command.doGet();
    }

    /*
     * postリクエスト時
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = getCommand(request);
        setPageTitle(request, command);
        setServletPath(request);
        command.init(request, response, getServletContext());
        command.doPost();
    }

    /*
     * CommandクラスにTitleアノテーションが付加されていた場合、sessionにタイトル情報を保存
     */
    private void setPageTitle(HttpServletRequest request, Command command) {
        Title title = command.getClass().getAnnotation(Title.class);
        if (title != null && StringUtils.isNotEmpty(title.value())) {
            HttpSession session = request.getSession();
            session.setAttribute("pageTitle", title.value());
        }
    }

    /*
     * sessionにservletPathを追加。jspにforwardされてからのViewHelper用
     */
    private void setServletPath(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("servletPath", request.getServletPath());
    }

    /*
     * Commandクラスのインスタンスを生成 失敗した場合はUnknownにredirect
     */
    public Command getCommand(HttpServletRequest request) {
        String commandName = getCommandClassName(request);
        Class<? extends Command> commandClass;
        Command command = null;

        boolean isInvalidPath = false;
        try {
            commandClass = Class.forName(commandName).asSubclass(Command.class);
            command = commandClass.newInstance();
        } catch (ClassNotFoundException e) {
            command = new UnknownCommand();
            isInvalidPath = true;
        } catch (InstantiationException e) {
            command = new UnknownCommand();
            isInvalidPath = true;
        } catch (IllegalAccessException e) {
            command = new UnknownCommand();
            isInvalidPath = true;
        } catch (Exception e) {
            command = new UnknownCommand();
            isInvalidPath = true;
        }

        HttpSession session = request.getSession();
        session.setAttribute("invalidPath", isInvalidPath);
        return command;
    }

    /*
     * URLをpackage.name.classnameCommand に変換
     */
    protected String getCommandClassName(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getPackage().getName());
        sb.append(".command");
        String[] extracted = request.getServletPath().split("/");
        for (int i = 0; i < extracted.length; i++) {
            String item = extracted[i];
            if (StringUtils.isNotEmpty(item)) {
                sb.append(".");
                sb.append(item);
            }
        }
        sb.append("Command");
        return sb.toString();
    }
}
