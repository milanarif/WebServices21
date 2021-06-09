package core;

import com.google.gson.Gson;
import persistpkg.Post;
import persistpkg.PostFunctions;
import spi.Adress;
import spi.Random;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;

public class RequestHandler {

    public static Response handleRequest(Request request) throws IOException{
        return switch (request.getRequestType()) {
            case GET -> get(request);
            case POST -> post(request);
            case HEAD -> head(request);
        };
    }
    private static Response get(Request request) throws IOException {
        if (request.getUrl().equals("/")) {
            File f = Path.of("/web/index.html").toFile();
            if (!(f.exists() && !f.isDirectory())) {
                return new Response("404 Not Found", 0);
            }
            else {
                byte[] responseBody =  Files.readAllBytes(f.toPath());
                return new Response("200 OK", responseBody.length, Files.probeContentType(f.toPath()), responseBody);
            }
        }

        if (request.getUrl().equals("/png")) {
            File f = Path.of("/web/welcome.png").toFile();
            if (!(f.exists() && !f.isDirectory())) {
                return new Response("404 Not Found", 0);
            }
            else {
                byte[] responseBody =  Files.readAllBytes(f.toPath());
                return new Response("200 OK", responseBody.length, Files.probeContentType(f.toPath()), responseBody);
            }
        }

        if (request.getUrl().equals("/pdf")) {
            File f = Path.of("/web/sample.pdf").toFile();
            if (!(f.exists() && !f.isDirectory())) {
                return new Response("404 Not Found", 0);
            }
            else {
                byte[] responseBody =  Files.readAllBytes(f.toPath());
                return new Response("200 OK", responseBody.length, Files.probeContentType(f.toPath()), responseBody);
            }
        }

        if (request.getUrl().equals("/css")) {
            File f = Path.of("/web/w3.css").toFile();
            if (!(f.exists() && !f.isDirectory())) {
                return new Response("404 Not Found", 0);
            }
            else {
                byte[] responseBody =  Files.readAllBytes(f.toPath());
                return new Response("200 OK", responseBody.length, Files.probeContentType(f.toPath()), responseBody);
            }
        }

        if (request.getUrl().equals("/js")) {
            File f = Path.of("/web/HelloWorld.js").toFile();
            if (!(f.exists() && !f.isDirectory())) {
                return new Response("404 Not Found", 0);
            }
            else {
                byte[] responseBody =  Files.readAllBytes(f.toPath());
                return new Response("200 OK", responseBody.length, Files.probeContentType(f.toPath()), responseBody);
            }
        }

        else if (request.getUrl().equals("/getAll")){
            List<Post> posts = PostFunctions.getAllPosts();
            if (posts != null) {
                Gson gson = new Gson();
                byte[] responseBody = gson.toJson(posts).getBytes(StandardCharsets.UTF_8);

                return new Response("200 OK", responseBody.length, "application/json", responseBody);
            }

            else {
                return new Response("404 Not Found", 0);
            }
        }

        else if (request.getUrl().startsWith("/?id=")) {
            Integer targetId = Integer.parseInt(request.getUrl().replaceAll("[^\\d.]", ""));
            Post post = PostFunctions.getPost(targetId);
            if (post != null) {
                Gson gson = new Gson();
                byte[] responseBody = gson.toJson(post).getBytes(StandardCharsets.UTF_8);

                return new Response("200 OK", responseBody.length, "application/json", responseBody);
            }
            else {
                return new Response("404 Not Found", 0);
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
                return new Response("404 Post Not Found", 0);
            }
            else {
                byte[] responseBody = result.getBytes(StandardCharsets.UTF_8);
                return new Response("200 OK", responseBody.length, "text/plain", responseBody);
            }
        }

        else return new Response("404 Not Found", 0);
    }

    private static Response post(Request request) {
        Gson gson = new Gson();
        Post post = gson.fromJson(request.getBody(), Post.class);
        PostFunctions.addPost(post);
        int length = request.getBody().getBytes(StandardCharsets.UTF_8).length;


        return new Response("201 Created", length, "application/json", request.getBody().getBytes(StandardCharsets.UTF_8));
    }

    private static Response head(Request request) throws IOException{
        Response response = get(request);
        assert response != null;
        return new Response(response.getCode(), 0, response.getContentType());
    }
}
