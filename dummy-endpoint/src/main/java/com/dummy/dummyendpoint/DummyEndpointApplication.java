package com.dummy.dummyendpoint;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class DummyEndpointApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(DummyEndpointApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext run = SpringApplication.run(DummyEndpointApplication.class, args);
		Environment env = run.getEnvironment();
		String serverPort = env.getProperty("server.port");

		LOGGER.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\thttp://localhost:{}\n\t" +
						"Swagger: \thttp://localhost:{}/swagger-ui/index.html\n\t" +
						"External: \thttp://{}:{}\n\t" +
						"----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				serverPort,
				serverPort,
				InetAddress.getLocalHost().getHostAddress(), serverPort);

	}

}
