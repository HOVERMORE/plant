package hc.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
public class FileUtil {

    public static String createFiles(String fileUrl){
        LocalDateTime currentDate=LocalDateTime.now();
        String year=String.valueOf(currentDate.getYear());
        String month=String.valueOf(currentDate.getMonthValue());
        String filepath= fileUrl +"\\"+year+"\\"+month;
        Path path = Paths.get(filepath);
        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error(fileUrl+":"+"文件创建失败");
                return null;
            }
        }
        return filepath;
    }
    public static String saveText(String fileName,String content,String filepath){
        File file = new File(filepath, fileName + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            log.info("内容已成功写入文件：" + file.getAbsolutePath());
        } catch (IOException e) {
            log.error("发生错误： " + e.getMessage());
            return null;
        }
        return file.getPath();
    }
    public static boolean saveVideo(MultipartFile multipartFile, String filepath){
        try {
            multipartFile.transferTo(new File(filepath));
        } catch (IOException e) {
          log.error("视频上传失败"+e.getMessage());
          return false;
        }
        log.info("视频上传成功");
        return true;
    }
    public static String getContent(String filepath){
        String content ="";
        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            content = bufferedReader.lines().collect(Collectors.joining("\n"));
            bufferedReader.close();
            //System.out.println(content);
        } catch (IOException e) {
            log.error("文本获取失败"+e.getMessage());
            return "";
        }
        return content;
    }
}
