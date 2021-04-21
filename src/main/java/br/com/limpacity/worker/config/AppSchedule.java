package br.com.limpacity.worker.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
@Profile("!test")
public class AppSchedule {

	private final JobLauncher jobLauncher;

	private final Job job;

	private final JobExplorer jobExplorer;

	@Scheduled(fixedDelayString = "${worker.scheduled.fixedRate}")
	public void run() {
		if (!this.hasJobExecuting(this.job.getName())) {
			try {
				final JobParameters jobParameters = new JobParametersBuilder()
						.addLong("time", System.currentTimeMillis()).toJobParameters();
				this.jobLauncher.run(this.job, jobParameters);
			}
			catch (final Exception e) {
				AppSchedule.log.error(e.getMessage());
			}
		}
	}

	private boolean hasJobExecuting(String jobName) {
		return !this.jobExplorer.findRunningJobExecutions(jobName).isEmpty();
	}

}
