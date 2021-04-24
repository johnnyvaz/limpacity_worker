package br.com.limpacity.worker.job;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.limpacity.worker.job.expired.ColetaFirstNotProcessor;
import br.com.limpacity.worker.model.ColetaQrCode;
import br.com.limpacity.worker.util.IntegrationStatusEnum;

class ColetaQrCodeExpiredProcessorTest {
		
	@Test
	void coletaError() throws Exception {
		
		ColetaFirstNotProcessor processor = new ColetaFirstNotProcessor();
		ColetaQrCode coletaQrCodeProcessed = processor.process(ColetaQrCode.builder()
				.ativo(true)
				.postoId(1L)
				.uuid("ebda9df8-b301-4fd8-9779-f89564bdf6ba")
				.id(3L)
				.build());
		Assertions.assertNotNull(coletaQrCodeProcessed);
		Assertions.assertEquals(IntegrationStatusEnum.FAILED.getStatus(), coletaQrCodeProcessed.getIntegrationStatus());
	}
	
	@Test
	void coletaErrorWihDescription() throws Exception {
		ColetaFirstNotProcessor processor = new ColetaFirstNotProcessor();
		ColetaQrCode coletaQrCodeProcessed = processor.process(ColetaQrCode.builder()
				.ativo(true)
				.postoId(1L)
				.uuid("ebda9df8-b301-4fd8-9779-f89564bdf6ba")
				.id(3L)
				.updateDate(new Date())
				.integrationStatus(IntegrationStatusEnum.SUCCESS.getStatus())
				.build());
		
		Assertions.assertNotNull(coletaQrCodeProcessed);
		Assertions.assertEquals(IntegrationStatusEnum.FAILED.getStatus(), coletaQrCodeProcessed.getIntegrationStatus());
	}

}
