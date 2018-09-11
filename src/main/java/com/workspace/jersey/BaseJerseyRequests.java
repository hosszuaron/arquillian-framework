package com.workspace.jersey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BaseJerseyRequests {

    Logger logger = LoggerFactory.getLogger(BaseJerseyRequests.class);

    public Response getRequest(String server, String path) {
        logger.info(String.format("Sending GET request to: '%s/%s'", server, path));
        return ClientBuilder.newClient().target(server).path(path).request(MediaType.APPLICATION_JSON).get(Response.class);
    }

    public Response postRequest(String server, String path, Entity<?> entity) {
        logger.info(String.format("Sending POST request to: '%s/%s'", server, path));
        return ClientBuilder.newClient().target(server).path(path).request(MediaType.APPLICATION_JSON).post(entity);
    }

    public Response putRequest(String server, String path, Entity<?> entity) {
        logger.info(String.format("Sending PUT request to: '%s/%s'", server, path));
        return ClientBuilder.newClient().target(server).path(path).request(MediaType.APPLICATION_JSON).put(entity);
    }
}
