package br.com.limpacity.worker.job;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ColetaProcessor implements ItemProcessor<ColetaQrCodeModel, ColetaQrCodeModel> {


	@Override
	public ColetaQrCodeModel process(ColetaQrCodeModel item) throws Exception {
		return item;
	}

}
