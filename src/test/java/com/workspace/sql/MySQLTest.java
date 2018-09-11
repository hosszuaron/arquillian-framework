package com.workspace.sql;

import com.workspace.sql.User.User;
import com.workspace.sql.User.UserRepositoryImpl;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class MySQLTest {

    Logger logger = LoggerFactory.getLogger(MySQLTest.class);

    @Inject
    BaseDbConn baseDbConn;

    @Before
    public void setUp() {
        logger.warn("Starting Orders Issued Integration test suite.");
        baseDbConn.connectToDb();
    }

    @After
    public void tearDown() {
        baseDbConn.disconnectFromDb();
        logger.warn("Orders Issued Integration test suite finished.");
    }

    @Test
    public void testDeTest() {

        User user = new User();
        user.setId(1);
        user.setEmail("johnny@rotten.com");
        user.setName("Johnny Rotten");

        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        userRepository.create(user);
        User userFromDb = userRepository.read(user.getEmail());
        userRepository.delete(user.getEmail());

        Assert.assertEquals("Johnny Rotten", userFromDb.getName());
    }
}
