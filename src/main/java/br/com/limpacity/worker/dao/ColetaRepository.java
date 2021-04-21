package br.com.limpacity.worker.dao;

import br.com.limpacity.worker.model.ColetaQrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ColetaRepository {
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SchemaDatabaseUtil schemaUtil;

	private String getTableName() {		
		
		return schemaUtil.getTableWithSchema("coleta_qrcode ");
		
	}
	
	private String getSelect() {
		
		return "select * from " + getTableName() 
				+ " WHERE id = ? ";

	}
	
	private String getUpdate() {
				
		 return  "UPDATE " + getTableName() + " "
		 		+ "SET ativo = 0 "
				+ "WHERE id = ? ";

	}



	public void update(List<? extends ColetaQrCode> coletaList) {
		this.jdbcTemplate.batchUpdate(getUpdate(), new ColetaPreparedStatementSetter(coletaList));
	}

	
	  public List<ColetaQrCode> getOne(Long Id) {
	  return this.jdbcTemplate.query(getSelect(), new Object[] { Id }, new ColetaRowMapper()); }
	 

	private class ColetaPreparedStatementSetter implements BatchPreparedStatementSetter {

		private final List<? extends ColetaQrCode> items;

		public ColetaPreparedStatementSetter(List<? extends ColetaQrCode> items) {
			super();
			this.items = items;
		}

		public void dataBind(PreparedStatement ps, final ColetaQrCode dto) throws SQLException {

			ps.setString(1, dto.getIntegrationStatus());

			ps.setString(3, dto.getIntegrationDescription());
			ps.setLong(4, dto.getEstacaoId());
			ps.setBoolean(5, dto.getAtivo());
			ps.setLong(6, dto.getId());

		}

		@Override
		public int getBatchSize() {
			return this.items.size();
		}

		@Override
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			this.dataBind(ps, this.items.get(i));
		}

	}

}
