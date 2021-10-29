package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.models.Candidate;
import ru.job4j.dreamjob.models.Post;
import ru.job4j.dreamjob.models.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    void save(User user);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    User findUserByEmail(String email);

    void removeCandidate(int id);

    void clearTable(String tableName);
}
