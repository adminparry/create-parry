package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoApplication {

	@Value("${server.port}")
	private void setPort(String port){
		DemoApplication.port1 = port;
	}
	private static String port1;


	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
		System.out.println("server running at http://localhost:" + port1);
	}

}
