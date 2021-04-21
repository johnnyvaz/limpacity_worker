package br.com.limpacity.worker.job.expired;

import br.com.limpacity.worker.dao.ColetaRowMapper;
import br.com.limpacity.worker.dao.SchemaDatabaseUtil;
import br.com.limpacity.worker.model.ColetaQrCode;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class ColetaExpiredReader {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SchemaDatabaseUtil schemaUtil;
	
	@Value("${worker.retry.maxtime}")
	private Double maxTimeHour;
	
	private String getTableName() {
				
		return schemaUtil.getTableWithSchema("INT_S_CAB_PEDIDO_SORTER ");
		
	}
	
	private String getSelect() {
		
		return "select * from " + getTableName()
				+ " WHERE ID_PROCESSADO IN ('R') AND ID_MODELO_ONDA_SORTER = 1 AND " 
				+ getFilterDifHour();
	}
	
	private String getFilterDifHour() {
				
		return "24 * (sysdate - dt_addrow)>" + maxTimeHour;
		
	}
	
	
	public JdbcCursorItemReader<ColetaQrCode> reader() {

		final JdbcCursorItemReader<ColetaQrCode> jdbcCursor = new JdbcCursorItemReader<>();
		jdbcCursor.setDataSource(this.dataSource);
		jdbcCursor.setSql(getSelect());
		jdbcCursor.setRowMapper(new ColetaRowMapper());

		return jdbcCursor;
	}
}
