package ru.job4j.dreamjob.servlet;

import org.junit.Test;
import ru.job4j.dreamjob.models.Post;
import ru.job4j.dreamjob.store.PsqlStore;
import ru.job4j.dreamjob.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServletTest {

    @Test
    public void whenCreatePost() throws IOException, ServletException {
        Store store = PsqlStore.instOf();
        store.clearTable("post");
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("Some vacancy");
        when(req.getParameter("description")).thenReturn("Some description");
        new PostServlet().doPost(req, resp);
        Post result = store.findPostById(1);
        assertThat(result.getName(), is("Some vacancy"));
    }

    @Test
    public void whenEditPost() throws IOException, ServletException {
        Store store = PsqlStore.instOf();
        store.clearTable("post");
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("Some vacancy");
        when(req.getParameter("description")).thenReturn("Some description");
        new PostServlet().doPost(req, resp);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("New vacancy");
        when(req.getParameter("description")).thenReturn("New description");
        new PostServlet().doPost(req, resp);
        Post result = store.findPostById(1);
        assertThat(result.getName(), is("New vacancy"));
    }
}