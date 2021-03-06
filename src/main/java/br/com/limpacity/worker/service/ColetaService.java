package br.com.limpacity.worker.service;

import br.com.limpacity.worker.model.ColetaQrCodeModel;

import java.util.List;

public interface ColetaService {

	List<ColetaQrCodeModel> findAllColetasOpen();

	void sendMsg(List<ColetaQrCodeModel> coletas);

	void save(List<? extends ColetaQrCodeModel> coletas);

}
