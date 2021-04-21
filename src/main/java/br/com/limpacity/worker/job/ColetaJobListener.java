package br.com.limpacity.worker.job;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import net.logstash.logback.marker.Markers;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class ColetaJobListener extends JobExecutionListenerSupport{
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		log.debug("Job Iniciado: " + jobExecution.getJobId());		
	}
		
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		buildLog(jobExecution);
		
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {			
			log.debug("Job Finalizado com sucesso: " + jobExecution.getJobId());			
			
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {			
			log.error("Job Finalizado com ERRO: " + jobExecution.getJobId());	
		}
		else {
			log.debug("Job status  " + jobExecution.getStatus().name() + jobExecution.getJobId());
		}
				
		
	}

	private BigDecimal getLatency(JobExecution jobExecution) {
		BigDecimal latency = new BigDecimal(jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime());
		latency = latency.divide(new BigDecimal(1000));
		latency = latency.setScale(4);
		return latency;
	}
	
	private void buildLog(JobExecution jobExecution) {
		
		final LogstashMarker httpRequest = Markers.append("job.id", jobExecution.getJobId());
		httpRequest.and(Markers.append("job.status", jobExecution.getStatus()));
		httpRequest.and(Markers.append("job.latency", getLatency(jobExecution).doubleValue()));
		httpRequest.and(Markers.append("job.exit_status", jobExecution.getExitStatus()));
		httpRequest.and(Markers.append("job.version", jobExecution.getVersion()));
		
		log.info(httpRequest, "job completed");
	}
}
