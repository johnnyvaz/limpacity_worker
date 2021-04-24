package br.com.limpacity.worker.apiClient;

import br.com.limpacity.producer.dto.NotificaEmailDTO;

import java.util.List;

public interface ProducerApiClient {

    void postEmail(List<NotificaEmailDTO> request);
}
