import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


class WeatherForecast {
    public static void main(String[] args) {
        String latitude = "39.168804";
        String longitude = "-86.536659";
        String temperature = "temperature_2m";
        String timeZone = "EST";
        String unit = "fahrenheit";
        String unitSymbol = "°F";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--latitude") && i + 1 < args.length) {
                latitude = args[i + 1];
            } else if (args[i].equals("--longitude") && i + 1 < args.length) {
                longitude = args[i + 1];
            } else if (args[i].equals("--unit") && i + 1 < args.length) {
                String unitInput = args[i + 1];
                if (unitInput.equalsIgnoreCase("c")) {
                    unit = "celsius";
                    unitSymbol = "°C";
                } else if (unitInput.equalsIgnoreCase("F")) {
                    unit = "fahrenheit";
                    unitSymbol = "°F";
                }
            }
        }

        String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude
                + "&hourly=" + temperature
                + "&temperature_unit=" + unit
                + "&timezone=" + timeZone;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonElement jsonElement = JsonParser.parseString(response.toString());
                JsonArray times = jsonElement.getAsJsonObject().getAsJsonObject("hourly").getAsJsonArray("time");
                JsonArray temperatures = jsonElement.getAsJsonObject().getAsJsonObject("hourly").getAsJsonArray("temperature_2m");

                System.out.println("7-Day Forecast in " + unit.substring(0, 1).toUpperCase() + unit.substring(1) + ":");

                String weatherData = response.toString();

                int timeStartIndex = weatherData.indexOf("time\":[") + "time\":[".length();
                int timeEndIndex = weatherData.indexOf("]", timeStartIndex);
                String longTimeString = weatherData.substring(timeStartIndex, timeEndIndex).replace("\"", "");
                String[] splitTimes = longTimeString.split(",");

                int tempStartIndex = weatherData.indexOf("temperature_2m\":[") + "temperature_2m\":[".length();
                int tempEndIndex = weatherData.indexOf("]", tempStartIndex);
                String longTempString = weatherData.substring(tempStartIndex, tempEndIndex);
                String[] splitTemps = longTempString.split(",");

                String currentDate = "";
                for (int i = 0; i < splitTimes.length; i++) {
                    String hourAndDate = splitTimes[i];
                    String date = hourAndDate.substring(0, 10);
                    String hour = hourAndDate.substring(11);

                    if (!(date.equals(currentDate))) {
                        currentDate = date;
                        System.out.println("Forecast for " + currentDate + ":");
                    }
                    int hourNum = Integer.parseInt(hour.substring(0, 2));
                    if (hourNum % 3 == 0) {
                        System.out.println(hour + ": " + splitTemps[i] + unitSymbol);
                    }
                }
            } else {
                throw new IOException("HTTP GET Request Failed with Response Code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}