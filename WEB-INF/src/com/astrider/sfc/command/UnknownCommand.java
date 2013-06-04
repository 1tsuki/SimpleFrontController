package com.astrider.sfc.command;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.FrontCommand;
import com.astrider.sfc.helper.annotation.Title;

@Title("ë∂ç›ÇµÇ»Ç¢ÉyÅ[ÉW")
public class UnknownCommand extends FrontCommand {

    @Override
    protected void doGet() throws ServletException, IOException {
        render("/Unknown.jsp");
    }

    @Override
    protected void doPost() throws ServletException, IOException {
        render("/Unknown.jsp");
    }

}
