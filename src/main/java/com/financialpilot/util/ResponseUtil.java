package com.financialpilot.util;

import java.io.IOException;

import com.financialpilot.dto.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtil {

    private ResponseUtil() {

    }

    public static void sendSuccess(HttpServletResponse response,
                                   String message,
                                   Object data)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

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