package com.financialpilot.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.financialpilot.exception.BankAccountNotFoundException;
import com.financialpilot.exception.DatabaseException;
import com.financialpilot.exception.ValidationException;

import com.financialpilot.model.BankAccount;

import com.financialpilot.service.BankAccountService;
import com.financialpilot.service.UserService;

import com.financialpilot.util.JsonUtil;
import com.financialpilot.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/accounts/*")
public class BankAccountServlet extends HttpServlet {

    private BankAccountService bankAccountService;
    @Override
    public void init() {

    UserService userService = new UserService();

    bankAccountService =
            new BankAccountService(userService);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    addBankAccount(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    String path = request.getPathInfo();

    if (path == null) {

        ResponseUtil.sendError(
                response,
                "Invalid Endpoint");

        return;
    }

    if (path.startsWith("/user/")) {

        getAllAccountsByUser(request,response);

    } else {

        getBankAccountById(request, response);
    }
    }

    @Override
    protected void doPut(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    updateBankAccount(request, response);
    }
    
    @Override
    protected void doDelete(HttpServletRequest request,
                        HttpServletResponse response)
        throws ServletException, IOException {

    deleteBankAccount(request, response);
   } 

    private void addBankAccount(HttpServletRequest request,
                            HttpServletResponse response)
        throws IOException {

    try {

        BankAccount account =
                JsonUtil.getObjectMapper()
                        .readValue(
                                request.getReader(),
                                BankAccount.class);

        bankAccountService.addBankAccount(account);

        ResponseUtil.sendSuccess(
                response,
                "Bank Account Added Successfully",
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
                "Invalid Bank Account Request");
    }
}
private void getAllAccountsByUser(HttpServletRequest request,
                            HttpServletResponse response)
        throws IOException {

    try {

        String path = request.getPathInfo();

        String userId =
                path.substring("/user/".length());

        ArrayList<BankAccount> accounts =
                bankAccountService.getAllAccountsByUser(
                        Integer.parseInt(userId));

        ResponseUtil.sendSuccess(
                response,
                "Bank Accounts Retrieved Successfully",
                accounts);

    } catch (Exception e) {

        e.printStackTrace();

        ResponseUtil.sendError(
                response,
                "Unable to retrieve bank accounts.");
    }
}
private void getBankAccountById(HttpServletRequest request,
                                HttpServletResponse response)
        throws IOException {

    try {

        String path = request.getPathInfo();

        // Example:
        // /1
        // accountId = "1"

        String accountId = path.substring(1);

        BankAccount account =
                bankAccountService.getBankAccountById(Integer.valueOf(accountId));

        ResponseUtil.sendSuccess(
                response,
                "Bank Account Retrieved Successfully",
                account);

    } catch (BankAccountNotFoundException e) {

        ResponseUtil.sendError(
                response,
                e.getMessage());

    } catch (Exception e) {

        e.printStackTrace();

        ResponseUtil.sendError(
                response,
                "Unable to retrieve bank account.");
    }
}

private void updateBankAccount(HttpServletRequest request,
                               HttpServletResponse response)
        throws IOException {

    try {

        String path = request.getPathInfo();

        String accountId =
                path.substring(1);

        BankAccount account =
                JsonUtil.getObjectMapper()
                        .readValue(
                                request.getReader(),
                                BankAccount.class);

        account.setAccountId(
                Integer.parseInt(accountId));

        bankAccountService.updateBankAccount(account);

        ResponseUtil.sendSuccess(
                response,
                "Bank Account Updated Successfully",
                null);

    } catch (BankAccountNotFoundException |
             ValidationException |
             DatabaseException e) {

        ResponseUtil.sendError(
                response,
                e.getMessage());

    } catch (Exception e) {

        e.printStackTrace();

        ResponseUtil.sendError(
                response,
                "Invalid Update Request");
    }
}
private void deleteBankAccount(HttpServletRequest request,
                               HttpServletResponse response)
        throws IOException {

    try {

        String path = request.getPathInfo();

        String accountId =
                path.substring(1);

        bankAccountService.deleteBankAccount(String.valueOf(accountId));
        ResponseUtil.sendSuccess(
                response,
                "Bank Account Deleted Successfully",
                null);

    } catch (BankAccountNotFoundException |
             DatabaseException e) {

        ResponseUtil.sendError(
                response,
                e.getMessage());

    } catch (Exception e) {

        e.printStackTrace();

        ResponseUtil.sendError(
                response,
                "Invalid Delete Request");
    }
}
}