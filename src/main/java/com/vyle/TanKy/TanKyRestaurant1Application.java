package com.vyle.TanKy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;


@EnableEncryptableProperties
@SpringBootApplication
public class TanKyRestaurant1Application {

	public static void main(String[] args) {
		SpringApplication.run(TanKyRestaurant1Application.class, args);
	}
	
}
