package com.davi.batch;

import org.springframework.boot.SpringApplication;

public class ApplicationBatchDefault {

	public static void main(String[] args) throws Exception{
		System.exit(SpringApplication.exit(SpringApplication.run(AppConfigService.class, args)));
	}

}
