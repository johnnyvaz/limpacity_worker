package br.com.limpacity.worker.job.expired;

import br.com.limpacity.worker.model.ColetaQrCode;
import br.com.limpacity.worker.util.IntegrationStatusEnum;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Component
public class ColetaExpiredProcessor implements ItemProcessor<ColetaQrCode, ColetaQrCode> {
	@Override
	public ColetaQrCode process(ColetaQrCode item) throws Exception {
		String description = item.getIntegrationDescription();
		item.setUpdateDate(LocalDateTime.now());
		item.setIntegrationStatus(IntegrationStatusEnum.FAILED.getStatus());
		if (Objects.isNull(description)) {
			description = "";
		}
		description += ". Retry exceeded";
		item.setIntegrationDescription(description);
		return item;
	}

}
