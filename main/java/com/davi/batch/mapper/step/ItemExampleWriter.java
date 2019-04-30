package com.davi.batch.mapper.step;

import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.davi.batch.auto.model.ItemExample;
import com.davi.batch.entity.oracle.EntityDb02Example;
import com.davi.batch.mapper.oracle.db01.EntityDb01ExampleMapper;
import com.davi.batch.mapper.oracle.db02.EntityDb02ExampleMapper;

@Component(value = "condicaoWorkSiteItemWriter")
public class ItemExampleWriter implements ItemWriter<ItemExample>{
	
	@Autowired private EntityDb01ExampleMapper entityDb01ExampleMapper;
	@Autowired private EntityDb02ExampleMapper entityDb02ExampleMapper;
	
	@Override
	@Transactional (propagation = Propagation.REQUIRED)
	public void write(List<? extends ItemExample> items) throws Exception {
		for (ItemExample item : items) {
			
			if(entityDb01ExampleMapper.existEntity(item.getEntityExample())) {
				entityDb01ExampleMapper.updateField(item.getEntityExample());
			} else {
				entityDb01ExampleMapper.insertEntity(item.getEntityExample());
			}
			
			EntityDb02Example entityDb02Example = new EntityDb02Example();
			entityDb02Example.setCode(item.getEntityExample().getCode());
			entityDb02Example.setExampleDate(new Date());
			entityDb02ExampleMapper.insertEntity(entityDb02Example);
			
		}
	}

}
