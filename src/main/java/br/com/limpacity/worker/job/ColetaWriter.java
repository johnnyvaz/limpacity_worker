package br.com.limpacity.worker.job;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import br.com.limpacity.worker.service.ColetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ColetaWriter implements ItemWriter<ColetaQrCodeModel> {

	@Autowired
	private ColetaService service;

	public List<ColetaQrCodeModel> validate(List<? extends ColetaQrCodeModel> coletas) {

		final List<ColetaQrCodeModel> coletaQrCodeModelValidList = new ArrayList<>();
		coletas.forEach(coleta -> {
			if (this.isValid(coleta)) {
				coletaQrCodeModelValidList.add(coleta);
			}
		});
		return coletaQrCodeModelValidList;

	}

	public boolean isValid(ColetaQrCodeModel coletaQrCodeModel) {

		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		final Validator validator = factory.getValidator();
		final Set<ConstraintViolation<ColetaQrCodeModel>> violations = validator.validate(coletaQrCodeModel);
		if (!violations.isEmpty()) {
			final StringBuilder integrationErrorValidation = new StringBuilder();
			ColetaWriter.log.error("Coleta invalid. {}", coletaQrCodeModel.toString());
			for (final ConstraintViolation<ColetaQrCodeModel> violation : violations) {
				ColetaWriter.log.error(violation.getMessage());
				integrationErrorValidation.append(violation.getMessage());
			}
			coletaQrCodeModel.setIntegrationStatus("E");
			coletaQrCodeModel.setError(integrationErrorValidation.toString());
		}
		return violations.isEmpty();
	}

	@Override
	public void write(List<? extends ColetaQrCodeModel> items) throws Exception {

		this.service.sendMsg(this.validate(items));

	}

}
