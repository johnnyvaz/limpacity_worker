package br.com.limpacity.worker.job;

import br.com.limpacity.worker.model.ColetaQrCode;
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
public class ColetaWriter implements ItemWriter<ColetaQrCode> {

	@Autowired
	private ColetaService service;

	public List<ColetaQrCode> validate(List<? extends ColetaQrCode> coletas) {

		final List<ColetaQrCode> coletaQrCodeValidList = new ArrayList<>();
		coletas.forEach(coleta -> {
			if (this.isValid(coleta)) {
				coletaQrCodeValidList.add(coleta);
			}
		});
		return coletaQrCodeValidList;

	}

	public boolean isValid(ColetaQrCode coletaQrCode) {

		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		final Validator validator = factory.getValidator();
		final Set<ConstraintViolation<ColetaQrCode>> violations = validator.validate(coletaQrCode);
		if (!violations.isEmpty()) {
			final StringBuilder integrationErrorValidation = new StringBuilder();
			ColetaWriter.log.error("Coleta invalid. {}", coletaQrCode.toString());
			for (final ConstraintViolation<ColetaQrCode> violation : violations) {
				ColetaWriter.log.error(violation.getMessage());
				integrationErrorValidation.append(violation.getMessage());
			}
			coletaQrCode.setIntegrationStatus("E");
			coletaQrCode.setError(integrationErrorValidation.toString());
		}
		return violations.isEmpty();
	}

	@Override
	public void write(List<? extends ColetaQrCode> items) throws Exception {

//		this.service.integrate(this.validate(items));

	}

}
