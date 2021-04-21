package br.com.limpacity.worker.job;

import br.com.limpacity.worker.model.ColetaQrCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ColetaQrCodeProcessorTest {

	@InjectMocks
    ColetaProcessor processor;
		
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void process() {
		
		ColetaQrCode coletaQrCode = ColetaQrCode.builder().build();
		Assertions.assertDoesNotThrow(()-> processor.process(coletaQrCode));
		
	}
	
}
