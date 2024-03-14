package hc.util.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GptConstants {
    public static String USER_FILE;
    public static String GPT_FILE;

    @Value("${file.userFile.url}")
    public void getUserFile(String userFile){
        this.USER_FILE=userFile;
    }
    @Value("${file.gptFile.url}")
    public void getGptFile(String gptFile){
        this.GPT_FILE=gptFile;
    }
}
