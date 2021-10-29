package ru.job4j.dreamjob.store;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dreamjob.models.Candidate;
import ru.job4j.dreamjob.models.Post;
import ru.job4j.dreamjob.models.User;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PsqlStoreTest {

    @Before
    public void clearTablesForTests() {
        Store store = PsqlStore.instOf();
        for (String table : List.of("post", "candidate", "users")) {
            store.clearTable(table);
        }
    }

    @Test
    public void whenCreatePost() {
        Store store = PsqlStore.instOf();
        Post firstPost = new Post(0, "Junior Java Job");
        store.save(firstPost);
        Post postInPsql = store.findPostById(firstPost.getId());
        assertThat(postInPsql.getName(), is(firstPost.getName()));
    }

    @Test
    public void whenCreateCandidate() {
        Store store = PsqlStore.instOf();
        Candidate candidate = new Candidate(0, "Developer");
        store.save(candidate);
        Candidate candidateInPsql = store.findCandidateById(candidate.getId());
        assertThat(candidateInPsql.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdatePost() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Junior Java Job");
        store.save(post);
        post.setName("Middle Java Job");
        store.save(post);
        Post postInPsql = store.findPostById(post.getId());
        assertThat(postInPsql.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        Store store = PsqlStore.instOf();
        Candidate candidate = new Candidate(0, "Developer");
        store.save(candidate);
        candidate.setName("Java Developer");
        store.save(candidate);
        Candidate candidateInPsql = store.findCandidateById(candidate.getId());
        assertThat(candidateInPsql.getName(), is(candidate.getName()));
    }

    @Test
    public void whenCreateUser() {
        Store store = PsqlStore.instOf();
        User user = new User();
        user.setId(0);
        user.setName("Valera");
        user.setEmail("Valera@v.com");
        user.setPassword("qwerty");
        store.save(user);
        User userInPsql = store.findUserByEmail(user.getEmail());
        assertThat(userInPsql.getName(), is(user.getName()));
    }

    @Test
    public void whenUpdateUser() {
        Store store = PsqlStore.instOf();
        User user = new User();
        user.setId(0);
        user.setName("Valera");
        user.setEmail("Valera@v.com");
        user.setPassword("qwerty");
        store.save(user);
        user.setPassword("123");
        store.save(user);
        User userInPsql = store.findUserByEmail(user.getEmail());
        assertThat(userInPsql.getPassword(), is(user.getPassword()));
    }

    @Test
    public void whenRemoveCandidate() {
        Store store = PsqlStore.instOf();
        Candidate firstCandidate = new Candidate(0, "Developer");
        Candidate secondCandidate = new Candidate(0, "Programmer");
        store.save(firstCandidate);
        store.save(secondCandidate);
        store.removeCandidate(2);
        Collection<Candidate> candidatesInPsql = store.findAllCandidates();
        assertThat(candidatesInPsql.size(), is(1));
    }

    @Test
    public void whenClearTableInMemStore() {
        Store store = MemStore.instOf();
        Candidate firstCandidate = new Candidate(0, "Developer");
        Candidate secondCandidate = new Candidate(0, "Programmer");
        store.save(firstCandidate);
        store.save(secondCandidate);
        store.clearTable("candidate");
        Collection<Candidate> candidates = store.findAllCandidates();
        assertThat(candidates.size(), is(0));
    }
}