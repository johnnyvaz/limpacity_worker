package br.com.limpacity.worker.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

	@Qualifier("dataSourceLimpacity")
	@Bean
	public MapJobRepositoryFactoryBean mapJobRepositoryFactory() throws Exception {
		
	    MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean =
	    		new MapJobRepositoryFactoryBean(new ResourcelessTransactionManager());
	    mapJobRepositoryFactoryBean.afterPropertiesSet();
	    return mapJobRepositoryFactoryBean;
	}

	
	@Bean
	public JobRepository jobRepository(MapJobRepositoryFactoryBean repositoryFactory) throws Exception {
	    return repositoryFactory.getObject();
	}


	@Bean
	public JobExplorer jobExplorer(MapJobRepositoryFactoryBean repositoryFactory) {
	    return new SimpleJobExplorer(
	    		repositoryFactory.getJobInstanceDao(), repositoryFactory.getJobExecutionDao(),
	            repositoryFactory.getStepExecutionDao(), repositoryFactory.getExecutionContextDao());
	}
	
	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
	    SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
	    simpleJobLauncher.setJobRepository(jobRepository);
	    return simpleJobLauncher;
	}
	
	@Bean("jobLauncherAsync")
	public SimpleJobLauncher jobLauncherAsync(JobRepository jobRepository) {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository);
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
		simpleJobLauncher.setTaskExecutor(simpleAsyncTaskExecutor);
		return simpleJobLauncher;
	}
	
}
