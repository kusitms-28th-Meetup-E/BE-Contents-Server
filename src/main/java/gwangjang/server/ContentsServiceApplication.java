package gwangjang.server;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableDiscoveryClient
@EnableScheduling
public class ContentsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentsServiceApplication.class, args);
	}

}
