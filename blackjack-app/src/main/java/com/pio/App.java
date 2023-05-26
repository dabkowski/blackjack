package com.pio;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        Application.launch(ViewRunner.class);
    }
}