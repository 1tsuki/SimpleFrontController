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
 * �T�v
 *  �SCommand�ɑ΂���A�N�Z�X�͑S�Ă��̃N���X����U�蕪������
 *
 * �@�\
 *  ��v�@�\
 *      �EdoGet()    �ʏ��doGet���\�b�h
 *      �EdoPost()   �ʏ��doPost���\�b�h
 *
 *  �����@�\
 *      �EsetPageTitle()     Command�N���X��Title�A�m�e�[�V�������t������Ă����ꍇ�Asession��pageTitle�v�f���i�[
 *      �EsetServletPath()   session��servletpath���i�[�Bjsp�]�����viewUtils�p
 *      �EgetCommandName()   ���N�G�X�gURL��Command���ɕϊ�
 *      �EgetCommand()       Command�N���X�̃C���X�^���X�𐶐��ACommand�s�݂̏ꍇ��UnknownCommand�Ƀ��_�C���N�g
 */

public class FrontController extends HttpServlet {
    private static final long serialVersionUID = -7412673150826768532L;

    /*
     * get���N�G�X�g��
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = getCommand(request);
        setPageTitle(request, command);
        setServletPath(request);
        command.init(request, response, getServletContext());
        command.doGet();
    }

    /*
     * post���N�G�X�g��
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = getCommand(request);
        setPageTitle(request, command);
        setServletPath(request);
        command.init(request, response, getServletContext());
        command.doPost();
    }

    /*
     * Command�N���X��Title�A�m�e�[�V�������t������Ă����ꍇ�Asession�Ƀ^�C�g������ۑ�
     */
    private void setPageTitle(HttpServletRequest request, Command command) {
        Title title = command.getClass().getAnnotation(Title.class);
        if (title != null && StringUtils.isNotEmpty(title.value())) {
            HttpSession session = request.getSession();
            session.setAttribute("pageTitle", title.value());
        }
    }

    /*
     * session��servletPath��ǉ��Bjsp��forward����Ă����ViewHelper�p
     */
    private void setServletPath(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("servletPath", request.getServletPath());
    }

    /*
     * Command�N���X�̃C���X�^���X�𐶐� ���s�����ꍇ��Unknown��redirect
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
     * URL��package.name.classnameCommand �ɕϊ�
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
