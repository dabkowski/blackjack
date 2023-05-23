package configuration;

import com.pio.startup.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ViewStartupService viewStartupServiceInterface() {
        return new BaseViewStartupService();
    }

    @Bean
    public ControllerStartupService controllerStartupServiceInterface(ViewStartupService viewStartupService) {
        return new BaseControllerStartupService(viewStartupService);
    }
}