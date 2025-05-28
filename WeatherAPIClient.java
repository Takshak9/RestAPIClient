import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class WeatherAPIClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Taking API Key and City Name as input
        System.out.print("Enter your OpenWeatherMap API Key: ");
        String apiKey = scanner.nextLine();

        System.out.print("Enter the city name: ");
        String city = scanner.nextLine();

        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        try {
            // Create URL object
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check HTTP response status
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Read response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Displaying data in structured format
                System.out.println("\nWeather Data for " + city + ":");
                System.out.println("Temperature: " + jsonResponse.getJSONObject("main").getDouble("temp") + " K");
                System.out.println("Weather: " + jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description"));
                System.out.println("Humidity: " + jsonResponse.getJSONObject("main").getInt("humidity") + "%");
            } else {
                System.err.println("Error: Unable to fetch weather data. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
