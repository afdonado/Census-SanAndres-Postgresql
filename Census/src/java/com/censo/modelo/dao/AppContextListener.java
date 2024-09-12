package com.censo.modelo.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
            sce.getServletContext().setAttribute("DataSource", dataSource);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}