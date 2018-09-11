package com.workspace.sql.User;

public interface UserRepository {

    void create(User user);

    User read(String email);

    void update(User user);

    void delete(String email);
}
