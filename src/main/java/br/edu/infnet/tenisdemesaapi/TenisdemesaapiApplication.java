package br.edu.infnet.tenisdemesaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("br.edu.infnet.tenisdemesaapi.model")
@ComponentScan(basePackages = "br.edu.infnet.tenisdemesaapi")
@EnableJpaRepositories("br.edu.infnet.tenisdemesaapi.repository")
public class TenisdemesaapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenisdemesaapiApplication.class, args);
		System.out.println("Hello world");
	}

}
