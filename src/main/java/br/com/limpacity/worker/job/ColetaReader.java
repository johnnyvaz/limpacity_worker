package br.com.limpacity.worker.job;

import br.com.limpacity.worker.dao.ColetaRowMapper;
import br.com.limpacity.worker.dao.SchemaDatabaseUtil;
import br.com.limpacity.worker.model.ColetaQrCodeModel;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ColetaReader {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SchemaDatabaseUtil schemaUtil;
	
	private String getTableName() {
				
		return schemaUtil.getTableWithSchema("coleta_qrcode ");
		
	}
	
	private String getSelect() {
		
		return "SELECT * FROM " + getTableName()
				+ " WHERE integration_status IN ('N','R')";
	}
	
	public JdbcCursorItemReader<ColetaQrCodeModel> reader() {

		final JdbcCursorItemReader<ColetaQrCodeModel> jdbcCursor = new JdbcCursorItemReader<>();
		jdbcCursor.setDataSource(this.dataSource);
		jdbcCursor.setSql(getSelect());
		jdbcCursor.setRowMapper(new ColetaRowMapper());

		return jdbcCursor;
	}
}
