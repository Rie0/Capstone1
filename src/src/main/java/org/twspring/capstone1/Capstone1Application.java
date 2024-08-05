package org.twspring.capstone1;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.twspring.capstone1.Model.User;
import org.twspring.capstone1.Service.Impl.UserService;

@SpringBootApplication
public class Capstone1Application {

    public static void main(String[] args) {
        SpringApplication.run(Capstone1Application.class, args);
    }

}
