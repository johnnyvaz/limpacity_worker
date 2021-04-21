package br.com.limpacity.worker.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SchemaDatabaseUtil {
	
	@Value("${spring.datasource.hikari.schema: }")
	private String schema;
	
	public  String getTableWithSchema(String tableName) {
			
		if (schema.trim().length()==0)
			return tableName;
		
		return schema + "." + tableName;
	}

}
