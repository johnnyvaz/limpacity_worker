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
				
		return schemaUtil.getTableWithSchema("coleta_qrcode ");
		
	}
	
	private String getSelect() {
		
		return "select * from " + getTableName()
				+ " WHERE integration_status IN ('R') AND " 
				+ getFilterDifHour();
	}
	
	private String getFilterDifHour() {
				
		return "1 * (SYSDATE() - creation_date)>" + maxTimeHour;
		
	}
	
	
	public JdbcCursorItemReader<ColetaQrCode> reader() {

		final JdbcCursorItemReader<ColetaQrCode> jdbcCursor = new JdbcCursorItemReader<>();
		jdbcCursor.setDataSource(this.dataSource);
		jdbcCursor.setSql(getSelect());
		jdbcCursor.setRowMapper(new ColetaRowMapper());

		return jdbcCursor;
	}
}
