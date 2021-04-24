package br.com.limpacity.worker.job;

import java.util.Date;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.limpacity.worker.job.expired.ColetaFirstNotProcessor;
import br.com.limpacity.worker.util.IntegrationStatusEnum;

class ColetaQrCodeModelExpiredProcessorTest {
		
	@Test
	void coletaError() throws Exception {
		
		ColetaFirstNotProcessor processor = new ColetaFirstNotProcessor();
		ColetaQrCodeModel coletaQrCodeModelProcessed = processor.process(ColetaQrCodeModel.builder()
				.ativo(true)
//				.postoId(1L)
				.uuid("ebda9df8-b301-4fd8-9779-f89564bdf6ba")
				.id(3L)
				.build());
		Assertions.assertNotNull(coletaQrCodeModelProcessed);
		Assertions.assertEquals(IntegrationStatusEnum.FAILED.getStatus(), coletaQrCodeModelProcessed.getIntegrationStatus());
	}
	
	@Test
	void coletaErrorWihDescription() throws Exception {
		ColetaFirstNotProcessor processor = new ColetaFirstNotProcessor();
		ColetaQrCodeModel coletaQrCodeModelProcessed = processor.process(ColetaQrCodeModel.builder()
				.ativo(true)
//				.postoId(1L)
				.uuid("ebda9df8-b301-4fd8-9779-f89564bdf6ba")
				.id(3L)
				.updateDate(new Date())
				.integrationStatus(IntegrationStatusEnum.SUCCESS.getStatus())
				.build());
		
		Assertions.assertNotNull(coletaQrCodeModelProcessed);
		Assertions.assertEquals(IntegrationStatusEnum.FAILED.getStatus(), coletaQrCodeModelProcessed.getIntegrationStatus());
	}

}
