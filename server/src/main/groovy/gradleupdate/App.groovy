package gradleupdate

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

@EnableFeignClients
@EnableOAuth2Client
@SpringBootApplication
class App {
    static void main(String[] args) {
        SpringApplication.run(App, args)
    }
}
