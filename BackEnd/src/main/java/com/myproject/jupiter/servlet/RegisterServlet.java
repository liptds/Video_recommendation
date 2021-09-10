package com.myproject.jupiter.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.jupiter.db.MySQLConnection;
import com.myproject.jupiter.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(request.getReader(), User.class);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        user.setPassword(ServletUtil.encryptPassword(user.getUserId(),user.getPassword()));
        MySQLConnection connection = new MySQLConnection();
        boolean isAdded = connection.addUser(user);
        connection.close();
        if (isAdded) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
