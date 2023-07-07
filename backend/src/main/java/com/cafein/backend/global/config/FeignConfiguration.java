package com.cafein.backend.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cafein.backend.global.error.FeignClientExceptionErrorDecoder;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@Configuration
@EnableFeignClients(basePackages = "com.cafein")
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignClientExceptionErrorDecoder();
	}

	@Bean
	public Retryer retryer() {
		return new Retryer.Default(1000, 2000, 3);
	}
}
