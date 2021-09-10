package ru.job4j.dreamjob.servlet;

import org.apache.commons.io.FilenameUtils;
import ru.job4j.dreamjob.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        Store.instOf().remove(Integer.parseInt(userId));
        File file = new File("c:\\images\\".concat(userId));
        file.delete();
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
