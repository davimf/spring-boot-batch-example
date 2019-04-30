package com.davi.batch.listener;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("listener.notification")
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@NotNull private String from;
	@NotNull private String to;
	@NotNull private String subject;
	
	@Autowired public JavaMailSender emailSender;
	
	@Override
	public void beforeJob(final JobExecution jobExecution) {
		LOGGER.info("Job started.");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		
		String content = "";
		final Collection<StepExecution> steps = jobExecution.getStepExecutions();
		
		for (StepExecution step : steps) {
			
			int totalSucesso = 0;
			int totalErros = 0;
			
			if (step.getExecutionContext().get("successCount") != null) {
				totalSucesso = (Integer) step.getExecutionContext().get("successCount");
			}
			if (step.getExecutionContext().get("errors") != null){
				totalErros = (Integer) step.getExecutionContext().get("errors");
			}
			
			long tempoTotalMs = step.getEndTime().getTime() - step.getStartTime().getTime();
			long tempoMedio = 0;
			
			if (step.getWriteCount() > 0){
				tempoMedio = tempoTotalMs / step.getWriteCount();
			}
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			
			ps.println("");
			ps.println("**************************************************************");
			ps.println("Step : " + step.getStepName());
			ps.println("Start Time : " + step.getStartTime());
			ps.println("End Time: " + step.getEndTime());
			ps.println("Read Count : " + step.getReadCount());
			ps.println("Write Count : " + step.getWriteCount());
			ps.println("Error Count : " + totalErros);
			ps.println("Success Count : " + totalSucesso);
			ps.println("Total execution time (ms) : " + tempoTotalMs);
			ps.println("Average processing time (ms) : " + tempoMedio);
			ps.println("Step Status : " + step.getStatus());
			ps.println("**************************************************************");
			
			LOGGER.info(baos.toString());
			content = content.concat(baos.toString());
		}
		
		sendMail(content);
	}
	
	private void sendMail(final String message) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(message);

		try {
			emailSender.send(mail);
			LOGGER.info("E-mail sent to: " + to);
		} catch (MailException e) {
			LOGGER.error("Could not send the e-mail notification successfully. ", e);
		}
	}
	
	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
