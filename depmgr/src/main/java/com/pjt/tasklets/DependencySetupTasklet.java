package com.pjt.tasklets;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.pjt.model.StepState;

public class DependencySetupTasklet implements Tasklet {

	protected ConcurrentHashMap<String, StepState> dependencyMet;
	protected ConcurrentHashMap<String, List<String>> dependencies;
	String environmentVariableForStepNames;

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String stepNames = System.getProperty(environmentVariableForStepNames);
		System.out.println("Stepnames : " + stepNames);
		dependencyMet.put(chunkContext.getStepContext().getStepName(), StepState.WAITING);

		overrideSetup();
		validateSetup(stepNames);

		dependencyMet.put("setupDependency", StepState.COMPLETE);
		return RepeatStatus.FINISHED;
	}

	protected void validateSetup(String stepNames) {
		String[] steps = stepNames.split(",");
		for (String step : steps) {
			if (!dependencyMet.containsKey(step)) {
				throw new RuntimeException(
						"Dependencies and actual steps do not match, missing " + step + " in configuration.");
			}
		}

		for (String step : dependencies.keySet()) {
			if (!dependencyMet.containsKey(step)) {
				throw new RuntimeException(
						"Dependencies matrix and actual steps do not match, additional step : " + step + " in matrix.");
			}
		}
	}

	public void setDependencyMet(ConcurrentHashMap<String, StepState> dependencyMet) {
		this.dependencyMet = dependencyMet;
	}

	public void setDependencies(ConcurrentHashMap<String, List<String>> dependencies) {
		this.dependencies = dependencies;
	}

	public void setEnvironmentVariableForStepNames(String environmentVariableForStepNames) {
		this.environmentVariableForStepNames = environmentVariableForStepNames;
	}

	protected void overrideSetup() {
		dependencyMet.put("step1", StepState.WAITING);
		dependencyMet.put("step2", StepState.WAITING);
		dependencyMet.put("step3", StepState.WAITING);
		dependencyMet.put("step4", StepState.WAITING);
		dependencyMet.put("step5", StepState.WAITING);
		dependencyMet.put("step6", StepState.WAITING);
		dependencyMet.put("step7", StepState.WAITING);
//		dependencyMet.put("step8", false);
		/*
		 * dependencyMet.put("step9", false); dependencyMet.put("step10", false);
		 * dependencyMet.put("step11", false); dependencyMet.put("step12", false);
		 * dependencyMet.put("step13", false); dependencyMet.put("step14", false);
		 * dependencyMet.put("step15", false); dependencyMet.put("step16", false);
		 * dependencyMet.put("step17", false); dependencyMet.put("step18", false);
		 * dependencyMet.put("step19", false); dependencyMet.put("step20", false);
		 * dependencyMet.put("step21", false); dependencyMet.put("step22", false);
		 * dependencyMet.put("step23", false); dependencyMet.put("step24", false);
		 * dependencyMet.put("step25", false); dependencyMet.put("step26", false);
		 * dependencyMet.put("step27", false); dependencyMet.put("step28", false);
		 * dependencyMet.put("step29", false); dependencyMet.put("step30", false);
		 * dependencyMet.put("step31", false); dependencyMet.put("step32", false);
		 * dependencyMet.put("step33", false); dependencyMet.put("step34", false);
		 * dependencyMet.put("step35", false); dependencyMet.put("step36", false);
		 * dependencyMet.put("step37", false); dependencyMet.put("step38", false);
		 * dependencyMet.put("step39", false); dependencyMet.put("step40", false);
		 * dependencyMet.put("step41", false); dependencyMet.put("step42", false);
		 * dependencyMet.put("step43", false); dependencyMet.put("step44", false);
		 * dependencyMet.put("step45", false); dependencyMet.put("step46", false);
		 * dependencyMet.put("step47", false); dependencyMet.put("step48", false);
		 * dependencyMet.put("step49", false); dependencyMet.put("step50", false);
		 * dependencyMet.put("step51", false); dependencyMet.put("step52", false);
		 * dependencyMet.put("step53", false); dependencyMet.put("step54", false);
		 * dependencyMet.put("step55", false); dependencyMet.put("step56", false);
		 * dependencyMet.put("step57", false); dependencyMet.put("step58", false);
		 * dependencyMet.put("step59", false); dependencyMet.put("step60", false);
		 * dependencyMet.put("step61", false); dependencyMet.put("step62", false);
		 * dependencyMet.put("step63", false); dependencyMet.put("step64", false);
		 * dependencyMet.put("step65", false); dependencyMet.put("step66", false);
		 * dependencyMet.put("step67", false); dependencyMet.put("step68", false);
		 * dependencyMet.put("step69", false); dependencyMet.put("step70", false);
		 * dependencyMet.put("step71", false); dependencyMet.put("step72", false);
		 * dependencyMet.put("step73", false); dependencyMet.put("step74", false);
		 * dependencyMet.put("step75", false); dependencyMet.put("step76", false);
		 * dependencyMet.put("step77", false); dependencyMet.put("step78", false);
		 * dependencyMet.put("step79", false); dependencyMet.put("step80", false);
		 * dependencyMet.put("step81", false); dependencyMet.put("step82", false);
		 * dependencyMet.put("step83", false); dependencyMet.put("step84", false);
		 * dependencyMet.put("step85", false); dependencyMet.put("step86", false);
		 * dependencyMet.put("step87", false); dependencyMet.put("step88", false);
		 * dependencyMet.put("step89", false); dependencyMet.put("step90", false);
		 * dependencyMet.put("step91", false); dependencyMet.put("step92", false);
		 * dependencyMet.put("step93", false); dependencyMet.put("step94", false);
		 * dependencyMet.put("step95", false); dependencyMet.put("step96", false);
		 * dependencyMet.put("step97", false); dependencyMet.put("step98", false);
		 * dependencyMet.put("step99", false); dependencyMet.put("step100", false);
		 * dependencyMet.put("step101", false); dependencyMet.put("step102", false);
		 * dependencyMet.put("step103", false); dependencyMet.put("step104", false);
		 * dependencyMet.put("step105", false); dependencyMet.put("step106", false);
		 * dependencyMet.put("step107", false); dependencyMet.put("step108", false);
		 * dependencyMet.put("step109", false); dependencyMet.put("step110", false);
		 * dependencyMet.put("step111", false); dependencyMet.put("step112", false);
		 * dependencyMet.put("step113", false); dependencyMet.put("step114", false);
		 * dependencyMet.put("step115", false); dependencyMet.put("step116", false);
		 * dependencyMet.put("step117", false); dependencyMet.put("step118", false);
		 * dependencyMet.put("step119", false); dependencyMet.put("step120", false);
		 * dependencyMet.put("step121", false); dependencyMet.put("step122", false);
		 * dependencyMet.put("step123", false); dependencyMet.put("step124", false);
		 * dependencyMet.put("step125", false); dependencyMet.put("step126", false);
		 * dependencyMet.put("step127", false); dependencyMet.put("step128", false);
		 * dependencyMet.put("step129", false); dependencyMet.put("step130", false);
		 * dependencyMet.put("step131", false); dependencyMet.put("step132", false);
		 * dependencyMet.put("step133", false);
		 */		

		List<String> step1Dependencies = new ArrayList<>();
		step1Dependencies.add("step2");
		step1Dependencies.add("step3");
		
		dependencies.put("step1", step1Dependencies);

		List<String> step2Dependencies = new ArrayList<>();
		step2Dependencies.add("step4");
		step2Dependencies.add("step3");
		
		dependencies.put("step2", step2Dependencies);

		List<String> step30Dependencies = new ArrayList<>();
		step30Dependencies.add("step4");
		step30Dependencies.add("step3");
		step30Dependencies.add("step133");
		
		//dependencies.put("step30", step30Dependencies);

		
		/*
		 * List<String> step130Dependencies = new ArrayList<>();
		 * step130Dependencies.add("step4"); step130Dependencies.add("step3");
		 * step130Dependencies.add("step133"); step130Dependencies.add("step30");
		 * step130Dependencies.add("step1");
		 * 
		 * dependencies.put("step130", step130Dependencies);
		 */
	}


	
}
