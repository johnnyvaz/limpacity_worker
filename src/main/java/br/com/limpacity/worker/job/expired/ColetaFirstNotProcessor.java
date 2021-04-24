package br.com.limpacity.worker.job.expired;

import br.com.limpacity.worker.model.ColetaQrCode;
import br.com.limpacity.worker.util.IntegrationStatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Component
public class ColetaFirstNotProcessor implements ItemProcessor<ColetaQrCode, ColetaQrCode> {
	@Override
	public ColetaQrCode process(ColetaQrCode item) throws Exception {
		item.setUpdateDate(new Date());
		item.setIntegrationStatus(IntegrationStatusEnum.FAILED.getStatus());
		return item;
	}

}
