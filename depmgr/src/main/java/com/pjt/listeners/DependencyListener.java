package com.pjt.listeners;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.pjt.model.StepState;

public class DependencyListener implements StepExecutionListener {

	Map<String, StepState> dependencyMet;
	Map<String, List<String>> dependencies;
	boolean stopTheWorld;

	public void beforeStep(StepExecution stepExecution) {
		List<String> stepDependencies = dependencies.get(stepExecution.getStepName());
		if(null != stepDependencies) {
		for(String dependentSteps : stepDependencies) {
			while(dependencyMet.get(dependentSteps).equals(StepState.WAITING)) {
				if(stopTheWorld) {
					if(StepState.FAILED.equals(dependencyMet.get(dependentSteps))) {
						System.out.println("Breaking " + stepExecution.getStepName() + ",Dependency step " + dependentSteps + " has failed");
					}else {
						System.out.println("Breaking " + stepExecution.getStepName() + ",Some step has failed");
					}
					stepExecution.setTerminateOnly();
					break;
				}
			}
			if(stopTheWorld) {
				break;
			}
			System.out.println("Met dependency " + dependentSteps + " for " + stepExecution.getStepName());
		}
		}else {
			System.out.println("No dependencies for " + stepExecution.getStepName());
			if(stopTheWorld) {
					stepExecution.setTerminateOnly();
			}
			return;
		}

		if(stepExecution.getStepName().equalsIgnoreCase("step2")) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Dependencies for step " + stepExecution.getStepName());
		for(String dependentSteps : stepDependencies) {
			System.out.print(dependentSteps + " : " + dependencyMet.get(dependentSteps) + ",");
		}
		System.out.println();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ExitStatus afterStep(StepExecution stepExecution) {
		if(!stepExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
			stopTheWorld = true;
			dependencyMet.put(stepExecution.getStepName(), StepState.FAILED);
			return ExitStatus.FAILED;
		}
		dependencyMet.put(stepExecution.getStepName(), StepState.COMPLETE);
		return ExitStatus.COMPLETED;
	}

	public void setDependencyMet(Map<String, StepState> dependencyMet) {
		this.dependencyMet = dependencyMet;
	}

	public void setDependencies(Map<String, List<String>> dependencies) {
		this.dependencies = dependencies;
	}

}
