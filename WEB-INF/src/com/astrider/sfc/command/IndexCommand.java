package com.astrider.sfc.command;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.Command;
import com.astrider.sfc.lib.helper.annotation.Title;

@Title("トップページ")
public class IndexCommand extends Command {
    @Override
    protected void doGet() throws ServletException, IOException {
        render();
    }

    @Override
    protected void doPost() throws ServletException, IOException {
        render("/Unknown.jsp");
    }
}
