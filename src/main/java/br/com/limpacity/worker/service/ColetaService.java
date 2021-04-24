package br.com.limpacity.worker.service;

import br.com.limpacity.worker.model.ColetaQrCode;

import java.util.List;

public interface ColetaService {

	void sendMsg(List<ColetaQrCode> coletas);

	void save(List<? extends ColetaQrCode> coletas);

}
