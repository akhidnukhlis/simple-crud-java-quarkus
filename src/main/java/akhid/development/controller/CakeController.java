package akhid.development.controller;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import akhid.development.dto.CakeRequestDto;
import akhid.development.model.postgres.Cake;
import akhid.development.service.CakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Path("/api/v1/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CakeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CakeController.class);
    @Inject
    CakeService cakeService;

    @Path("/cake/submit")
    @POST
    public Response submit (CakeRequestDto dto) {
        LOGGER.info("Cake submit: {}", dto);

        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> payload = cakeService.submit(dto);

            result.put("statusCode", 201);
            result.put("data", payload);
            return Response.status(Response.Status.CREATED)
                    .entity(result)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("statusCode", 500);
            result.put("message", "INTERNAL_SERVER_ERROR");
            return Response.serverError()
                    .entity(result)
                    .build();
        }
    }

    @Path("/cakes")
    @GET
    public Response findAll () {
        LOGGER.info("Cake find all");

        Map<String, Object> result = new HashMap<>();
        try {
            result.put("statusCode", 200);
            result.put("data", Cake.findAll().list());
            return Response.ok(result).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("statusCode", 500);
            result.put("message", "INTERNAL_SERVER_ERROR");
            return Response.serverError()
                    .entity(result)
                    .build();
        }
    }

    @Path("/cake/id/{id}")
    @GET
    public Response findById (
            @PathParam("id")
            @Pattern(regexp = "[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message = "Pattern not match")
            String uuid) {
        LOGGER.info("Cake find by id: {}", uuid);

        Map<String, Object> result = new HashMap<>();
        try {
            result.put("statusCode", 200);
            result.put("data", cakeService.findById(uuid));
            return Response.ok(result).build();
        } catch (ValidationException ex) {
            result.put("message", ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }  catch (Exception ex) {
            ex.printStackTrace();
            result.put("statusCode", 500);
            result.put("message", "INTERNAL_SERVER_ERROR");
            return Response.serverError()
                    .entity(result)
                    .build();
        }
    }

    @Path("/cake/update/id/{id}")
    @PUT
    public Response updateById (
            @PathParam("id")
            @Pattern(regexp = "[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message = "Pattern not match")
            String uuid, CakeRequestDto dto) {
        LOGGER.info("Cake update by id: {}", uuid);

        Map<String, Object> result = new HashMap<>();
        try {
            result.put("statusCode", 200);
            result.put("data", cakeService.updateById(uuid, dto));
            return Response.ok(result).build();
        } catch (ValidationException ex) {
            result.put("message", ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        } catch (NotFoundException nfe) {
            result.put("message", nfe.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("statusCode", 500);
            result.put("message", "INTERNAL_SERVER_ERROR");
            return Response.serverError()
                    .entity(result)
                    .build();
        }
    }

    @Path("/cake/delete/id/{id}")
    @DELETE
    public Response deleteById (
            @PathParam("id")
            @Pattern(regexp = "[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message = "Pattern not match")
            String uuid) {
        LOGGER.info("Cake delete by id: {}", uuid);

        Map<String, Object> result = new HashMap<>();
        try {
            result.put("statusCode", 200);
            result.put("data", cakeService.deleteById(uuid));
            return Response.ok(result).build();
        } catch (ValidationException ex) {
            result.put("message", ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        } catch (NotFoundException nfe) {
            result.put("message", nfe.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        }  catch (Exception ex) {
            ex.printStackTrace();
            result.put("statusCode", 500);
            result.put("message", "INTERNAL_SERVER_ERROR");
            return Response.serverError()
                    .entity(result)
                    .build();
        }
    }
}
