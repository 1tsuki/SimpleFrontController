package com.astrider.sfc.lib.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BaseDao {
    private final String localName = "java:comp/env/jdbc/testCon";
    private Context      context   = null;
    private DataSource   ds        = null;
    protected Connection con       = null;

    public BaseDao() {
        try {
            context = new InitialContext();
            ds = (DataSource) context.lookup(localName);
            con = ds.getConnection();
            con.setAutoCommit(false);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
