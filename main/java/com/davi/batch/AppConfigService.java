package com.davi.batch;

import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.davi.batch.auto.model.ItemExample;
import com.davi.batch.entity.informix.EntityLegacyDb01Example;
import com.davi.batch.listener.JobCompletionNotificationListener;

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.davi.batch")
public class AppConfigService extends DefaultBatchConfigurer {
	
	@Value("${chunkSize}") 
	private int chunkSize;
	
	@Value("${skipLimitException}")
	private int skipLimitException;
	
	@Autowired private JobBuilderFactory jobBuilderFactory;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	@Autowired private MapJobRepositoryFactoryBean repositoryFactoryBean;
	
	@Bean
    public Job job(JobCompletionNotificationListener listener, 
    		@Qualifier("stepDefault") Step stepDefault,
    		@Qualifier("defaultTasklet") Step defaultTasklet) {
        return jobBuilderFactory.get("JOB-DEFAULT")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(stepDefault)
            .next(defaultTasklet)
            .build();
    }
	
	@Qualifier("stepDefault")
	@Bean
	public Step stepDefault(
			@Qualifier("itemExampleReader") ItemReader<EntityLegacyDb01Example> itemExampleReader,
			@Qualifier("itemExampleProcessor") ItemProcessor<EntityLegacyDb01Example, ItemExample> itemExampleProcessor,
			@Qualifier("itemExampleWriter") ItemWriter<ItemExample> itemExampleWriter) {
		
		return stepBuilderFactory.get("stepBuscarItensCondicaoWorkSite").<EntityLegacyDb01Example, ItemExample>chunk(chunkSize)
				.reader(itemExampleReader)
				.processor(itemExampleProcessor)
				.writer(itemExampleWriter)
				.faultTolerant()
				.skip(Exception.class)
				.skipLimit(skipLimitException)
				.build();
	}
	
	@Qualifier("defaultTasklet")
	@Bean
	public Step defaultTasklet(
			@Qualifier("defaultTasklet") Tasklet stepAdditionalTasklet) {
		
		return stepBuilderFactory.get("defaultTasklet")
			.tasklet(stepAdditionalTasklet)
			.build();
	}
	
	@Bean
	public MapJobRepositoryFactoryBean jobExplorerFactory() {
		MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean(new ResourcelessTransactionManager());
		return factoryBean;
	}

	@Bean
	protected JobExplorer createJobExplorer() throws Exception {
		MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(repositoryFactoryBean);
		jobExplorerFactory.afterPropertiesSet();
		return jobExplorerFactory.getObject();
	}

	@Override
	protected JobRepository createJobRepository() throws Exception {
		try {
			repositoryFactoryBean.afterPropertiesSet();
			JobRepository jobRepository = repositoryFactoryBean.getObject();
			return jobRepository;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("hostmail");

	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.antivirus", "antivirus.txt");
	    props.put("mail.debug", "false");
	    
	    return mailSender;
	}

}
