package br.com.limpacity.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificaEmailDTO {

    @NotEmpty
    private String destino;

    @NotEmpty
    private String copia;

    @NotEmpty
    private String assunto;

    @NotEmpty
    private String mensagem;

    @NotEmpty
    private String descricaoColeta;

    private Date creationDate;

}
