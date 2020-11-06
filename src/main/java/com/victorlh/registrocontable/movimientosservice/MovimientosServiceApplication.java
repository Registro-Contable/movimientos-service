package com.victorlh.registrocontable.movimientosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = { @ComponentScan("com.victorlh.registrocontable.common.securitycommon") })
public class MovimientosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovimientosServiceApplication.class, args);
	}

}
