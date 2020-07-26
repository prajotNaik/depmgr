package depmgr;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pjt.launcher.DependencyManagerJobLauncher;

public class JobRunnerTest  {

	
	public static void main(String[] args) {
		String jobConfig = "/jobs/myjob.xml";
		String jobName = "myjob";
		String[] springConfig = { jobConfig };
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder().addLong("time", System.currentTimeMillis());
		String groupName = null;

		new DependencyManagerJobLauncher().launchJob(jobName, context, jobLauncher, jobParametersBuilder, groupName, "dependencyListener", "stepName");		
	}

}
