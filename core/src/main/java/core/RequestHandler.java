package core;

import com.google.gson.Gson;
import persistpkg.Post;
import persistpkg.PostFunctions;

import java.util.List;

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
