package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.models.Post;

import java.time.LocalDateTime;

public class PsqlMain {
    public static void main(String[] args) {
//        Store store = PsqlStore.instOf();
//        store.save(new Post(0, "Junior Java job"));
//        store.save(new Post(0, "Junior Java job"));
//        store.save(new Post(0, "Senior Java job"));
//        for (Post post : store.findAllPosts()) {
//            System.out.printf("%s %s%n",post.getId(), post.getName());
//        }
//        System.out.println();
//        store.save(new Post(2, "Middle Java job"));
//        for (Post post : store.findAllPosts()) {
//            System.out.printf("%s %s%n",post.getId(), post.getName());
//        }
//        System.out.println();
//        Post post = store.findPostById(3);
//        System.out.printf("%s %s%n",post.getId(), post.getName());
        Store store = MemStore.instOf();
        LocalDateTime day = LocalDateTime.now().minusHours(12);
        LocalDateTime twoDay = LocalDateTime.now().minusHours(27);
        store.save(new Post(0, "Java", "Middle", day));
        store.save(new Post(0, "Java", "Senior", twoDay));
        for (Post post : store.findAllPosts()) {
            System.out.printf("%s %s %s %s%n",post.getId(), post.getName(), post.getDescription(), post.getCreated());
        }

        for (Post post : store.findLastDayPosts()) {
            System.out.printf("%s %s %s %s%n",post.getId(), post.getName(), post.getDescription(), post.getCreated());
        }
    }
}
