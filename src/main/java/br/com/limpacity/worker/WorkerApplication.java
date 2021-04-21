package br.com.limpacity.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorkerApplication {

	WorkerApplication() {

	}

	public static void main(String[] args) {
		SpringApplication.run(WorkerApplication.class, args);
	}

}
