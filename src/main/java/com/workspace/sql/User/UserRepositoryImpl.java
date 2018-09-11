package com.workspace.sql.User;

import com.workspace.sql.BaseDbConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {

    Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Inject
    BaseDbConn baseDbConn;

    public void create(User user) {
        try {
            String INSERT_RECORD = "INSERT INTO users VALUES (?,?,?)";
            PreparedStatement ps = baseDbConn.getConn().prepareStatement(INSERT_RECORD);

            ps.setLong(1, user.getId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getName());
            ps.addBatch();

            ps.executeBatch();

            logger.info("Test data with the following details was saved in the DB: " + user.toString());
        } catch (Exception e) {
            logger.error("Error occurred during DB insert!", e);
        }
    }

    public User read(String email) {
        try {
            String SELECT_RECORD = "SELECT * FROM users WHERE email=?";
            PreparedStatement ps = baseDbConn.getConn().prepareStatement(SELECT_RECORD);

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return getUser(rs);
        } catch (Exception e) {
            logger.error("Error occurred during DB query!", e);
            return null;
        }
    }

    private User getUser(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return null;
        }

        long id = rs.getLong("id");
        String email = rs.getString("email");
        String name = rs.getString("name");

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);

        return user;
    }

    public void update(User user) {
        User existingUser = read(user.getEmail());
        if (existingUser == null) {
            return;
        }
        try {
            String UPDATE_RECORD = "UPDATE users SET id=?, email=?, name=?";
            PreparedStatement ps = baseDbConn.getConn().prepareStatement(UPDATE_RECORD);

            ps.setLong(1, user.getId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getName());

            int noOfAffectedRows = ps.executeUpdate();
            if (noOfAffectedRows == 0) {
                logger.warn("Record was not found in the DB!");
                return;
            }
            logger.info("Test data with ID '" + user.getId()
                    + "' was updated in the DB with the following details: " + user.toString());
        } catch (Exception e) {
            logger.error("Error occurred during DB update!", e);
        }
    }

    public void delete(String email) {
        try {
            String DELETE_RECORD = "DELETE FROM users WHERE email=?";
            PreparedStatement ps = baseDbConn.getConn().prepareStatement(DELETE_RECORD);

            ps.setString(1, email);

            int noOfAffectedRows = ps.executeUpdate();
            if (noOfAffectedRows == 0) {
                logger.warn("Record was not found in the DB!");
                return;
            }
            logger.info("Successfully deleted test data with email: '" + email + "' from 'users'.");
        } catch (Exception e) {
            logger.error("Error occurred during DB delete!", e);
        }
    }
}
