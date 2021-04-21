package br.com.limpacity.worker.service.impl;

import br.com.limpacity.worker.dao.ColetaRepository;
import br.com.limpacity.worker.model.ColetaQrCode;
import br.com.limpacity.worker.service.ColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColetaQrCodeServiceImpl implements ColetaService {

//	@Autowired
//	private ProductionControlApiClient apiService;

	@Autowired
	private ColetaRepository coletaRepository;

//	@Override
//	public void integrate(List<? extends Coleta> coletas) {
//		this.integrateProductionControl(coletas);
//		this.save(coletas);
//	}

//	@Override
//	public void integrateProductionControl(List<? extends Coleta> coletas) {
//		coletas.parallelStream().forEach(coleta -> 	apiService.postSeparation(coleta));
//
//	}

	@Override
	public void sendMsg(List<? extends ColetaQrCode> coletas) {
		System.out.println("coleta service ---> " + coletas);
		coletas.forEach(coleta -> {
			coleta.setIntegrationStatus("S");
		});
		this.save(coletas);
	}

	@Override
	public void save(List<? extends ColetaQrCode> coletas) {
		this.coletaRepository.update(coletas);
	}

}
