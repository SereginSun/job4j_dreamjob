package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.models.Candidate;
import ru.job4j.dreamjob.models.City;
import ru.job4j.dreamjob.models.Post;
import ru.job4j.dreamjob.models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    private static final AtomicInteger USER_ID = new AtomicInteger(4);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private final Map<Integer, City> cities = new ConcurrentHashMap<>();

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public User findUserByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }
    @Override
    public void removeCandidate(int id) {
        candidates.remove(id);
    }

    @Override
    public void removePost(int id) {
        posts.remove(id);
    }

    @Override
    public Collection<City> findAllCities() {
        return cities.values();
    }

    @Override
    public Collection<Post> findLastDayPosts() {
        List<Post> lastDayPosts = new ArrayList<>();
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        for (Post post : posts.values()) {
            if (post.getCreated().isAfter(lastDay)) {
                lastDayPosts.add(post);
            }
        }
        return lastDayPosts;
    }

    @Override
    public Collection<Candidate> findLastDayCandidates() {
        List<Candidate> lastDayCandidates = new ArrayList<>();
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        for (Candidate candidate : candidates.values()) {
            if (candidate.getCreated().isAfter(lastDay)) {
                lastDayCandidates.add(candidate);
            }
        }
        return lastDayCandidates;
    }

    @Override
    public void clearTable(String tableName) {
        switch (tableName) {
            case "post":
                posts.clear();
                break;
            case "candidate":
                candidates.clear();
                break;
            case "users":
                users.clear();
                break;
        }
    }
}
