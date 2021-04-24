package br.com.limpacity.worker.service.impl;

import br.com.limpacity.producer.dto.NotificaEmailDTO;
import br.com.limpacity.worker.apiClient.ProducerApiClient;
import br.com.limpacity.worker.dao.ColetaRepository;
import br.com.limpacity.worker.model.ColetaQrCodeModel;
import br.com.limpacity.worker.repository.ColetaUpdateRepository;
import br.com.limpacity.worker.service.ColetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void sendMsg(List<ColetaQrCodeModel> coletas) {

		coletas.forEach(coleta -> {
			producerApiClient.postEmail(toEmails(coletas));
			update.updateWithQuery(coleta);
			this.save(coletas);
		});

//		producerApiClient.postEmail(toEmails(coletas));
//		this.save(coletas);
	}

	@Override
	public void save(List<? extends ColetaQrCodeModel> coletas) {
		this.coletaRepository.update(coletas);
	}


	private List<NotificaEmailDTO> toEmails(List<ColetaQrCodeModel> coleta) {
		return coleta.stream().map(this::toEmail).collect(Collectors.toList());
	}

	private NotificaEmailDTO toEmail(ColetaQrCodeModel coleta) {
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
