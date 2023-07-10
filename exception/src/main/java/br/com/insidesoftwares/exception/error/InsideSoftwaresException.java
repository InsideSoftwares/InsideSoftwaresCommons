package br.com.insidesoftwares.exception.error;

import br.com.insidesoftwares.commons.specification.ExceptionCode;
import lombok.Getter;

@Getter
public class InsideSoftwaresException extends RuntimeException {
    private final String code;
	private final Object[] args;

	public InsideSoftwaresException(ExceptionCode code) {
		super();
		this.code = code.getCode();
		this.args = null;
	}

	public InsideSoftwaresException(ExceptionCode code, Object... args) {
		super();
		this.code = code.getCode();
		this.args = args;
	}
}
