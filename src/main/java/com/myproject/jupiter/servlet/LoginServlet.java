package com.myproject.jupiter.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.jupiter.db.MySQLConnection;
import com.myproject.jupiter.entity.LoginRequestBody;
import com.myproject.jupiter.entity.LoginResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        LoginRequestBody body = mapper.readValue(request.getReader(), LoginRequestBody.class);
        if (body == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        MySQLConnection connection = new MySQLConnection();
        String userId = body.getUserId();
        String password = ServletUtil.encryptPassword(body.getUserId(),body.getPassword());
        String userName = connection.verifyLogin(userId, password);
        connection.close();
        if (userName.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            // create session
            HttpSession session = request.getSession(); //create a new session
            session.setAttribute("user_id",body.getUserId());
            session.setAttribute("user_name", userName);
            session.setMaxInactiveInterval(600);
            LoginResponseBody responseBody = new LoginResponseBody(body.getUserId(), userName);
            response.getWriter().print(new ObjectMapper().writeValueAsString(responseBody));
        }
    }
}
