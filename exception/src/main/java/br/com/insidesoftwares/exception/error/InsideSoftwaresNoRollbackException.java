package br.com.insidesoftwares.exception.error;

import br.com.insidesoftwares.commons.specification.ExceptionCode;
import lombok.Getter;

@Getter
public class InsideSoftwaresNoRollbackException extends RuntimeException{
    private final String code;
	public InsideSoftwaresNoRollbackException(ExceptionCode code) {
		super();
		this.code = code.getCode();
	}
}
