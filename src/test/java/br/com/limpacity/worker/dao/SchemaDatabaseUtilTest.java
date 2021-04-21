package br.com.limpacity.worker.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@TestPropertySource(properties ={"spring.datasource.hikari.schema=coleta_qrcode"})
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = true)
@TypeExcludeFilters(JdbcTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@AutoConfigureJdbc
@AutoConfigureTestDatabase
@ImportAutoConfiguration
@ComponentScan
@Sql(value = "classpath:pull/coletaQrCode.sql")
class SchemaDatabaseUtilTest {

	@Autowired
	SchemaDatabaseUtil util;
	
	
	@Test
	void schemaNotEmpty() {
		
		var tableNameWithSchema = util.getTableWithSchema("Table");
		Assertions.assertEquals("coleta_qrcode.Table", tableNameWithSchema);
		
	}
}
