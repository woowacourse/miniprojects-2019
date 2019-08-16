package com.woowacourse.dsgram.service.oauth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "oauth.client.github")
public class GithubClient {
    private static final Logger log = LoggerFactory.getLogger(GithubClient.class);

    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CODE = "code";

    private String clientId;
    private String clientSecret;

    private String tokenUrl;
    private String userInfoUrl;
    private String userEmailUrl;

    public String getToken(String code) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(tokenUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // TODO: 2019-08-16 MediaType.APPLICATION_JSON_UTF8_VALUE
            connection.setRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Accept", "application/json");

            setBody(code, connection);

            if (connection.getResponseCode() == HttpStatus.OK.value()) {
                return getJsonElement(connection)
                        .getAsJsonObject().get("access_token").getAsString();
            }

        } catch (IOException e) {
            log.error("Fail! - Token 받아오기 실패", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        throw new RuntimeException("연결 실패?");
    }

    private void setBody(String code, HttpURLConnection connection) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLIENT_ID, clientId);
        jsonObject.addProperty(CLIENT_SECRET, clientSecret);
        jsonObject.addProperty(CODE, code);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonObject.toString().getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonElement getJsonElement(HttpURLConnection connection) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            return new JsonParser().parse(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("뭘 적어야하지...?");
    }

    public String getUserEmail(String accessToken) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(userEmailUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "token " + accessToken);
            connection.addRequestProperty("Accept", "application/json");

            return getJsonElement(connection).getAsJsonArray().get(0)
                    .getAsJsonObject().get("email").getAsString();
        } catch (IOException e) {
            log.error("Fail! - Email 가져오기", e);
        }
        throw new RuntimeException("뭘 적어야하지...?");
    }

    public JsonElement getUserInformation(String accessToken) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(userInfoUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", String.format("token %s", accessToken));
            connection.addRequestProperty("Accept", "application/json");
            return getJsonElement(connection);
        } catch (IOException e) {
            log.error("Fail! - User Information 가져오기", e);
        }
        throw new RuntimeException("뭘 적어야하지...?");
    }
}

