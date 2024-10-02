package com.mycompany.app;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class App {

    private static final Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        ObjectMapper objectMapper = new ObjectMapper();

        String filepath = "./weather-data.json";

        WeatherData data = null;

        // create the WeatherData object from the JSON file
        try {
            File file = new File(filepath);
            boolean exists = file.exists();

            if (!exists) {
                System.out.println("File does not exist");
                return;
            }

            data = objectMapper.readValue(file, WeatherData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        boolean validQuery = false;
        boolean tryAgain = true;

        while (!validQuery || tryAgain) {
            // prompt the user for a JSONPath query
            String query = promptForJsonPathQuery();

            // print the result of the query
            try {
                String json = objectMapper.writeValueAsString(data);

                Object result = parseJsonPath(json, query);

                printResult(result);

                validQuery = true;

                tryAgain = wantToTryAgain();
            } catch (Exception e) {
                System.out.println("Invalid query. Please try again.");
                tryAgain = true;
                validQuery = false;
            }
        }
    }

    // prompt the user for a JSONPath query
    public static String promptForJsonPathQuery() {
        System.out.print("Enter a JSONPath query: ");
        String query = reader.nextLine();

        return query;
    }

    public static Object parseJsonPath(String json, String query) {
        Object result = JsonPath.parse(json).read(query);
        return result;
    }

    public static void printResult(Object result) {
        if (result instanceof List) {
            List<?> listResult = (List<?>) result;
            for (Object item : listResult) {
                System.out.println(item);
            }
        } else if (result instanceof Map) {
            Map<?, ?> mapResult = (Map<?, ?>) result;
            System.out.println(mapResult);
        } else {
            System.out.println(result);
        }
    }

    public static boolean wantToTryAgain() {
        System.out.print("Do you want to try again? (y/n): ");
        String response = reader.nextLine();

        return response.equals("y");
    }
}