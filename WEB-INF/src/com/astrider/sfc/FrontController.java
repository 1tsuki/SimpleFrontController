package com.astrider.sfc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.command.UnknownCommand;
import com.astrider.sfc.helper.StringUtils;
import com.astrider.sfc.helper.annotation.Title;

public class FrontController extends HttpServlet {
    private static final long serialVersionUID = -7412673150826768532L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        setPageTitle(request, command);
        command.init(request, response, getServletContext());
        command.doGet();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        setPageTitle(request, command);
        command.init(request, response, getServletContext());
        command.doPost();
    }

    protected void setPageTitle(HttpServletRequest request, FrontCommand command) {
        Title title = command.getClass().getAnnotation(Title.class);
        if (title != null && StringUtils.isNotEmpty(title.value())) {
            HttpSession session = request.getSession();
            session.setAttribute("pageTitle", title.value());
        }
    }

    protected FrontCommand getCommand(HttpServletRequest request) {
        String commandName = getCommandClassName(request);
        Class<? extends FrontCommand> commandClass;
        FrontCommand command = null;
        try {
            commandClass = Class.forName(commandName).asSubclass(FrontCommand.class);
            command = commandClass.newInstance();
        } catch (ClassNotFoundException e) {
            command = new UnknownCommand();
        } catch (InstantiationException e) {
            command = new UnknownCommand();
        } catch (IllegalAccessException e) {
            command = new UnknownCommand();
        } catch (Exception e) {
            command = new UnknownCommand();
        }

        return command;
    }

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
