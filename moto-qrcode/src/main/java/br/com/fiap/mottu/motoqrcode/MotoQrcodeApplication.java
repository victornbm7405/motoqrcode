package br.com.fiap.mottu.motoqrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MotoQrcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoQrcodeApplication.class, args);
	}

	

}


