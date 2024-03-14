package hc.util;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import java.util.Scanner;

@Slf4j
public class GptChatClient {
    public static String getChatGptResponse(String content) {
        String url = "https://daily.w-l-h.xyz/v1/chat/completions";
        String apiKey = "sk-2oaXBKQEGsZHEWjQ6f09D9C070F54e138d99Bf5a061d5a68";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        String json = "{\"model\":\"gpt-3.5-turbo\"," +
                "\"messages\":[{\"role\":\"system\",\"content\":\"你是一个强大的助手\"}," +
                "{\"role\":\"user\",\"content\":\""
                + content + "\"}]}";

        RequestBody body = RequestBody.create(mediaType, json);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        String responseBody = "";
        try {

            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            //System.out.println("json字符串为："+responseBody);
        } catch (Exception e) {
            log.error("gpt-api,发生错误" + e.getMessage());
        }

        return extractContent(responseBody);
    }

    public static String extractContent(String jsonData) {
        if (StrUtil.isBlank(jsonData))
            return "返回值为空";
        Gson gson = new GsonBuilder().create();
        String[] lines = jsonData.split("\n");
        StringBuilder contentBuilder = new StringBuilder();
        if (lines.length >= 2) {
            for (String line : lines) {
                if (line.startsWith("data: ")) {
                    String jsonLine = line.substring(6); // Remove "data: " prefix
                    if (jsonLine.equals("[DONE]"))
                        break;
                    JsonObject jsonObject = gson.fromJson(jsonLine, JsonObject.class);
                    JsonArray choices = jsonObject.getAsJsonArray("choices");
                    if (choices != null && choices.size() > 0) {
                        JsonObject choice = choices.get(0).getAsJsonObject();
                        JsonObject delta = choice.getAsJsonObject("delta");
                        if (delta != null && delta.size() > 0) {
                            String content = delta.get("content").getAsString();
                            if (StrUtil.isBlank(content))
                                continue;
                            contentBuilder.append(content);
                        }
                    }
                }
            }
            return contentBuilder.toString();
        } else {
            JSONObject json = JSONUtil.parseObj(lines[0]);
            String content = json.getByPath("choices[0].message.content", String.class);
            return content;
        }
    }


    public static void main(String[] args) throws Exception {
        loop:
        while (true) {
            Scanner sc = new Scanner(System.in);
            String question = sc.next();
            if (question.contains("EOF"))
                break loop;
            String resp = getChatGptResponse(question);
            System.out.println("解析后的字符串：" + resp);

        }
    }
}
