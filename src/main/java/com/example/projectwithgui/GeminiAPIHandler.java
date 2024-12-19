//package com.example.projectwithgui;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.URL;
//import java.net.URLConnection;
//
//public class GeminiAPIHandler {
//    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
//
//    private String apiKey;
//
//    public GeminiAPIHandler(String apiKey) {
//        this.apiKey = apiKey;
//    }
//
//    // Method to get the response from the API
//    public String getResponse(String prompt) throws IOException {
//        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";
//
//        URL url = new URL(API_URL + "?key=" + apiKey);
//        URLConnection connection = url.openConnection();
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "application/json");
//        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
//            writer.write(jsonBody);
//            writer.flush();
//        }
//        StringBuilder response = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//        }
//        return extractGeneratedText(response.toString());
//    }
//    //my device isnt accepting the dependency so i am generating the raw responce
//    private String extractGeneratedText(String responseJson) {
//        try {
//            JsonObject jsonResponse = JsonParser.parseString(responseJson).getAsJsonObject();
//
//            // Log the raw JSON for debugging purposes
//            System.out.println("Raw Response: " + jsonResponse.toString());
//
//            // Check if the "contents" field exists and is not null
//            if (jsonResponse.has("contents") && !jsonResponse.getAsJsonArray("contents").isEmpty()) {
//                // Extract the first "content" item
//                JsonObject content = jsonResponse.getAsJsonArray("contents").get(0).getAsJsonObject();
//
//                // Check if the "parts" field exists and is not null
//                if (content.has("parts") && !content.getAsJsonArray("parts").isEmpty()) {
//                    JsonObject part = content.getAsJsonArray("parts").get(0).getAsJsonObject();
//                    return part.get("text").getAsString(); // Return the generated text
//                } else {
//                    return "Error: No response parts in the API response.";
//                }
//            } else {
//                return "Error: No contents in the API response.";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error: Failed to parse the response.";
//        }
//    }
//    public String getRawResponse(String prompt) throws IOException {
//        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";
//
//        // Prepare the URL and open a connection
//        URL url = new URL(API_URL + "?key=" + apiKey);
//        URLConnection connection = url.openConnection();
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "application/json");
//
//        // Send the POST request
//        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
//            writer.write(jsonBody);
//            writer.flush();
//        }
//
//        // Read the response
//        StringBuilder response = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//        }
//
//        // Return the raw response (unprocessed JSON)
//        return response.toString();
//    }
//}

package com.example.projectwithgui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class GeminiAPIHandler {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    private String apiKey;

    public GeminiAPIHandler(String apiKey) {
        this.apiKey = apiKey;
    }

    // Method to get the response from the API
    public String getResponse(String prompt) throws IOException {
        String rawResponse = getRawResponse(prompt);
        String formattedResponse = extractGeneratedText(rawResponse);
        return "Chatbot: " + formattedResponse;
    }

    // Extract the generated text from the raw JSON response
    private String extractGeneratedText(String responseJson) {
        try {
            JsonObject jsonResponse = JsonParser.parseString(responseJson).getAsJsonObject();

            // Log the raw JSON for debugging purposes
            System.out.println("Raw Response: " + jsonResponse.toString());

            // Extract "candidates" array
            if (jsonResponse.has("candidates") && !jsonResponse.getAsJsonArray("candidates").isEmpty()) {
                JsonObject firstCandidate = jsonResponse.getAsJsonArray("candidates").get(0).getAsJsonObject();

                // Extract "content" object
                if (firstCandidate.has("content")) {
                    JsonObject content = firstCandidate.getAsJsonObject("content");

                    // Extract "parts" array
                    if (content.has("parts") && !content.getAsJsonArray("parts").isEmpty()) {
                        JsonObject firstPart = content.getAsJsonArray("parts").get(0).getAsJsonObject();
                        return firstPart.get("text").getAsString().trim(); // Return the generated text
                    }
                }
            }
            return "Error: No valid response found in the API response.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Failed to parse the response.";
        }
    }

    // Get the raw JSON response from the API
    public String getRawResponse(String prompt) throws IOException {
        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";

        // Prepare the URL and open a connection
        URL url = new URL(API_URL + "?key=" + apiKey);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        // Send the POST request
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(jsonBody);
            writer.flush();
        }

        // Read the response
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        // Return the raw response (unprocessed JSON)
        return response.toString();
    }
}
