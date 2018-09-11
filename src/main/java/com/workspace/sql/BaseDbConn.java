package com.workspace.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDbConn {

    Logger logger = LoggerFactory.getLogger(BaseDbConn.class);

    private Connection conn;

    private String DB_CONN = "jdbc:mysql://localhost:3306/test_db";
    private String DB_USER = "admin";
    private String DB_PASS = "admin";

    public BaseDbConn() {
    }

    public Connection getConn() {
        return conn;
    }

    public void connectToDb() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_CONN, DB_USER, DB_PASS);
                logger.info("Successfully established database connection.");
            } catch (Exception e) {
                logger.error("Error occurred while connecting to the DB!", e);
            }
        }
    }

    public void disconnectFromDb() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                logger.info("Successfully disconnected from the database.");
            } catch (Exception e) {
                logger.error("Error occurred while disconnecting to the DB!", e);
            }
        }
    }
}
