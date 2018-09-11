package com.workspace.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;


@Singleton
public class BaseDbConn {

    static Logger logger = LoggerFactory.getLogger(BaseDbConn.class);

    private static Connection conn;

    private static String DB_CONN = "jdbc:mysql://localhost:3306/test_db";
    private static String DB_USER = "admin";
    private static String DB_PASS = "admin";

    public static Connection getConn() {
        return conn;
    }

    public static void connectToDb() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_CONN, DB_USER, DB_PASS);
                logger.info("Successfully established database connection.");
            } catch (Exception e) {
                logger.error("Error occurred while connecting to the DB!", e);
            }
        }
    }

    public static void disconnectFromDb() {
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
