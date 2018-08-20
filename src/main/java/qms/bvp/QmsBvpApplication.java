package qms.bvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class QmsBvpApplication {

	public static void main(String[] args) {
		SpringApplication.run(QmsBvpApplication.class, args);
	}
}
