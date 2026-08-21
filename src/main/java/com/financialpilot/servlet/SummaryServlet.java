package com.financialpilot.servlet;

import java.io.IOException;

import com.financialpilot.dto.FinancialSummary;
import com.financialpilot.service.SummaryService;
import com.financialpilot.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/summary/*")
public class SummaryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private SummaryService summaryService = new SummaryService();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            ResponseUtil.sendError(
                    response,
                    "User ID is required.");

            return;
        }

        getFinancialSummary(request, response);
    }

    private void getFinancialSummary(HttpServletRequest request,
                                     HttpServletResponse response)
            throws IOException {

        try {

            String[] path = request.getPathInfo().split("/");

            if (path.length < 2) {

                ResponseUtil.sendError(
                        response,
                        "User ID is required.");

                return;
            }

            int userId = Integer.parseInt(path[1]);

            FinancialSummary summary =
                    summaryService.getFinancialSummary(userId);

            ResponseUtil.sendSuccess(
                    response,
                    "Financial Summary Retrieved Successfully",
                    summary);

        } catch (NumberFormatException e) {

            ResponseUtil.sendError(
                    response,
                    "Invalid User ID.");

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());
        }
    }
}