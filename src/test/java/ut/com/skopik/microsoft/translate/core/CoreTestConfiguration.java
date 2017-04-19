package ut.com.skopik.microsoft.translate.core;

import com.skopik.microsoft.translate.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@EnableCaching
@SpringBootApplication
@ComponentScan
@Import(value = CoreConfiguration.class)
public class CoreTestConfiguration extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CoreTestConfiguration.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CoreTestConfiguration.class);
    }

    private static Class<CoreTestConfiguration> applicationClass = CoreTestConfiguration.class;

}
