package ru.netology.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PostService {
  private final PostRepository repository;

  private static AtomicInteger aInt = new AtomicInteger(0);

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id).orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    if(post.getId() == 0) {
      post.setId(aInt.incrementAndGet());
    }
    return repository.save(post);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}

