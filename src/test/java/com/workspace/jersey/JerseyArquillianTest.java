package com.workspace.jersey;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class JerseyArquillianTest {

    @Inject
    HelloService helloService;

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "com.workspace")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @RunAsClient
    public void helloServiceTest(@ArquillianResteasyResource("jersey") WebTarget webTarget) {
        Response response = webTarget.path("/hello").request(MediaType.APPLICATION_JSON).get();
        assertEquals("Hello!", response.readEntity(String.class));
    }

    @Test
    public void helloInjectionTest() {
        assertEquals("Hello!", helloService.sayHello());
    }
}
