package hc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("hc.mapper")
public class PlantApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantApplication.class, args);
    }

}
