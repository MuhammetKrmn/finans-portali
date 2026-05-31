package com.finansportal.backend;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling           // hafta 5'te scheduler için şimdiden ekle
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	// Uygulama başlayınca virtual thread bilgisini logla
	@Bean
	ApplicationRunner virtualThreadCheck() {
		return args -> {
			Thread current = Thread.currentThread();
			System.out.println("=== Virtual Thread Kontrolü ===");
			System.out.println("Thread: " + current.getName());
			System.out.println("Virtual: " + current.isVirtual());
			System.out.println("================================");
		};
	}
}