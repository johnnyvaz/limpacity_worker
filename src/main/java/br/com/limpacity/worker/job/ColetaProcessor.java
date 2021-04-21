package br.com.limpacity.worker.job;

import br.com.limpacity.worker.dao.ColetaContentRepository;
import br.com.limpacity.worker.model.ColetaQrCode;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColetaProcessor implements ItemProcessor<ColetaQrCode, ColetaQrCode> {

	@Autowired
	private ColetaContentRepository coletaContentRepository;

	@Override
	public ColetaQrCode process(ColetaQrCode item) throws Exception {

		return item;
	}

}
