package br.com.limpacity.worker.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import br.com.limpacity.worker.job.expired.ColetaFirstNotWriter;
import br.com.limpacity.worker.util.IntegrationStatusEnum;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ComponentScan("br.com.limpacity.worker")
@OverrideAutoConfiguration(enabled = true)
@TypeExcludeFilters(JdbcTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@AutoConfigureJdbc
@AutoConfigureTestDatabase
@TestPropertySource(properties= {"spring.batch.limit-thread=5", "spring.batch.chunk=2","worker.retry.maxtime=1"})
@Sql(value = "classpath:pull/coletaQrCode.sql")
class ColetaQrCodeModelExpiredWriterTest {
	
	@Autowired
	private ColetaFirstNotWriter writer;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
		
	@Test
	void saveFailed() {
		List<ColetaQrCodeModel> coletaQrCodeModelList = new ArrayList<>();
		ColetaQrCodeModel coletaQrCodeModel = ColetaQrCodeModel.builder()
				.ativo(true)
//				.postoId(1L)
				.uuid("ebda9df8-b301-4fd8-9779-f89564bdf6ba")
				.id(3L)
				.updateDate(new Date())
				.integrationStatus(IntegrationStatusEnum.SUCCESS.getStatus())
				.build();
		coletaQrCodeModelList.add(coletaQrCodeModel);
		Assertions.assertDoesNotThrow(()->writer.write(coletaQrCodeModelList));
	}
}
