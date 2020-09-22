package com.example.demo;

import org.apache.catalina.Context;
import ch.qos.logback.access.tomcat.LogbackValve;
import org.apache.catalina.Pipeline;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

public class SpringApplication {
    private static final Logger LOG = LoggerFactory.getLogger(SpringApplication.class);

    public static void main(String[] args) throws Exception {
        ClassPathResource resource = new ClassPathResource("application.properties", SpringApplication.class.getClassLoader());
        Properties properties = new Properties();
        properties.load(resource.getInputStream());

        int port = Integer.parseInt(properties.getProperty("server.port", "8080"));
        String webapp = properties.getProperty("server.webapp", "src/main/webapp");
        String contextPath = properties.getProperty("server.servlet.context-path", "");
        String applicationName = properties.getProperty("spring.application.name", "SpringApplication");

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("out/webapp");
        Connector connector = tomcat.getConnector();
        connector.setURIEncoding(StandardCharsets.UTF_8.displayName());

        Context context = tomcat.addWebapp(contextPath, new File(webapp).getAbsolutePath());
        StandardJarScanner jarScanner = (StandardJarScanner) context.getJarScanner();
        jarScanner.setScanManifest(false);

        tomcat.addWebapp(contextPath, new File(webapp).getAbsolutePath());

        Pipeline pipeline = tomcat.getHost().getPipeline();
        LogbackValve logbackValve = new LogbackValve();
        logbackValve.setFilename("logback-access.xml");
        pipeline.addValve(logbackValve);

        tomcat.setPort(port);
        tomcat.start();
        LOG.info("{} started {}", applicationName, new Date());
        tomcat.getServer().await();
    }
}
