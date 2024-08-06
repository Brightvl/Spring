package brightvl.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
public class Page {

    public static void main(String[] args) {
        SpringApplication.run(Page.class, args);
    }
}