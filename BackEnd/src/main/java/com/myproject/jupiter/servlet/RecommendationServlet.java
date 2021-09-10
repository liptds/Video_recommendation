package com.myproject.jupiter.servlet;

import com.myproject.jupiter.entity.Item;
import com.myproject.jupiter.recommendation.ItemRecommender;
import com.myproject.jupiter.recommendation.RecommendationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RecommendationServlet", value = "/recommendation")
public class RecommendationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, List<Item>> itemMap = new HashMap<>();
        HttpSession session = request.getSession(false);
        ItemRecommender itemRecommender = new ItemRecommender();
        try {
            if (session == null) {
                itemMap = itemRecommender.recommendItemsByDefault();
            } else {
                String userId = (String) request.getSession().getAttribute("user_id");
                itemMap = itemRecommender.recommendItemsByUser(userId);
            }
        } catch (
                RecommendationException e) {
            throw new ServletException(e);
        }

        ServletUtil.writeItemMap(response, itemMap);
    }
}

