import java.net.http.*;
import java.net.URI;
import java.io.IOException;
import org.json.JSONObject;

public class RestApiClient {
    private static final String API_URL = "https://api.weatherapi.com/v1/current.json?key=YOUR_API_KEY&q=London";

    public static void main(String[] args) {
        try {
            String response = sendGetRequest(API_URL);
            parseAndDisplayResponse(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to send GET request
    public static String sendGetRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Method to parse JSON and display structured data
    public static void parseAndDisplayResponse(String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject location = jsonObject.getJSONObject("location");
        JSONObject current = jsonObject.getJSONObject("current");

        System.out.println("Weather Information");
        System.out.println("-------------------");
        System.out.println("Location: " + location.getString("name") + ", " + location.getString("country"));
        System.out.println("Temperature: " + current.getDouble("temp_c") + "°C");
        System.out.println("Condition: " + current.getJSONObject("condition").getString("text"));
    }
}
