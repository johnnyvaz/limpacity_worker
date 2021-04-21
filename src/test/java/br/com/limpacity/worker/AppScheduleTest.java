package br.com.limpacity.worker;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import br.com.limpacity.worker.config.AppSchedule;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AppScheduleTest {
	
	@Mock
	private  JobLauncher jobLauncher;

	@Mock
	private  Job job;

	@Mock
	private  JobExplorer jobExplorer;
	
	@InjectMocks
	AppSchedule schedule;
	
	@Test
	void run() {
		
		Assertions.assertDoesNotThrow(()->schedule.run());		
		
	}
	
	@Test
	void testAlreadyJobExecuting() {
		
		Assertions.assertDoesNotThrow(()->{
			Set<JobExecution> jobExecutions = new HashSet<JobExecution>();
			jobExecutions.add(new JobExecution(2L));			
			when(jobExplorer.findRunningJobExecutions(Mockito.any())).thenReturn(jobExecutions);
			schedule.run();
		});
		
	}
	
	@Test
	void runFailed() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		Assertions.assertDoesNotThrow(()->{
			when(jobLauncher.run(Mockito.any() ,Mockito.any())).thenThrow(new RuntimeException("test error"));
			Assertions.assertDoesNotThrow(()-> schedule.run());
		});
			
	}
	
	
}
