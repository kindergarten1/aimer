package com.cilcil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = {"com.cilcil"})
//@MapperScan("com.cilcil.modules.*")
public class CilcilClientApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(CilcilClientApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        System.out.println("\n----------------------------------------------------------\n\t" +
                "Application ClientApplication is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port  + "/swagger-ui/index.html\n\t" +
                "Doc: \t\thttp://" + ip + ":" + port + "/doc.html\n" +
                "----------------------------------------------------------");
    }

}
