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
public class ColetaFirstNotReader {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SchemaDatabaseUtil schemaUtil;
	
	@Value("${notificacoes.not1}")
	private Double firstNotTime;
	
	private String getTableName() {
				
		return schemaUtil.getTableWithSchema("coleta_qrcode ");
		
	}
	
	private String getSelect() {
		
		return "select * from " + getTableName()
				+ " WHERE integration_status IN ('R', 'N') AND " +
				" data_ultimo_email*1 - (creation_date * 1) > 10000 ";
//				+ getFilterDifHour();
	}
	
//	private String getFilterDifHour() {
//		var tempo = "1 * (SYSDATE() - data_ultimo_email)>" + firstNotTime;
//		System.out.println("tempo ----------------> " + tempo);
//		return tempo;
//	}
	
	
	public JdbcCursorItemReader<ColetaQrCode> reader() {

		final JdbcCursorItemReader<ColetaQrCode> jdbcCursor = new JdbcCursorItemReader<>();
		jdbcCursor.setDataSource(this.dataSource);
		jdbcCursor.setSql(getSelect());
		jdbcCursor.setRowMapper(new ColetaRowMapper());

		return jdbcCursor;
	}
}
