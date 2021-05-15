package br.com.limpacity.worker.service.impl;

import br.com.limpacity.producer.dto.NotificaEmailDTO;
import br.com.limpacity.worker.apiClient.ProducerApiClient;
import br.com.limpacity.worker.dao.ColetaRepository;
import br.com.limpacity.worker.exception.ColetaQrCodeException;
import br.com.limpacity.worker.model.ColetaQrCodeModel;
import br.com.limpacity.worker.repository.ColetaQrCodeRepository;
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

	@Value("${notificacoes.not1}")
	private String not1;

	@Autowired
	private ColetaQrCodeRepository coletaQrCode;

	@Autowired
	private ColetaRepository coletaRepository;


	public List<ColetaQrCodeModel> findAllColetasOpen(){
		final List<ColetaQrCodeModel> result = coletaQrCode.findAllColetasOpen();
		if(result.isEmpty()) {
			throw new ColetaQrCodeException();
		}
		return  result;
	}

	@Override
	public void sendMsg(List<ColetaQrCodeModel> coletas) {

		coletas.forEach(coleta -> {
			producerApiClient.postEmail(toEmails(coletas));
		});
		this.save(coletas);

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
				.assunto("Email automático " + coleta.getId())
				.copia("copia")
				.creationDate(new Date())
				.destino("johnnyjohnnyjohnnyvaz@gmail.com")
				.descricaoColeta("descrição coleta: ")
				.mensagem("Você tem uma coleta para ser realizada : " + coleta.getUuid())
				.build();

	}
}
