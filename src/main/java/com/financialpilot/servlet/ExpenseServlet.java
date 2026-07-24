package com.financialpilot.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.financialpilot.exception.DatabaseException;
import com.financialpilot.exception.ExpenseNotFoundException;
import com.financialpilot.exception.ValidationException;
import com.financialpilot.model.Expense;
import com.financialpilot.service.ExpenseService;
import com.financialpilot.service.UserService;
import com.financialpilot.util.JsonUtil;
import com.financialpilot.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/expenses/*")
public class ExpenseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExpenseService expenseService =
            new ExpenseService(new UserService());

    @Override
    protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    String path = request.getPathInfo();

    if (path == null || path.equals("/")) {

        addExpense(request, response);
        return;
    }

    ResponseUtil.sendError(
            response,
            "Endpoint Not Found");
}
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path == null) {

            ResponseUtil.sendError(response,
                    "Invalid Endpoint");
            return;
        }

        if (path.startsWith("/user/")) {

            getAllExpenses(request, response);

        } else {

            getExpenseById(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        updateExpense(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        deleteExpense(request, response);
    }

    private void addExpense(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException {

        try {

            Expense expense =
                    JsonUtil.getObjectMapper()
                            .readValue(
                                    request.getReader(),
                                    Expense.class);

            expenseService.addExpense(expense);

            ResponseUtil.sendSuccess(
                    response,
                    "Expense Added Successfully",
                    null);

        } catch (ValidationException |
                 DatabaseException e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());

        } catch (Exception e) {

            e.printStackTrace();

            ResponseUtil.sendError(
                    response,
                    e.toString());
        }
    }

        private void getAllExpenses(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException {

        try {

            String path = request.getPathInfo();

            int userId = Integer.parseInt(
                    path.substring(path.lastIndexOf("/") + 1));

            ArrayList<Expense> expenses =
                    expenseService.getExpenses(userId);

            ResponseUtil.sendSuccess(
                    response,
                    "Expenses Retrieved Successfully",
                    expenses);

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());
        }
    }

    private void getExpenseById(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException {

        try {

            String path = request.getPathInfo();

            int expenseId =
                    Integer.parseInt(
                            path.substring(1));

            Expense expense =
                    expenseService.getExpenseById(expenseId);

            ResponseUtil.sendSuccess(
                    response,
                    "Expense Retrieved Successfully",
                    expense);

        } catch (ExpenseNotFoundException e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    "Invalid Expense ID");
        }
    }

    private void updateExpense(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        try {

            Expense expense =
                    JsonUtil.getObjectMapper()
                            .readValue(
                                    request.getReader(),
                                    Expense.class);

            expenseService.updateExpense(expense);

            ResponseUtil.sendSuccess(
                    response,
                    "Expense Updated Successfully",
                    null);

        } catch (ExpenseNotFoundException |
                 ValidationException |
                 DatabaseException e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    "Invalid Update Request");
        }
    }

    private void deleteExpense(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        try {

            String path = request.getPathInfo();

            int expenseId =
                    Integer.parseInt(
                            path.substring(
                                    path.lastIndexOf("/") + 1));

            int userId =
                    Integer.parseInt(
                            request.getParameter("userId"));

            expenseService.deleteExpense(
                    expenseId,
                    userId);

            ResponseUtil.sendSuccess(
                    response,
                    "Expense Deleted Successfully",
                    null);

        } catch (ExpenseNotFoundException |
                 DatabaseException e) {

            ResponseUtil.sendError(
                    response,
                    e.getMessage());

        } catch (Exception e) {

            ResponseUtil.sendError(
                    response,
                    "Invalid Delete Request");
        }
    }
}