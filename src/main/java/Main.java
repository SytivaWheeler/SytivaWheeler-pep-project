import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.SocialMediaController;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();

        app.start(8080);

        // HttpClient webClient = HttpClient.newHttpClient();
        // ObjectMapper objectMapper;

        // HttpRequest postRequest = HttpRequest.newBuilder()
        //         .uri(URI.create("http://localhost:8080/register"))
        //         .POST(HttpRequest.BodyPublishers.ofString("{" +
        //                 "\"username\": \"user\", " +
        //                 "\"password\": \"password\" }"))
        //         .header("Content-Type", "application/json")
        //         .build();
        // HttpResponse response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        // int status = response.statusCode();
        
    }
}
