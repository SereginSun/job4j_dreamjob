package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.models.Post;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Junior Java job"));
        store.save(new Post(0, "Junior Java job"));
        store.save(new Post(0, "Senior Java job"));
        for (Post post : store.findAllPosts()) {
            System.out.printf("%s %s%n",post.getId(), post.getName());
        }
        System.out.println();
        store.save(new Post(2, "Middle Java job"));
        for (Post post : store.findAllPosts()) {
            System.out.printf("%s %s%n",post.getId(), post.getName());
        }
        System.out.println();
        Post post = store.findPostById(3);
        System.out.printf("%s %s%n",post.getId(), post.getName());
    }
}
