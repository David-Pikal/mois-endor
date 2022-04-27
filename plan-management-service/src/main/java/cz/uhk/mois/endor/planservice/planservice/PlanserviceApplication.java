package cz.uhk.mois.endor.planservice.planservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PlanserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanserviceApplication.class, args);
	}

}
