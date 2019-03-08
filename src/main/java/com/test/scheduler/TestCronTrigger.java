package com.test.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

/**
 * @author inexture
 *
 */
public class TestCronTrigger {
	static JobBean bean;
	static JobKey jobKey;
	static Scheduler scheduler;
	static JobDetail jobDetail;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Initialize New Job Details As Job Key and Job Group
		jobKey = new JobKey("Aadil's Job", "group1");
		// Initialize Job Details
		jobDetail = JobBuilder.newJob(TestJob.class).withIdentity(jobKey.getName(), jobKey.getGroup()).build();
		startJob(jobKey, jobDetail);
	}

	/**
	 * @param jobKey
	 * @param jobDetail
	 * @throws Exception
	 */
	public static void startJob(JobKey jobKey, JobDetail jobDetail) throws Exception {
		// Get Scheduler
		scheduler = new StdSchedulerFactory().getScheduler();

		// New Cron Trigger to build Job
		CronTrigger cronTrigger = (CronTrigger) scheduler
				.getTrigger(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));

		// Add Job Listener and Match Job details like JobKey
		scheduler.getListenerManager().addJobListener(new TestJobListener(), KeyMatcher.keyEquals(jobKey));

		// Build a New Trigger with Cron Expression
		cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobKey.getName(), jobKey.getGroup())
				.withSchedule(CronScheduleBuilder.cronSchedule(getCron())).build();

		scheduler.start();
		scheduler.scheduleJob(jobDetail, cronTrigger);

	}

	/**
	 * @throws Exception
	 */
	public static void updateCronExp() throws Exception {
		// Get Scheduler
		scheduler = new StdSchedulerFactory().getScheduler();

		// Get Job Listener
		scheduler.getListenerManager().addJobListener(new TestJobListener(), KeyMatcher.keyEquals(jobKey));

		scheduler.start();

		// Schedule Job Using Updated Cron Expression
		scheduler.scheduleJob(jobDetail, TriggerBuilder.newTrigger().withIdentity(jobKey.getName(), jobKey.getGroup())
				.withSchedule(CronScheduleBuilder.cronSchedule(bean.getDelay())).build());
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static String getCron() throws Exception {
		bean = CronExp.CronExpression();
		return bean.getDelay();

	}
}
