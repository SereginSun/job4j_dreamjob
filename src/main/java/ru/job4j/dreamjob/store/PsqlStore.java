package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dreamjob.models.Candidate;
import ru.job4j.dreamjob.models.City;
import ru.job4j.dreamjob.models.Post;
import ru.job4j.dreamjob.models.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.format;

public class PsqlStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(PsqlStore.class.getClassLoader()
                                .getResourceAsStream("db.properties"))
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        String sql = "SELECT * FROM post";
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        String sql = "SELECT * FROM candidate";
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id"),
                            it.getTimestamp("created").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private void create (Post post) {
        String sql = "INSERT INTO post(name, description, created) VALUES (?, ?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
    }

    private void update(Post post) {
        String sql = "UPDATE post SET name = ?, description = ?, created = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.setInt(4, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private void create(Candidate candidate) {
        String sql = "INSERT INTO candidate(name, city_id, created) VALUES (?, ?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
    }

    private void update(Candidate candidate) {
        String sql = "UPDATE candidate SET name = ?, city_id = ?, created = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setInt(4, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    private void create(User user) {
        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
    }

    private void update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
    }

    @Override
    public Post findPostById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    String name = result.getString(2);
                    String desc = result.getString(3);
                    LocalDateTime created = result.getTimestamp(4).toLocalDateTime();
                    post = new Post(id, name, desc, created);
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
        return post;
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    String name = result.getString(2);
                    int cityId = result.getInt(3);
                    LocalDateTime created = result.getTimestamp(4).toLocalDateTime();
                    candidate = new Candidate(id, name, cityId, created);
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
        return candidate;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    user = new User();
                    user.setId(result.getInt(1));
                    user.setName(result.getString(2));
                    user.setEmail(email);
                    user.setPassword(result.getString(4));
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
        return user;
    }

    @Override
    public void removeCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM candidate WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
    }

    @Override
    public void removePost(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM post WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
    }

    public Collection<City> findAllCities() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cities.add(new City(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
        return cities;
    }

    @Override
    public Collection<Post> findLastDayPosts() {
        String sql = "SELECT * FROM post WHERE created BETWEEN now() - interval '1 day' AND now()";
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getTimestamp(4).toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findLastDayCandidates() {
        String sql = "SELECT * FROM candidate WHERE created BETWEEN now() - interval '1 day' AND now()";
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    candidates.add(new Candidate(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getTimestamp(4).toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Database query failed", e);
        }
        return candidates;
    }

    @Override
    public void clearTable(String tableName) {
        String script1 = format("DELETE FROM %s", tableName);
        String script2 = format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1", tableName);
        try (Connection cn = pool.getConnection();
             Statement statement = cn.createStatement()) {
            statement.executeUpdate(script1);
            statement.executeUpdate(script2);
        } catch (SQLException e) {
            LOG.warn("Failed to clear " + tableName);
        }
    }
}
