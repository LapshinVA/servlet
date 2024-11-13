package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_DELETE = "DELETE";

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("ru.netology");
        controller = (PostController) context.getBean("postController");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            switch (method) {
                case METHOD_GET:
                    if (path.equals("/api/posts")) {
                        controller.all(resp);
                        return;
                    }
                    if (path.matches("/api/posts/\\d+")) {
                        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                        controller.getById(id, resp);
                        return;
                    }
                    break;

                case METHOD_POST:
                    if (path.equals("/api/posts")) {
                        controller.save(req.getReader(), resp);
                        return;
                    }
                    break;

                case METHOD_DELETE:
                    if (path.matches("/api/posts/\\d+")) {
                        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") +1));
                        controller.removeById(id, resp);
                        return;
                    }
                    break;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

