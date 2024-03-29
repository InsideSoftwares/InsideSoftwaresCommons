package br.com.insidesoftwares.commons.configuration.mysql;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

import java.io.Serial;

@Component
public class MySQLUpperCaseStrategy extends PhysicalNamingStrategyStandardImpl {
	@Serial
	private static final long serialVersionUID = 1383021413247872469L;

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return Identifier.toIdentifier(name.getText().toUpperCase());
	}
}
