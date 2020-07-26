package com.pjt.launcher;

import java.util.Collection;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.support.GroupAwareJob;
import org.springframework.batch.core.job.AbstractJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.AbstractStep;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyManagerJobLauncher {

	public BatchStatus launchJob(String jobName, ClassPathXmlApplicationContext context, JobLauncher jobLauncher, JobParametersBuilder jobParametersBuilder) {
		return launchJob(jobName, context, jobLauncher, jobParametersBuilder, null, "dependencyListener", "stepNames");
	}
	
	public BatchStatus launchJob(String jobName, ClassPathXmlApplicationContext context, JobLauncher jobLauncher, JobParametersBuilder jobParametersBuilder, String groupName) {
		return launchJob(jobName, context, jobLauncher, jobParametersBuilder, groupName, "dependencyListener", "stepNames");
	}
	
	public BatchStatus launchJob(String jobName, ClassPathXmlApplicationContext context, JobLauncher jobLauncher, JobParametersBuilder jobParametersBuilder, String groupName, String dependencyListener) {
		return launchJob(jobName, context, jobLauncher, jobParametersBuilder, groupName, dependencyListener, "stepNames");
	}
	
	public BatchStatus launchJob(String jobName, ClassPathXmlApplicationContext context, JobLauncher jobLauncher, JobParametersBuilder jobParametersBuilder, String groupName, String dependencyListener, String environmentVariableForStepNames) {
		String stepNamesHolder = null != environmentVariableForStepNames ? environmentVariableForStepNames : "stepNames";
		JobParameters jobParameters = jobParametersBuilder.toJobParameters();
		Job job = (Job) context.getBean(jobName);
		addDependencyListenerToAllSteps(context, job, groupName, dependencyListener, stepNamesHolder);
		BatchStatus batchStatus = BatchStatus.UNKNOWN;
		try {
	
			JobExecution execution = jobLauncher.run(job, jobParameters);
			System.out.println("Exit Status : " + execution.getStatus());
			batchStatus = execution.getStatus();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		System.out.println("Done");
		context.close();
		return batchStatus;
	}

	protected void addDependencyListenerToAllSteps(ClassPathXmlApplicationContext context, Job job, String groupName, String dependencyListener, String environmentVariableForStepNames) {
		if(job instanceof AbstractJob) {
			AbstractJob abstractJob = (AbstractJob)job;
			
			Collection<String> stepNames = abstractJob.getStepNames();
			String allStep = "";
			for(String steps : stepNames) {
				allStep = allStep.concat(steps).concat(",");
				AbstractStep step = (AbstractStep)abstractJob.getStep(steps);
				step.registerStepExecutionListener((StepExecutionListener)context.getBean(dependencyListener));
			}
			System.setProperty(environmentVariableForStepNames, allStep);
			
		}else if(job instanceof GroupAwareJob){
			GroupAwareJob groupAwareJob = (GroupAwareJob)job;
			addDependencyListenerToAllSteps(context, (Job)context.getBean(groupAwareJob.getName().replaceFirst(groupName, "")), groupName, dependencyListener, environmentVariableForStepNames);
		}
	}

}