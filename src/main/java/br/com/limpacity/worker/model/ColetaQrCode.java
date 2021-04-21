package br.com.limpacity.worker.model;

import br.com.limpacity.worker.util.IntegrationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ColetaQrCode {

	private Long id;

	@NotNull
	private String uuid;

	@NotNull
	private Boolean ativo;

	@NotNull
	private Long estacaoId;

	@NotNull
	private Date creationDate;

	@Setter
	private LocalDateTime updateDate;

	@Setter
	private String error;
	
	@Setter
	private String integrationStatus;

	@Setter
	private String integrationDescription;

	public String getIntegrationStatus() {
		if (integrationStatus==null)
		{
			return IntegrationStatusEnum.FAILED.getStatus();
		}		
		return integrationStatus;
	}
}
