package com.pio;

import com.pio.startup.BaseControllerStartupService;
import com.pio.startup.BaseViewStartupService;
import com.pio.startup.ControllerStartupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"configuration", "com.pio"})
public class App {

    private static ControllerStartupService controllerStartupService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        controllerStartupService = new BaseControllerStartupService(new BaseViewStartupService());
        controllerStartupService.run();
    }

}