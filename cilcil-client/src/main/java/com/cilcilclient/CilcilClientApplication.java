package com.cilcilclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CilcilClientApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(CilcilClientApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
//		AttachPathSerialize.REQUEST_DIR = env.getProperty("file.request-dir");
        System.out.println("\n----------------------------------------------------------\n\t" +
                "Application ClientApplication is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + "/\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port  + "/swagger-ui/index.html\n\t" +
                "Doc: \t\thttp://" + ip + ":" + port + "/v2/api-docs\n" +
                "----------------------------------------------------------");
    }

}
