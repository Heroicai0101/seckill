package com.cgx.marketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.system.ApplicationPidFileWriter;

import java.io.File;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ApplicationStarter {

	public static void main(String[] args) {
		File pidFile = new File("app.pid");
		pidFile.setWritable(true, true);
		pidFile.setExecutable(false);
		pidFile.setReadable(true);

		SpringApplication application = new SpringApplication(ApplicationStarter.class);
		application.addListeners(new ApplicationPidFileWriter(pidFile));
		application.run(args);
	}

}