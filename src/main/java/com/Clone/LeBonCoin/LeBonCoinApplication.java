package com.Clone.LeBonCoin;

import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class LeBonCoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeBonCoinApplication.class, args);
	}


}
