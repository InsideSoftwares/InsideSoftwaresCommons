package br.com.insidesoftwares.exception.error;

import br.com.insidesoftwares.commons.specification.ExceptionCode;
import lombok.Getter;

@Getter
public class InsideSoftwaresException extends RuntimeException {
    private final String code;
	private final String[] args;

	public InsideSoftwaresException(ExceptionCode code) {
		super();
		this.code = code.getCode();
		this.args = null;
	}

	public InsideSoftwaresException(ExceptionCode code, String... args) {
		super();
		this.code = code.getCode();
		this.args = args;
	}
}
