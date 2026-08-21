package com.financialpilot.servlet;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financialpilot.model.Budget;
import com.financialpilot.service.BudgetService;
import com.financialpilot.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/budget/*")
public class BudgetServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private BudgetService budgetService = new BudgetService();

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        addBudget(request, response);
    }


@Override

protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    String pathInfo = request.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) {

        ResponseUtil.sendError(
                response,
                "Invalid Request.");

        return;
    }

    String[] path = pathInfo.split("/");

    // GET ALL BUDGETS FOR USER
    // /budget/user/1
    if (path.length == 3 &&
        path[1].equalsIgnoreCase("user")) {

        getAllBudgets(request, response);

    }

    // GET ONE BUDGET
    // /budget/1/8/2026
    else if (path.length == 4) {

        getBudget(request, response);

    }

    // GET REMAINING BUDGET
    // /budget/remaining/1/8/2026
    else if (path.length == 5 &&
             path[1].equalsIgnoreCase("remaining")) {

        getRemainingBudget(request, response);

    }

    else {

        ResponseUtil.sendError(
                response,
                "Invalid Endpoint.");

    }
}
    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        deleteBudget(request, response);
    }

    // ---------------- POST ----------------

    private void addBudget(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {

        try {

            Budget budget =
                    mapper.readValue(request.getReader(),
                            Budget.class);

            budgetService.addBudget(budget);

            ResponseUtil.sendSuccess(
                    response,
                    "Budget Added Successfully.",
                    budget);

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());
        }
    }

    // ---------------- GET ----------------

    private void getBudget(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {

        try {

             String pathInfo = request.getPathInfo();

            String[] path = pathInfo.split("/");

            int userId = Integer.parseInt(path[1]);
            int month = Integer.parseInt(path[2]);
            int year = Integer.parseInt(path[3]);

            Budget budget =
                    budgetService.getBudget(
                            userId,
                            month,
                            year);

            ResponseUtil.sendSuccess(
                    response,
                    "Budget Retrieved Successfully.",
                    budget);

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());
        }
    }

    private void getAllBudgets(HttpServletRequest request,
                           HttpServletResponse response)
        throws IOException {

    try {

        String[] path =
                request.getPathInfo().split("/");

        int userId =
                Integer.parseInt(path[2]);

        ResponseUtil.sendSuccess(
                response,
                "Budgets Retrieved Successfully.",
                budgetService.getAllBudgets(userId));

    } catch (Exception e) {

        e.printStackTrace();

        ResponseUtil.sendError(
                response,
                e.getMessage());
    }
}

    // ---------------- PUT ----------------

    private void updateBudget(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException {

        try {

            Budget budget =
                    mapper.readValue(request.getReader(),
                            Budget.class);

            budgetService.updateBudget(budget);

            ResponseUtil.sendSuccess(
                    response,
                    "Budget Updated Successfully.",
                    budget);

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());
        }
    }

    // ---------------- DELETE ----------------

    private void deleteBudget(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException {

        try {

             String pathInfo = request.getPathInfo();

            String[] path = pathInfo.split("/");


            int userId = Integer.parseInt(path[1]);
            int month = Integer.parseInt(path[2]);
            int year = Integer.parseInt(path[3]);

            budgetService.deleteBudget(
                    userId,
                    month,
                    year);

            ResponseUtil.sendSuccess(
                    response,
                    "Budget Deleted Successfully.",
                    null);

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());
        }
    }

    // ---------------- Remaining Budget ----------------

    private void getRemainingBudget(HttpServletRequest request,
                                    HttpServletResponse response)
            throws IOException {

        try {

             String pathInfo = request.getPathInfo();

            String[] path = pathInfo.split("/");


            int userId = Integer.parseInt(path[2]);
            int month = Integer.parseInt(path[3]);
            int year = Integer.parseInt(path[4]);

            Object summary =
                    budgetService.getRemainingBudget(
                            userId,
                            month,
                            year);

            ResponseUtil.sendSuccess(
                    response,
                    "Remaining Budget Retrieved Successfully.",
                    summary);

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());
        }
    }
}