package br.com.limpacity.worker.service.impl;

import br.com.limpacity.producer.dto.NotificaEmailDTO;
import br.com.limpacity.worker.apiClient.ProducerApiClient;
import br.com.limpacity.worker.dao.ColetaRepository;
import br.com.limpacity.worker.model.ColetaQrCode;
import br.com.limpacity.worker.repository.ColetaUpdateRepository;
import br.com.limpacity.worker.service.ColetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ColetaQrCodeServiceImpl implements ColetaService {

	@Autowired
	private ProducerApiClient producerApiClient;

	@Value("${notificacoes.not1}")
	private String not1;

	@Autowired
	private ColetaRepository coletaRepository;

	@Autowired
	private ColetaUpdateRepository update;

	@Override
	@Transactional
	public void sendMsg(List<ColetaQrCode> coletas) {
		coletas.forEach(coleta -> {
			update.updateWithQuery(coleta);
		});
		producerApiClient.postEmail(toEmails(coletas));
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
				.assunto("Email de Coleta - tentativa automática " + coleta.getId())
				.copia("copia")
				.creationDate(new Date())
				.destino("email destino")
				.descricaoColeta("descrição coleta: " + coleta.getUuid())
				.mensagem("Corpo da mensagem")
				.build();

	}
}
