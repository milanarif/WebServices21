package core;

import com.google.gson.Gson;
import persistpkg.Post;
import persistpkg.PostFunctions;
import spi.Adress;
import spi.Random;

import java.util.List;
import java.util.ServiceLoader;

public class RequestHandler {

    public static String handleRequest(Request request) {
        return switch (request.getRequestType()) {
            case GET -> get(request);
            case POST -> post(request);
            case HEAD -> head(request);
        };
    }

    private static String get(Request request) {
        if (request.getUrl().equals("/")) {
            return null;
        }
        else if (request.getUrl().equals("/getAll")){
            List<Post> posts = PostFunctions.getAllPosts();
            Gson gson = new Gson();
            return gson.toJson(posts);
        }
        else if (request.getUrl().startsWith("/?id=")) {
            Integer targetId = Integer.parseInt(request.getUrl().replaceAll("[^\\d.]", ""));
            Post post = PostFunctions.getPost(targetId);
            Gson gson = new Gson();
            return gson.toJson(post);
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
            return result;
        }
        else return null;
    }

    //Todo
    private static String post(Request request) {
        Gson gson = new Gson();
        Post post = gson.fromJson(request.getBody(), Post.class);
        PostFunctions.addPost(post);

        return null;
    }

    //Todo
    private static String head(Request request) {
        return null;
    }
}
