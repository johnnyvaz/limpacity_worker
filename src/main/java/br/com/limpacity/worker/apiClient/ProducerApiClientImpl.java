package br.com.limpacity.worker.apiClient;

import br.com.limpacity.producer.dto.NotificaEmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class ProducerApiClientImpl implements ProducerApiClient {

	private final String baseUrl;
    private final String authToken;

	@Autowired
    private RestTemplate restTemplate;

    public ProducerApiClientImpl(String baseUrl, String authToken) {
        this.baseUrl = baseUrl;
        this.authToken = authToken;
    }

	@Override
	public void postEmail(List<NotificaEmailDTO> request) {
		
		HttpClientTemplate requestTemplate = new HttpClientTemplate(restTemplate);		
        String uri = baseUrl.concat("/notifica-email");
        try{
            ResponseEntity<String> response = requestTemplate.callAPI(uri, HttpMethod.POST, authToken, request);
            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                log.info(" Sending email : " + response.getStatusCode() );
            } else {
                log.error("Error on Sending Email" + response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());

        }

	}

}
