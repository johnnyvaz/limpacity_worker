package br.com.limpacity.worker.job;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ColetaQrCodeModelProcessorTest {

	@InjectMocks
    ColetaProcessor processor;
		
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void process() {
		
		ColetaQrCodeModel coletaQrCodeModel = ColetaQrCodeModel.builder().build();
		Assertions.assertDoesNotThrow(()-> processor.process(coletaQrCodeModel));
		
	}
	
}
