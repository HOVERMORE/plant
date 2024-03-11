package hc.plant;


import hc.pojos.Logs;
import hc.pojos.Video;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import hc.service.IVideoService;
import hc.util.constants.VideoConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@SpringBootTest
class PlantApplicationTests {
    @Resource
    private IVideoService videoService;
    @Resource
    private ILogsService logsService;
    /**
     * 测试分块视频
     */
    @Test
    void pickVideo() throws Exception {
        //源文件
        File sourceFile=new File("C:\\Users\\HOVER\\Videos\\动漫\\从零开始的异世界生活49.mp4");
        //分块文件存储路径
        String chunkFilePath="C:\\Users\\HOVER\\Videos\\动漫\\分块存储\\";
        //分块文件大小
        int chunkSize=1024*1024*1;
        //分块文件个数
        int chunkNum=(int)Math.ceil(sourceFile.length()*1.0/chunkSize);
        //使用流从源文件读数据，向分块文件中写数据
        RandomAccessFile raf_r=new RandomAccessFile(sourceFile,"r");
        //缓存区
        byte[] bytes=new byte[1024];
        for (int i = 0; i < chunkNum; i++) {
            File chunkFile=new File(chunkFilePath+i);
            //分块文件写入流
            RandomAccessFile raf_rw=new RandomAccessFile(chunkFile,"rw");
            int len=-1;
            while((len=raf_r.read(bytes))!=-1){
                raf_rw.write(bytes,0,len);
                if(chunkFile.length()>=chunkSize)
                    break;
            }
            raf_rw.close();
        }
        raf_r.close();
    }

    /**
     * 合并分块视频
     * @throws Exception
     */
    @Test
    public void testMerge() throws Exception {
        //块文件目录
        File chunkFolder=new File("C:\\Users\\HOVER\\Videos\\动漫\\分块存储");
        //源文件
        File sourceFile=new File("C:\\Users\\HOVER\\Videos\\动漫\\从零开始的异世界生活49.mp4");
        //合并后的文件
        File mergeFile=new File("C:\\Users\\HOVER\\Videos\\动漫\\合并存储\\从零开始的异世界生活49.mp4");

        //取出所有分块文件
        File[] files=chunkFolder.listFiles();
        List<File> fileList = Arrays.asList(files);

        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName())-Integer.parseInt(o2.getName());
            }
        });
        //向合并文件写的流
        RandomAccessFile raf_rw = new RandomAccessFile(mergeFile, "rw");
        //缓存区
        byte[] bytes=new byte[1024];
        //遍历分块文件，向合并的文件写
        for(File file: fileList){
            //读分块的流
            RandomAccessFile raf_r=new RandomAccessFile(file,"r");
            int len=-1;
            while((len=raf_r.read(bytes))!=-1){
                raf_rw.write(bytes,0,len);
            }
            raf_r.close();
        }
        raf_rw.close();

    }

    @Test
    public void testName(){
        String filepath= VideoConstants.VIDEO_URL;
        System.out.println(filepath);
        String realPath=filepath+"\\123.mp4";
        System.out.println(realPath);
    }
    @Test
    public void creatPath(){
        LocalDateTime currentDate=LocalDateTime.now();
        String year=String.valueOf(currentDate.getYear());
        String month=String.valueOf(currentDate.getMonthValue());
        String filepath= VideoConstants.VIDEO_URL+"\\"+year+"\\"+month;
        Path path = Paths.get(filepath);

        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(filepath);
    }

    @Test
    public void path(){
        String realPath="C:\\Users\\HOVER\\Videos\\开发\\2024\\3\\20240311171625.mp4";
        String playPath=VideoConstants.PLAY_URL+realPath
                .replace(VideoConstants.VIDEO_URL,"")
                .replace("\\", "/");
        System.out.println(playPath);
    }

    @Test
    public void testLogsService(){
        HttpCodeEnum httpCodeEnum=HttpCodeEnum.DATA_NOT_EXIST;
        int code=httpCodeEnum.getCode();
        String str="用户不存在";
        String s1=this.getClass().getSimpleName();
        String s2=httpCodeEnum.getErrorMsg()+"---"+str;
        Logs logs=new Logs()
                .setCode(code)
                        .setService(s1)
                                .setType(s2);
        /**
         *  .setCode(httpCodeEnum.getCode())
         *                 .setService(s1)
         *                 .setDescribe(s2);
         */

        logsService.save(logs);
    }

}
