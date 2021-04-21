package br.com.limpacity.worker.job;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ColetaQrCodeJobListenerTest {
	
	@Mock
	JobExecution jobExecution;
	
	@InjectMocks
	ColetaJobListener jobListener;
	
	@Test
	void beforeJob() {

		Assertions.assertDoesNotThrow(()->jobListener.beforeJob(jobExecution));
	}
	
	@Test
	void afterJob() {
		
		Assertions.assertDoesNotThrow(()->{
			when(jobExecution.getStartTime()).thenReturn(new Date());
			when(jobExecution.getEndTime()).thenReturn(new Date());		
			when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
			jobListener.afterJob(jobExecution);
			
			when(jobExecution.getStatus()).thenReturn(BatchStatus.FAILED);
			jobListener.afterJob(jobExecution);
			
			when(jobExecution.getStatus()).thenReturn(BatchStatus.ABANDONED);
			jobListener.afterJob(jobExecution);
		});
		
	}
	
	

}
