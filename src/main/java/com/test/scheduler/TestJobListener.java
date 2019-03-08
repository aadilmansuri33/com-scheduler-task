package com.test.scheduler;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author inexture
 *
 */
public class TestJobListener implements JobListener {
	JobBean bean;

	/**
	 * @throws Exception
	 */
	TestJobListener() throws Exception {
		bean = CronExp.CronExpression();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.JobListener#getName()
	 */
	public String getName() {
		return "HelloJcgJobListener";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.JobListener#jobToBeExecuted(org.quartz.JobExecutionContext)
	 */
	public void jobToBeExecuted(JobExecutionContext context) {

		final String jobName = context.getJobDetail().getKey().toString();
		System.out.println("Job Executed: " + jobName + " is starting...!!");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quartz.JobListener#jobExecutionVetoed(org.quartz.JobExecutionContext)
	 */
	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.JobListener#jobWasExecuted(org.quartz.JobExecutionContext,
	 * org.quartz.JobExecutionException)
	 */
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

		// getting job name
		final String jobName = context.getJobDetail().getKey().toString();

		System.out.println("Job : " + jobName + " is finished!!");

		System.out.println("-----------------------------------------" + new Date());

		try {
			// CronTrigger for getting cron expression isRunning
			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withSchedule(CronScheduleBuilder.cronSchedule(TestCronTrigger.getCron())).build();

			// if(TestJob.class.getName().equals(TestJob.class.getName())) check job class
			// are same or not if it same then check next condition
			// Note : JobClass match befor compare cron expression
			// Compare Old-Cron Expression with New Expression
			if (!bean.getDelay().equals(trigger.getCronExpression())) {

				// get a scheduler which is in current running
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();

				scheduler.shutdown();

				// called Method for schedule according New Cron Expression
				TestCronTrigger.updateCronExp();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
