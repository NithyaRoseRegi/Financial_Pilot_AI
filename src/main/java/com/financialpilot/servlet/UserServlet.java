package com.financialpilot.servlet;
import com.financialpilot.database.UserDAO;
import java.io.IOException;
import java.util.List;
import com.financialpilot.dto.LoginRequest;
import com.financialpilot.dto.LoginResponse;
import com.financialpilot.exception.AuthenticationException;
import com.financialpilot.exception.DatabaseException;
import com.financialpilot.exception.DuplicateEmailException;
import com.financialpilot.exception.ValidationException;
import com.financialpilot.model.User;
import com.financialpilot.service.UserService;
import com.financialpilot.util.JsonUtil;
import com.financialpilot.util.ResponseUtil;
import com.financialpilot.exception.UserNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    UserService userService = new UserService();
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path == null) {

            ResponseUtil.sendError(response, "Invalid Endpoint");
            return;
        }

        switch (path) {

            case "/register":
                registerUser(request, response);
                break;

            case "/login":
                loginUser(request, response);
                break;

            default:
                ResponseUtil.sendError(response, "Endpoint Not Found");
        }
    }
private void registerUser(HttpServletRequest request,
                          HttpServletResponse response)
        throws IOException {

    try {

        User user = JsonUtil.getObjectMapper()
                .readValue(request.getReader(), User.class);

        userService.registerUser(user);

        ResponseUtil.sendSuccess(
                response,
                "User registered successfully",
                null);

    } catch (DuplicateEmailException |
             ValidationException |
             DatabaseException e) {

        ResponseUtil.sendError(
                response,
                e.getMessage());

    } catch (Exception e) {

        e.printStackTrace();

        ResponseUtil.sendError(
                response,
                e.getClass().getName() + " : " + e.getMessage());
    }
        }

    

    private void loginUser(HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException {

    try {

        LoginRequest loginRequest =
                JsonUtil.getObjectMapper()
                        .readValue(request.getReader(), LoginRequest.class);

        User user = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword());

        LoginResponse loginResponse =
                new LoginResponse(
                        user.getUserId(),
                        user.getName(),
                        user.getEmail());

        ResponseUtil.sendSuccess(
                response,
                "Login Successful",
                loginResponse);

    } catch (AuthenticationException e) {

        ResponseUtil.sendError(
                response,
                e.getMessage());

    }catch (Exception e) {

    e.printStackTrace();

    ResponseUtil.sendError(
            response,
            e.toString());
}

}
@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    String path = request.getPathInfo();

    if (path == null || path.equals("/")) {

        getAllUsers(request, response);

    } else {

        getUserById(request, response);
    }
}
private void getAllUsers(HttpServletRequest request,
                         HttpServletResponse response)
        throws IOException {

    try {

        List<User> users = userService.getAllUsers();

        ResponseUtil.sendSuccess(
                response,
                "Users Retrieved Successfully",
                users);

    } catch (Exception e) {

        e.printStackTrace();

        ResponseUtil.sendError(
                response,
                e.getMessage());
    }
}
private void getUserById(HttpServletRequest request,
                         HttpServletResponse response)
        throws IOException {

    try {

        String path = request.getPathInfo();

        int userId =
                Integer.parseInt(path.substring(1));

        User user =
                userService.getUserById(userId);

        ResponseUtil.sendSuccess(
                response,
                "User Retrieved Successfully",
                user);

    } catch (UserNotFoundException e) {

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
@Override
protected void doPut(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    updateUser(request, response);
}
private void updateUser(HttpServletRequest request,
                        HttpServletResponse response)
        throws IOException {

    try {

        String path = request.getPathInfo();

        int userId = Integer.parseInt(path.substring(1));

        User user =
                JsonUtil.getObjectMapper()
                        .readValue(
                                request.getReader(),
                                User.class);

        user.setUserId(userId);

        if (UserDAO.emailExistsForAnotherUser(
        user.getEmail(),
        user.getUserId())) {

        throw new ValidationException(
            "Email already exists.");
}

        userService.updateUser(user);

        ResponseUtil.sendSuccess(
                response,
                "User Updated Successfully",
                null);

    } catch (ValidationException |
             DatabaseException |
             UserNotFoundException e) {

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
@Override
protected void doDelete(HttpServletRequest request,
                        HttpServletResponse response)
        throws ServletException, IOException {

    deleteUser(request, response);
}

private void deleteUser(HttpServletRequest request,
                        HttpServletResponse response)
        throws IOException {

    try {

        String path = request.getPathInfo();

        int userId = Integer.parseInt(path.substring(1));

        userService.deleteUser(userId);

        ResponseUtil.sendSuccess(
                response,
                "User Deleted Successfully",
                null);

    } catch (UserNotFoundException |
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
}