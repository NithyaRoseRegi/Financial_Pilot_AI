package com.financialpilot.util;

import java.io.IOException;

import com.financialpilot.dto.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtil {

    private ResponseUtil() {

    }
    
    public static void sendUnauthorized(HttpServletResponse response,
                                    String message)
        throws IOException {
    response.setHeader("WWW-Authenticate", "Bearer");
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ApiResponse body = new ApiResponse(false, message, null);
    JsonUtil.getObjectMapper().writeValue(response.getWriter(), body);
}  

    public static void sendSuccess(HttpServletResponse response,
                                   String message,
                                   Object data)
            throws IOException {

        writeSuccess(response, message, data, HttpServletResponse.SC_OK);
    }

    public static void sendCreated(HttpServletResponse response,
                                   String message,
                                   Object data)
            throws IOException {

        writeSuccess(response, message, data, HttpServletResponse.SC_CREATED);
    }

    private static void writeSuccess(HttpServletResponse response,
                                     String message,
                                     Object data,
                                     int status)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);

        ApiResponse apiResponse =
                new ApiResponse(true, message, data);

        JsonUtil.getObjectMapper()
                .writeValue(response.getWriter(), apiResponse);
    }

    public static void sendError(HttpServletResponse response,
                                 String message)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        ApiResponse apiResponse =
                new ApiResponse(false, message, null);

        JsonUtil.getObjectMapper()
                .writeValue(response.getWriter(), apiResponse);
    }

}