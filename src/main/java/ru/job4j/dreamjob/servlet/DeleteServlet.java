package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String canId = req.getParameter("canId");
        PsqlStore.instOf().removeCandidate(Integer.parseInt(canId));
        File file = new File("c:\\images\\".concat(canId));
        file.delete();
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
