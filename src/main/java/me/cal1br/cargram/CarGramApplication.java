package me.cal1br.cargram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarGramApplication {

    public static void main(String[] args) {
        System.out.println(System.getenv("CLOUDINARY_URL"));
        SpringApplication.run(CarGramApplication.class, args);
    }

}
