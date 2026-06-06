package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Задание №3: прогноз Open-Meteo для координат Нижнего Новгорода (56, 44),
 * таблица на экран и в {@code result/forecast.txt}.
 */
public final class Task3 {

    private static final String FORECAST_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain"
                    + "&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";

    private Task3() {
    }

    public static void run(WebDriver webDriver) {
        try {
            webDriver.get(FORECAST_URL);
            String jsonStr = readJsonBody(webDriver);
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(jsonStr);
            JSONObject hourly = (JSONObject) root.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            StringBuilder table = new StringBuilder();
            table.append("|№   |  Дата/время   | Температура | Осадки (мм)  |\n");
            table.append("| -- | ------------- | ----------- | ------------ |\n");
            for (int i = 0; i < times.size(); i++) {
                table.append("| ").append(i + 1).append(" | ");
                table.append(times.get(i)).append(" | ");
                table.append(temperatures.get(i)).append(" | ");
                table.append(rains.get(i)).append(" |\n");
            }
            String text = table.toString();
            System.out.println("Задание 3 — прогноз погоды (Нижний Новгород):");
            System.out.print(text);

            Path out = Paths.get("result", "forecast.txt");
            Files.createDirectories(out.getParent());
            try (BufferedWriter w = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
                w.write(text);
            }
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }

    private static String readJsonBody(WebDriver webDriver) {
        List<WebElement> pre = webDriver.findElements(By.tagName("pre"));
        if (!pre.isEmpty()) {
            return pre.get(0).getText();
        }
        return webDriver.findElement(By.tagName("body")).getText();
    }
}
