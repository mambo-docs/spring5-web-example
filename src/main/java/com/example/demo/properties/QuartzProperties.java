package com.example.demo.properties;

import com.example.demo.bean.JobStoreType;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class QuartzProperties {

    private JobStoreType jobStoreType;
    private String schedulerName;
    private int startUpDelay;
    private boolean autoStartup;
    private boolean overwriteExistingJobs;
    private boolean waitForJobsToCompleteOnShutdown;
    private Properties properties;

    public QuartzProperties(Environment environment) throws IOException {
        this.jobStoreType = environment.getProperty("spring.quartz.job-store-type", JobStoreType.class, JobStoreType.MEMORY);
        this.schedulerName = environment.getProperty("spring.quartz.scheduler-name", String.class, "quartz-scheduler");
        this.startUpDelay = environment.getProperty("spring.quartz.start-up-delay", Integer.class, 0);
        this.autoStartup = environment.getProperty("spring.quartz.auto-startup", Boolean.class, false);
        this.overwriteExistingJobs = environment.getProperty("spring.quartz.overwrite-existing-jobs", Boolean.class, false);
        this.waitForJobsToCompleteOnShutdown = environment.getProperty("spring.quartz.wait-for-jobs-to-complete-on-shutdown", Boolean.class, false);

        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        this.properties = propertiesFactoryBean.getObject();
    }

    public JobStoreType getJobStoreType() {
        return jobStoreType;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public int getStartUpDelay() {
        return startUpDelay;
    }

    public boolean isAutoStartup() {
        return autoStartup;
    }

    public boolean isOverwriteExistingJobs() {
        return overwriteExistingJobs;
    }

    public boolean isWaitForJobsToCompleteOnShutdown() {
        return waitForJobsToCompleteOnShutdown;
    }

    public Properties getProperties() {
        return properties;
    }
}
