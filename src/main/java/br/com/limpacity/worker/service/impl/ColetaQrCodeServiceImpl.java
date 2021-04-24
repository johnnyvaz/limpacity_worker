package br.com.limpacity.worker.service.impl;

import br.com.limpacity.producer.dto.NotificaEmailDTO;
import br.com.limpacity.worker.apiClient.ProducerApiClient;
import br.com.limpacity.worker.dao.ColetaRepository;
import br.com.limpacity.worker.model.ColetaQrCode;
import br.com.limpacity.worker.service.ColetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ColetaQrCodeServiceImpl implements ColetaService {

	@Autowired
	private ProducerApiClient producerApiClient;

	@Value("${limpacity.producerUrl}")
	private String producerUrl;

	@Autowired
	private ColetaRepository coletaRepository;

	@Override
	public void sendMsg(List<ColetaQrCode> coletas) {

		coletas.forEach(coleta -> {
			coleta.setIntegrationStatus("S");
			coleta.setUpdateDate(new Date());
		});

		producerApiClient.postEmail(toEmails(coletas));
		log.debug("Sending Email - " + coletas);
		System.out.println(coletas);
		this.save(coletas);
	}

	@Override
	public void save(List<? extends ColetaQrCode> coletas) {
		this.coletaRepository.update(coletas);
	}


	private List<NotificaEmailDTO> toEmails(List<ColetaQrCode> coleta) {
		return coleta.stream().map(this::toEmail).collect(Collectors.toList());
	}

	private NotificaEmailDTO toEmail(ColetaQrCode coleta) {
		return NotificaEmailDTO.builder()
				.assunto("Email de Coleta - tentativa automática" + coleta.getId())
				.copia("copia")
				.creationDate(new Date())
				.destino("email destino")
				.descricaoColeta("descrição coleta: " + coleta.getUuid())
				.mensagem("Corpo da mensagem")
				.build();

	}
}
