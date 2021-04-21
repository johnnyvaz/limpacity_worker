package br.com.limpacity.worker.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColetaContentRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SchemaDatabaseUtil schemaUtil;
	
	private String getTableName() {
		
		return schemaUtil.getTableWithSchema("INT_S_DET_PEDIDO_SORTER ");
		
	}
	
	private String getSelect() {
		
		return "SELECT " + 
				"    NU_PEDIDO_ORIGEM," + 
				"    CD_CARGA," + 
				"    CD_PRODUTO," + 
				"    NU_SEPARACAO," + 
				"    ID_CARGA_PCP," + 
				"    TP_PEDIDO," + 
				"    CD_BARRAS," + 
				"    ID_PICKING_RAPIDO," + 
				"    COUNT(*) AS quantity" + 
				" FROM " + getTableName() + 
				" WHERE ID_CAB_PEDIDO_SORTER = ?" + 
				" GROUP BY" + 
				"    NU_PEDIDO_ORIGEM," + 
				"    CD_CARGA," + 
				"    CD_PRODUTO," + 
				"    NU_SEPARACAO," + 
				"    ID_CARGA_PCP," + 
				"    TP_PEDIDO," + 
				"    CD_BARRAS," + 
				"    ID_PICKING_RAPIDO";
	}

}
