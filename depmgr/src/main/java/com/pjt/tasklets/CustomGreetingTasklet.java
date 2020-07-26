package com.pjt.tasklets;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class CustomGreetingTasklet implements Tasklet {

	private String taskGreeting;
	private List<String> parentTaskGreeting;

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		this.taskGreeting = chunkContext.getStepContext().getStepName();
		System.out.println("taskGreeting " + taskGreeting);
		if(chunkContext.getStepContext().getStepName().equalsIgnoreCase("step3")  || chunkContext.getStepContext().getStepName().equalsIgnoreCase("step22") || chunkContext.getStepContext().getStepName().equalsIgnoreCase("step36")) {
			System.out.println("Failing step3 ...");
			throw new RuntimeException();
		}
		if(null != parentTaskGreeting) parentTaskGreeting.add(taskGreeting);
		return RepeatStatus.FINISHED;
	}
	public void setTaskGreeting(String taskGreeting) {
		this.taskGreeting = taskGreeting;
	}
	public void setParentTaskGreeting(List<String> parentTaskGreeting) {
		this.parentTaskGreeting = parentTaskGreeting;
	}

}
