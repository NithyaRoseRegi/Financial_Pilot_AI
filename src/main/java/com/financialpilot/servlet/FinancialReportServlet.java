package com.financialpilot.servlet;

import java.io.IOException;

import com.financialpilot.dto.FinancialReport;
import com.financialpilot.service.FinancialReportService;
import com.financialpilot.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/reports/*")
public class FinancialReportServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;


    private FinancialReportService reportService =
            new FinancialReportService();


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String pathInfo =
                request.getPathInfo();


        if (pathInfo == null ||
            pathInfo.equals("/")) {

            ResponseUtil.sendError(
                    response,
                    "Invalid Request.");

            return;
        }


        try {

            String[] path =
                    pathInfo.split("/");


            /*
             * Expected:
             *
             * /monthly/1/8/2026
             *
             * path[1] = monthly
             * path[2] = userId
             * path[3] = month
             * path[4] = year
             */

            if (path.length == 5 &&
                path[1].equalsIgnoreCase("monthly")) {


                int userId =
                        Integer.parseInt(path[2]);


                int month =
                        Integer.parseInt(path[3]);


                int year =
                        Integer.parseInt(path[4]);


                FinancialReport report =
                        reportService.getMonthlyReport(
                                userId,
                                month,
                                year);


                ResponseUtil.sendSuccess(
                        response,
                        "Financial Report Retrieved Successfully.",
                        report);

            }

            else {

                ResponseUtil.sendError(
                        response,
                        "Invalid Endpoint.");

            }

        }

        catch (NumberFormatException e) {

            ResponseUtil.sendError(
                    response,
                    "Invalid User ID, Month or Year.");

        }

        catch (Exception e) {

            e.printStackTrace();

            ResponseUtil.sendError(
                    response,
                    e.getMessage());

        }
    }
}