package com.example.demo.config;

import com.example.demo.properties.QuartzProperties;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;
import java.util.List;

/**
 * Scheduling Configuration
 *
 * @author mambo
 */
@EnableScheduling
@ComponentScan({"com.example.demo.schedule"})
@Configuration
public class ScheduleConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }

    @Bean
    public JobFactory quartzJobFactory(AutowireCapableBeanFactory beanFactory) {
        return new SpringBeanJobFactory(){
            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                Object job = super.createJobInstance(bundle);
                beanFactory.autowireBean(job);
                return job;
            }
        };
    }

    @Bean
    public TaskExecutor quartzThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(25);
        return taskExecutor;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, List<Trigger> triggers) throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setApplicationContext(applicationContext);
        schedulerFactoryBean.setTaskExecutor(quartzThreadPoolTaskExecutor());
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setTriggers(triggers.toArray(new Trigger[triggers.size()]));

        Environment environment = applicationContext.getEnvironment();
        QuartzProperties quartzProperties = new QuartzProperties(environment);
        schedulerFactoryBean.setSchedulerName(quartzProperties.getSchedulerName());
        schedulerFactoryBean.setStartupDelay(quartzProperties.getStartUpDelay());
        schedulerFactoryBean.setAutoStartup(quartzProperties.isAutoStartup());
        schedulerFactoryBean.setOverwriteExistingJobs(quartzProperties.isOverwriteExistingJobs());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(quartzProperties.isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setQuartzProperties(quartzProperties.getProperties());
        return schedulerFactoryBean;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}