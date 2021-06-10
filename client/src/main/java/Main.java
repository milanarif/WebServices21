import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();

        while (true) {
            menu();
            switch (Input.inputInt()) {
                case 1:
                    var getAll = HttpRequest.newBuilder(
                            URI.create("http://localhost:5050/getAll"))
                            .build();
                    System.out.println("\n" + client.send(getAll, HttpResponse.BodyHandlers.ofString()).body());
                    break;
                case 2:
                    System.out.print("Enter ID: ");
                    Integer id = Input.inputInt();
                    var get = HttpRequest.newBuilder(
                            URI.create("http://localhost:5050/?id=" + id.toString()))
                            .build();
                    System.out.println("\n" + client.send(get, HttpResponse.BodyHandlers.ofString()).body());
                    break;
                case 3:
                    var getRandom = HttpRequest.newBuilder(
                            URI.create("http://localhost:5050/?random=number"))
                            .build();
                    System.out.println("\n" + client.send(getRandom, HttpResponse.BodyHandlers.ofString()).body());
                    break;
                case 4:
                    System.out.print("\nEnter Subject: ");
                    String subject = Input.inputString();
                    System.out.print("\nEnter Author: ");
                    String author = Input.inputString();
                    System.out.print("\nEnter text: ");
                    String text = Input.inputString();
                    var addPost = HttpRequest.newBuilder(
                            URI.create("http://localhost:5050/"))
                            .POST(HttpRequest.BodyPublishers.ofString("{subject:"+ subject +", author:" + author + ", text:" + text + "}\r\n"))
                            .build();
                    System.out.println("\n" + client.send(addPost, HttpResponse.BodyHandlers.ofString()).body());
                    break;

                case 5:
                    System.out.print("Enter path: ");
                    String path = Input.inputString();
                    var custom = HttpRequest.newBuilder(
                            URI.create("http://localhost:5050/" + path))
                            .build();
                    System.out.println("\n" + client.send(custom, HttpResponse.BodyHandlers.ofString()).body());
                    break;

                case 9:
                    System.exit(0);
                    break;
            }
        }
    }
    public static void menu() {
        System.out.println("\n1. Get All");
        System.out.println("2. Get ID");
        System.out.println("3. Get Random Number");
        System.out.println("4. Add Post");
        System.out.println("5. Custom");
        System.out.println("9. Exit");
        System.out.print("Choice: ");
    }

}
