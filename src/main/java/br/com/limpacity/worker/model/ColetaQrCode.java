package br.com.limpacity.worker.model;

import br.com.limpacity.worker.util.IntegrationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColetaQrCode {

	private Long id;

	@NotNull
	private String uuid;

	@NotNull
	private Boolean ativo;

	@NotNull
	private Long postoId;

	@NotNull
	private Date creationDate;

	private Date updateDate;

	private String error;
	
	private String integrationStatus;

	private Integer qtdeNotEmail;

	public String getIntegrationStatus() {
		if (integrationStatus==null)
		{
			return IntegrationStatusEnum.FAILED.getStatus();
		}		
		return integrationStatus;
	}

	private Date dataUltimoEmail;

	private String campoTest;

}
