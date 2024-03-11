package hc.util.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoConstants {
    public static String VIDEO_URL;
    public static String PLAY_URL;

    @Value("${video.url}")
    private void setVideoUrl(String videoUrl){
        VIDEO_URL=videoUrl;
    }

    @Value("${video.playUrl}")
    private void setPlayUrl(String playUrl){
        PLAY_URL=playUrl;
    }
}
