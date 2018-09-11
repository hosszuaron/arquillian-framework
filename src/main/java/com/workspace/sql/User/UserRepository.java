package com.workspace.sql.User;

import java.sql.Connection;

public interface UserRepository {

    void create(Connection conn, User user);

    User read(Connection conn, String email);

    void update(Connection conn, User user);

    void delete(Connection conn, String email);
}
