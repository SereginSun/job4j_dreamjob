package ru.job4j.dreamjob.servlet;

import org.junit.Test;
import ru.job4j.dreamjob.models.Candidate;
import ru.job4j.dreamjob.store.PsqlStore;
import ru.job4j.dreamjob.store.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CandidateServletTest {

    @Test
    public void whenCreateCandidate() throws IOException {
        Store store = PsqlStore.instOf();
        store.clearTable("candidate");
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("Boris");
        when(req.getParameter("city")).thenReturn("1");
        new CandidateServlet().doPost(req, resp);
        Candidate result = store.findCandidateById(1);
        assertThat(result.getName(), is("Boris"));
    }

    @Test
    public void whenEditCandidate() throws IOException {
        Store store = PsqlStore.instOf();
        store.clearTable("candidate");
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("Boris");
        when(req.getParameter("city")).thenReturn("1");
        new CandidateServlet().doPost(req, resp);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("Roman");
        when(req.getParameter("city")).thenReturn("2");
        new CandidateServlet().doPost(req, resp);
        Candidate candidate = store.findCandidateById(1);
        assertThat(candidate.getName(), is("Roman"));
    }
}