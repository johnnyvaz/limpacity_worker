package br.com.limpacity.worker.job;

import br.com.limpacity.worker.model.ColetaQrCode;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ColetaProcessor implements ItemProcessor<ColetaQrCode, ColetaQrCode> {


	@Override
	public ColetaQrCode process(ColetaQrCode item) throws Exception {
		return item;
	}

}
