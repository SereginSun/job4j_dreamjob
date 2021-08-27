package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.models.Candidate;
import ru.job4j.dreamjob.models.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "for Junior Java Developer", "23.08.2021"));
        posts.put(2, new Post(2, "Middle Java Job", "for Middle Java Developer", "24.08.2021"));
        posts.put(3, new Post(3, "Senior Java Job", "for Senior Java Developer", "22.08.2021"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}