package com.autoflex.inventory.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("success", false);
        
        if (exception instanceof ResourceNotFoundException) {
            errorResponse.put("message", exception.getMessage());
            errorResponse.put("error", "Resource Not Found");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }
        else if (exception instanceof IllegalArgumentException) {
            errorResponse.put("message", exception.getMessage());
            errorResponse.put("error", "Bad Request");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        }
        else if (exception instanceof jakarta.validation.ConstraintViolationException) {
            errorResponse.put("message", "Validation failed");
            errorResponse.put("error", "Validation Error");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        }
        
        errorResponse.put("message", "An unexpected error occurred");
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("details", exception.getMessage());
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .build();
    }
}
