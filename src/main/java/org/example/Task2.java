package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Задание №2: JSON с api.ipify.org, извлечение IPv4-адреса.
 */
public final class Task2 {

    private Task2() {
    }

    public static void run(WebDriver webDriver) {
        try {
            webDriver.get("https://api.ipify.org/?format=json");
            String jsonStr = readJsonBody(webDriver);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            String ip = (String) obj.get("ip");
            System.out.println("Задание 2 — IPv4-адрес клиента:");
            System.out.println(ip);
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
