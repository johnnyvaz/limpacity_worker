package br.com.limpacity.worker.util;

public enum IntegrationStatusEnum {

	WAITING("N",false), SUCCESS("S", false), FAILED("E", true), RETRY_LATER("R", true);

	private String status;

	private boolean descriptionRequired;

	IntegrationStatusEnum(String status, Boolean descriptionRequired) {
		this.status = status;
		this.descriptionRequired = descriptionRequired;
	}

	public String getStatus() {
		return this.status;
	}

	public boolean isDescriptionRequired() {
		return this.descriptionRequired;
	}

}
