package com.carcenter.car_center_api;

import com.carcenter.car_center_api.config.AwsProperties;
import com.carcenter.car_center_api.config.TwilioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AwsProperties.class, TwilioProperties.class})
public class CarCenterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarCenterApiApplication.class, args);
	}

}
