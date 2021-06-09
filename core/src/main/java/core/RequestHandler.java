package core;

import com.google.gson.Gson;
import persistpkg.Post;
import persistpkg.PostFunctions;
import spi.Adress;
import spi.Random;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ServiceLoader;

public class RequestHandler {

    public static Response handleRequest(Request request) {
        return switch (request.getRequestType()) {
            case GET -> get(request);
            case POST -> post(request);
            case HEAD -> head(request);
        };
    }
    //TODO!
    private static Response get(Request request) {
        if (request.getUrl().equals("/")) {
            return null;
        }

        else if (request.getUrl().equals("/getAll")){
            List<Post> posts = PostFunctions.getAllPosts();
            if (posts != null) {
                Gson gson = new Gson();
                String responseBody = gson.toJson(posts);
                int length = responseBody.getBytes(StandardCharsets.UTF_8).length;

                return new Response(200, length, "application/json", responseBody);
            }

            else {
                return new Response(400, 0);
            }
        }

        else if (request.getUrl().startsWith("/?id=")) {
            Integer targetId = Integer.parseInt(request.getUrl().replaceAll("[^\\d.]", ""));
            Post post = PostFunctions.getPost(targetId);
            if (post != null) {
                Gson gson = new Gson();
                String responseBody = gson.toJson(post);
                int length = responseBody.getBytes(StandardCharsets.UTF_8).length;

                return new Response(200, length, "application/json", responseBody);
            }
            else {
                return new Response(400, 0);
            }
        }

        else if (request.getUrl().startsWith("/?random=")) {
            String randomType = request.getUrl().substring(request.getUrl().indexOf("=") + 1);
            ServiceLoader<Random> randoms = ServiceLoader.load(Random.class);
            String result = null;

            for (Random random : randoms) {
                Adress annotation = random.getClass().getAnnotation(Adress.class);
                if (annotation.value().equals("number")) {
                    result = random.getRandom();
                }
            }
            if (result == null) {
                return new Response(400, 0);
            }
            else {
                int length = result.getBytes(StandardCharsets.UTF_8).length;
                return new Response(200, length, "text/html", result);
            }
        }

        else return new Response(404, 0);
    }


    private static Response post(Request request) {
        Gson gson = new Gson();
        Post post = gson.fromJson(request.getBody(), Post.class);
        PostFunctions.addPost(post);
        int length = request.getBody().getBytes(StandardCharsets.UTF_8).length;


        return new Response(201, length, "application/json", request.getBody());
    }

    private static Response head(Request request) {
        Response response = get(request);
            return new Response(response.getCode(), 0, response.getContentType());
    }
}
