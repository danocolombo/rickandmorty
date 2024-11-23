import com.fasterxml.jackson.databind.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static java.net.http.HttpClient.newHttpClient;

public class Main {
    public static void main(String[] args) {
        String jsonResponse = "";
        try (HttpClient client = newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://rickandmortyapi.com/api/character")) // Replace with actual URL
                    .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                jsonResponse = response.body();

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // String jsonResponse = "...";
        ObjectMapper mapper = new ObjectMapper();

        try {

            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode results = rootNode.path("results");
            List<Buddy> buddies = new ArrayList<>();
            if (results.isArray()) {
                for (JsonNode node : results) {
                    String bId = node.path("id").asText();
                    String bName = node.path("name").asText();
                    String bStatus = node.path("status").asText();
                    String bSpecies = node.path("species").asText();
                    String bType = node.path("type").asText();
                    String bGender = node.path("gender").asText();
                    Buddy buddy = new Buddy(bId, bName, bStatus, bSpecies, bGender
                    );
                    buddies.add(buddy);
                }
            }
            // Use the list of buddies as needed
            // at this point we should have List of Buddy (characters)
            System.out.println("Buddies count: " + buddies.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
