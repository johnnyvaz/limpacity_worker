package br.com.limpacity.worker.job.expired;

import br.com.limpacity.worker.model.ColetaQrCode;
import br.com.limpacity.worker.service.ColetaService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColetaFirstNotWriter implements ItemWriter<ColetaQrCode> {

	@Autowired
	private ColetaService service;

	@Override
	public void write(List<? extends ColetaQrCode> items) throws Exception {
		service.save(items);		
	}	
}
