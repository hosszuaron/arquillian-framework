package com.workspace.sql;

import com.workspace.sql.User.User;
import com.workspace.sql.User.UserRepositoryImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;

@RunWith(Arquillian.class)
public class MySQLTest {

    @Inject
    BaseDbConn baseDbConn;

    @Deployment
    public static WebArchive deploy() {
        File[] files = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importDependencies(ScopeType.COMPILE)
                .resolve()
                .withTransitivity()
                .asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(BaseDbConn.class.getPackage())
                .addPackage(User.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);
        return war;
    }

    @Test
    public void testDeTest() {

        baseDbConn.connectToDb();

        User user = new User();
        user.setId(1);
        user.setEmail("johnny@rotten.com");
        user.setName("Johnny Rotten");

        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        userRepository.create(baseDbConn.getConn(), user);
        User userFromDb = userRepository.read(baseDbConn.getConn(), user.getEmail());
        userRepository.delete(baseDbConn.getConn(), user.getEmail());

        baseDbConn.disconnectFromDb();

        Assert.assertEquals("Johnny Rotten", userFromDb.getName());
    }
}
