package akhid.development.controller;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import akhid.development.dto.CakeRequestDto;
import akhid.development.service.CakeService;

import java.util.HashMap;
import java.util.Map;

@Path("/api/v1/product/cake")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CakeController {
    @Inject
    CakeService cakeService;

    @Path("/submit")
    @POST
    public Response submit(CakeRequestDto dto) {
        Map<String, Object> result = new HashMap<>();

        try {
            Map<String, Object> payload = cakeService.submit(dto);

            result.put("statusCode", 201);
            result.put("data", payload);
            return Response.status(Response.Status.CREATED)
                    .entity(result)
                    .build();
        } catch (ValidationException ex) {
            result.put("statusCode", 300);
            result.put("message", ex.getMessage());
            return Response.ok()
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
}
