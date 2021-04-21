package br.com.limpacity.worker.service;

import br.com.limpacity.worker.model.ColetaQrCode;

import java.util.List;

public interface ColetaService {

//	void integrate(List<? extends Coleta> coletas);

//	void integrateColetas(List<? extends Coleta> coletas);

	void save(List<? extends ColetaQrCode> coletas);

}
