package chan.shop.viewservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "chan.shop")
@EnableJpaRepositories(basePackages = "chan.shop")
public class ViewServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViewServiceApplication.class, args);
    }
}
