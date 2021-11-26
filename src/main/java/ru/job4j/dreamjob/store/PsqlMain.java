package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.models.Post;

import java.time.LocalDateTime;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Junior Java job", "Some desc...", LocalDateTime.now().minusHours(12)));
        store.save(new Post(0, "Junior Java job", "description", LocalDateTime.now().minusHours(27)));
        store.save(new Post(0, "Senior Java job", "Senior", LocalDateTime.now().minusDays(2)));
        for (Post post : store.findAllPosts()) {
            System.out.printf("%s %s %s %s%n",post.getId(), post.getName(), post.getDescription(), post.getCreated());
        }
        System.out.println();
        store.save(new Post(22, "Middle Java job", "Not senior.", LocalDateTime.now()));
        for (Post post : store.findAllPosts()) {
            System.out.printf("%s %s %s %s%n",post.getId(), post.getName(), post.getDescription(), post.getCreated());
        }
        System.out.println();
        Post post = store.findPostById(3);
        System.out.printf("%s %s %s %s%n",post.getId(), post.getName(), post.getDescription(), post.getCreated());
    }
}
