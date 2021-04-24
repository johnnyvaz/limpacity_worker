package br.com.limpacity.worker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusInstalacao {
    INSTALADO("INSTALADO"),
    AGENDADO("AGENDADO"),
    MANUTENCAO("EM_MANUTENCAO");

    private String statusInstalacao;
}
