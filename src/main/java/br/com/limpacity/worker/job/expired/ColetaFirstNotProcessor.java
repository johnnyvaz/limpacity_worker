package br.com.limpacity.worker.job.expired;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import br.com.limpacity.worker.util.IntegrationStatusEnum;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ColetaFirstNotProcessor implements ItemProcessor<ColetaQrCodeModel, ColetaQrCodeModel> {
	@Override
	public ColetaQrCodeModel process(ColetaQrCodeModel item) throws Exception {
		item.setUpdateDate(new Date());
		item.setIntegrationStatus(IntegrationStatusEnum.FAILED.getStatus());
		return item;
	}

}
