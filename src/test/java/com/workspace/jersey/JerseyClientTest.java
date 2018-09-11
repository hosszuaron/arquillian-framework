package com.workspace.jersey;

import com.workspace.jersey.model.TodosModel;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class JerseyClientTest {

    @Test
    public void jerseyGetTest() {
        JerseyRequestClient requestClient = new JerseyRequestClient();
        Response response = requestClient.getDummy();
        assertEquals(200, response.getStatus());
        assertEquals("1", response.readEntity(TodosModel.class).getId());
    }

    @Test
    public void jerseyPostTest() {
        JerseyRequestClient requestClient = new JerseyRequestClient();
        Response response = requestClient.postDummy();
        assertEquals(201, response.getStatus());
        assertEquals("5", response.readEntity(TodosModel.class).getUserId());
    }

    @Test
    public void jerseyPutTest() {
        JerseyRequestClient requestClient = new JerseyRequestClient();
        Response response = requestClient.putDummy();
        assertEquals(200, response.getStatus());
        assertEquals("7", response.readEntity(TodosModel.class).getUserId());
    }
}
