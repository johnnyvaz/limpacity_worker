package br.com.limpacity.worker.config;

import br.com.limpacity.worker.apiClient.ProducerApiClient;
import br.com.limpacity.worker.apiClient.ProducerApiClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@EnableRetry
public class AppConfig {

	@Value("${limpacity.producerUrl}")
	private String producerUrl;

	@Value("${limpacity.producerToken}")
	private String producerToken;

	@Value("${api.timeout}")
	private String timeout;

	private ProducerApiClient producerApiClient;

	@Bean
	public RestTemplate getRestTemplate() {

		final int clientApiTimeout = Integer.parseInt(timeout);
		final SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();
		rf.setReadTimeout(clientApiTimeout);
		rf.setConnectTimeout(clientApiTimeout);
		final ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(rf);
		return new RestTemplate(factory);
	}

	@Bean
	public ProducerApiClient postEmailApiClientConfig() {
		if(producerApiClient == null){
			producerApiClient = new ProducerApiClientImpl(producerUrl, producerToken);
		}
		return producerApiClient;
	}
}
