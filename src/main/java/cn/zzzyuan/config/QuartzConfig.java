package cn.zzzyuan.config;

import cn.zzzyuan.quartz.ElasticSearchTask;
import cn.zzzyuan.quartz.RedisTask;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import java.util.Date;
import java.util.Objects;

@Configuration
public class QuartzConfig {

    @Bean(name = "firstJobDetail")
    public MethodInvokingJobDetailFactoryBean firstJobDetail(RedisTask redisTask) {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setConcurrent(false);
        methodInvokingJobDetailFactoryBean.setTargetObject(redisTask);
        methodInvokingJobDetailFactoryBean.setTargetMethod("persistenceToMysql");
        return methodInvokingJobDetailFactoryBean;
    }

    @Bean(name = "firstTrigger")
    public CronTriggerFactoryBean firstTrigger(@Qualifier("firstJobDetail")MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("firstTrigger");
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(methodInvokingJobDetailFactoryBean.getObject()));
        cronTriggerFactoryBean.setCronExpression("0 0 0 * * ?");
        return cronTriggerFactoryBean;
    }
    @Bean(name = "secondJobDetail")
    public MethodInvokingJobDetailFactoryBean secondJobDetail(RedisTask redisTask) {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setConcurrent(false);
        methodInvokingJobDetailFactoryBean.setTargetObject(redisTask);
        methodInvokingJobDetailFactoryBean.setTargetMethod("redisInit");
        return methodInvokingJobDetailFactoryBean;
    }

    @Bean(name = "secondTrigger")
    public SimpleTriggerFactoryBean secondTrigger(@Qualifier("secondJobDetail")MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setStartDelay(10);
        simpleTriggerFactoryBean.setJobDetail(Objects.requireNonNull(methodInvokingJobDetailFactoryBean.getObject()));
        simpleTriggerFactoryBean.setStartTime(new Date());
        simpleTriggerFactoryBean.setRepeatCount(0);
        return simpleTriggerFactoryBean;
    }

    @Bean(name = "threeJobDetail")
    public MethodInvokingJobDetailFactoryBean threeJobDetail(ElasticSearchTask elasticSearchTask) {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setConcurrent(false);
        methodInvokingJobDetailFactoryBean.setTargetObject(elasticSearchTask);
        methodInvokingJobDetailFactoryBean.setTargetMethod("elasticSearchInit");
        return methodInvokingJobDetailFactoryBean;
    }

    @Bean(name = "threeTrigger")
    public SimpleTriggerFactoryBean threeTrigger(@Qualifier("threeJobDetail")MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setStartDelay(10);
        simpleTriggerFactoryBean.setJobDetail(Objects.requireNonNull(methodInvokingJobDetailFactoryBean.getObject()));
        simpleTriggerFactoryBean.setStartTime(new Date());
        simpleTriggerFactoryBean.setRepeatCount(0);
        return simpleTriggerFactoryBean;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger firstTrigger, Trigger secondTrigger,Trigger threeTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(firstTrigger, secondTrigger,threeTrigger);
        return bean;
    }
}
