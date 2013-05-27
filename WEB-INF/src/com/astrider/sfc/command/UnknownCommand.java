package com.astrider.sfc.command;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.FrontCommand;
import com.astrider.sfc.helper.annotation.Page;

@Page("ë∂ç›ÇµÇ»Ç¢ÉyÅ[ÉW")
public class UnknownCommand extends FrontCommand {

    @Override
    protected void processGet() throws ServletException, IOException {
        render("/Unknown.jsp");
    }

    @Override
    protected void processPost() throws ServletException, IOException {
        render("/Unknown.jsp");
    }

}
