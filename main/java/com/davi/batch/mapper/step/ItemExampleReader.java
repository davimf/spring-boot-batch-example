package com.davi.batch.mapper.step;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.davi.batch.entity.informix.EntityLegacyDb01Example;
import com.davi.batch.mapper.informix.db01.EntityLegacyDb01ExampleMapper;
import com.davi.batch.mapper.informix.db02.EntityLegacyDb02ExampleMapper;

@Component(value = "itemExampleReader")
public class ItemExampleReader implements ItemReader<EntityLegacyDb01Example> {
	
	@Autowired private EntityLegacyDb01ExampleMapper entityLegacyDb01ExampleMapper;
	@Autowired private EntityLegacyDb02ExampleMapper entityLegacyDb02ExampleMapper;

	private Iterator<EntityLegacyDb01Example> iteratorLegacyEntityLegacyDb01;
	
	@Override
	public EntityLegacyDb01Example read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return iteratorLegacyEntityLegacyDb01.hasNext() ? iteratorLegacyEntityLegacyDb01.next() : null;
	}
	
	@BeforeStep
	void beforeStep(StepExecution stepExecution) {
		
		final List<EntityLegacyDb01Example> activesWithRules = new ArrayList<EntityLegacyDb01Example>();
		
		final List<EntityLegacyDb01Example> activesDb01Legacy = entityLegacyDb01ExampleMapper.getAllActive();
		for (EntityLegacyDb01Example activeDb01 : activesDb01Legacy) {
			if(entityLegacyDb02ExampleMapper.isValid(activeDb01.getCode())) {
				activesWithRules.add(activeDb01);
			}
		}
		
		iteratorLegacyEntityLegacyDb01 = activesWithRules.iterator();
	}
	
}
