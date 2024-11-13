package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Stub
@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public synchronized List<Post> all() {
        return posts;
    }

    public synchronized Optional<Post> getById(long id) {
        return posts.stream()
                .filter(a -> a.getId() == id)
                .findAny();
    }

    public synchronized Post save(Post post) {
        if(posts.isEmpty()){
            posts.add(post);
            return post;
        }
        for (Post post1 : posts) {
            if (post1.getId() == post.getId()) {
                post1.setContent(post.getContent());
                return post;
            }
        }
        posts.add(post);
        return post;
    }

    public synchronized void removeById(long id) {
        for (Post post1 : posts) {
            if (post1.getId() == id) {
                posts.remove(post1);
                return;
            }
        }
    }
}
