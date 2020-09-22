package com.example.demo.schedule;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

@Service
@DisallowConcurrentExecution
public class QuartzSampleJob extends QuartzJobBean {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzSampleJob.class);
    private static final String IDENTITY = "QuartzSampleJob";

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("Execute using quartz scheduler!");
    }

    @Bean(name = "QuartzSampleJob")
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(this.getClass())
                .withIdentity(IDENTITY)
                .storeDurably(true)
                .build();
    }

    @Bean(name = "QuartzSampleJobTrigger")
    public CronTriggerFactoryBean jobTrigger(@Qualifier("QuartzSampleJob") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0/5 * * * * ?");
        return factoryBean;
    }
}
