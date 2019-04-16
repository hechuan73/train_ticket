package com.trainticket.verificationcode;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableSwagger2
public class VerificationCodeApplication{

	public static void main(String[] args) {
		SpringApplication.run(VerificationCodeApplication.class, args);
	}
}
