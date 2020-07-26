package com.pjt.tasklets;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class DependentCustomGreetingTasklet implements Tasklet {

	private List<String> parentTaskGreeting;
	private String taskGreeting;
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("taskGreeting " + taskGreeting);
		System.out.println("parentTaskGreeting " + parentTaskGreeting);
		return RepeatStatus.FINISHED;
	}
	public void setTaskGreeting(String taskGreeting) {
		this.taskGreeting = taskGreeting;
	}
	public void setParentTaskGreeting(List<String> parentTaskGreeting) {
		this.parentTaskGreeting = parentTaskGreeting;
	}

}
