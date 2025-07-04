package nl.grapjeje.lock;

import java.util.ArrayList;
import java.util.List;

public record Account(
        String name,
        String username,
        String password,
        String notes,
        String type,
        String url
) {
    public String toJson() {
        return "{"
                + "\"name\":\"" + escapeJson(name) + "\","
                + "\"username\":\"" + escapeJson(username) + "\","
                + "\"password\":\"" + escapeJson(password) + "\","
                + "\"notes\":\"" + escapeJson(notes) + "\","
                + "\"type\":\"" + escapeJson(type) + "\","
                + "\"url\":\"" + escapeJson(url) + "\""
                + "}";
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public static Account fromJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        String[] parts = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String name = "", username = "", password = "", notes = "", type = "", url = "";

        for (String part : parts) {
            String[] keyValue = part.split(":", 2);
            if (keyValue.length != 2) continue;
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim();
            if (value.startsWith("\"") && value.endsWith("\""))
                value = value.substring(1, value.length() - 1);
            value = value.replace("\\\"", "\"").replace("\\\\", "\\");

            switch (key) {
                case "name" -> name = value;
                case "username" -> username = value;
                case "password" -> password = value;
                case "notes" -> notes = value;
                case "type" -> type = value;
                case "url" -> url = value;
            }
        }
        return new Account(name, username, password, notes, type, url);
    }

    public static String listToJson(List<Account> accounts) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < accounts.size(); i++) {
            sb.append(accounts.get(i).toJson());
            if (i < accounts.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public static List<Account> JsonToList(String json) {
        json = json.trim();
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]")) json = json.substring(0, json.length() - 1);

        List<Account> accounts = new ArrayList<>();
        String[] items = json.split("(?<=\\}),(?=\\{)");

        for (String item : items) {
            accounts.add(Account.fromJson(item));
        }
        return accounts;
    }
}
