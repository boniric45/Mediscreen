package fr.mediscreen.rapport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableFeignClients("fr.mediscreen.rapport")
public class RapportApplication {

    public static void main(String[] args) {
        SpringApplication.run(RapportApplication.class, args);
    }
}
