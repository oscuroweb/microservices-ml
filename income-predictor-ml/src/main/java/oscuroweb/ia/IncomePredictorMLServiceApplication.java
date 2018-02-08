package oscuroweb.ia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IncomePredictorMLServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncomePredictorMLServiceApplication.class, args);
	}
}
