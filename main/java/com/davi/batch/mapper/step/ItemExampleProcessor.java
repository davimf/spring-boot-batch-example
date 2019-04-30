package com.davi.batch.mapper.step;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.davi.batch.auto.model.ItemExample;
import com.davi.batch.entity.informix.EntityLegacyDb01Example;
import com.davi.batch.entity.oracle.EntityDb01Example;

@Component(value = "itemExampleProcessor")
public class ItemExampleProcessor implements ItemProcessor<EntityLegacyDb01Example, ItemExample> {
	
	private static final Logger LOGGER = LogManager.getLogger(ItemExampleProcessor.class);
	
	private int errors = 0;
	private int successCount = 0;
	
	@AfterProcess
	public void afterProcess(EntityLegacyDb01Example item, ItemExample result) {
		if (result != null) {
			successCount++;
		} else {
			errors++;
		}
	}
	
	@OnProcessError
	public void onProcessError(EntityLegacyDb01Example item, Exception e) {
		errors++;
		if(item != null) {
			LOGGER.error(item.toString());
		} else {
			LOGGER.error("Error (null entity) ", e);
		}
	}
	
	@AfterStep
	void AfterStep(final StepExecution stepExecution) {
		stepExecution.getExecutionContext().put("successCount", successCount);
		stepExecution.getExecutionContext().put("errors", errors);
	}

	public ItemExample process(EntityLegacyDb01Example entityLegacyDb01Example) throws Exception {
		ItemExample item = new ItemExample();
		
		EntityDb01Example entityDb01 = new EntityDb01Example();
		entityDb01.setCode(entityLegacyDb01Example.getCode());
		entityDb01.setValue(entityLegacyDb01Example.getValue());
		
		item.setEntityDb01Example(entityDb01);
		
		return item;
	}
}