package com.davi.batch.mapper.step;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.davi.batch.mapper.oracle.db01.EntityDb01ExampleMapper;

@Component(value = "defaultTasklet")
public class DefaultTasklet  implements Tasklet, InitializingBean {
	
	@Autowired private EntityDb01ExampleMapper entityDb01ExampleMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		entityDb01ExampleMapper.executeProcedure();
		return RepeatStatus.FINISHED;
	}

}
