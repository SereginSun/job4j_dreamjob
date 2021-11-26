package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.models.Post;
import ru.job4j.dreamjob.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", new ArrayList<>(PsqlStore.instOf().findAllPosts()));
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Post post = new Post(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"),
                req.getParameter("description"),
                LocalDateTime.now()
        );
        PsqlStore.instOf().save(post);
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
