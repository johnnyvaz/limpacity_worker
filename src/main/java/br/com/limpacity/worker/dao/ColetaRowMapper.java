package br.com.limpacity.worker.dao;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColetaRowMapper implements RowMapper<ColetaQrCodeModel> {

	public static final String ROW_ID = "ID";
	public static final String ROW_UUID = "UUID";
	public static final String ROW_ATIVO = "ATIVO";
	public static final String ROW_POSTO_ID = "POSTO_ID";
	public static final String ROW_QTDE_NOT_EMAIL = "QTDE_NOT_EMAIL";
	public static final String ROW_CREATION_DATE = "CREATION_DATE";
	public static final String ROW_DATA_ULTIMO_EMAIL = "DATA_ULTIMO_EMAIL";
	public static final String ROW_INTEGRATION_STATUS = "INTEGRATION_STATUS";
	
	@Override
	public ColetaQrCodeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ColetaQrCodeModel.builder()
				.id(rs.getLong(ColetaRowMapper.ROW_ID))
				.uuid(rs.getString(ColetaRowMapper.ROW_UUID))
				.ativo(rs.getBoolean(ColetaRowMapper.ROW_ATIVO))
				//.posto(rs.getLong(ColetaRowMapper.ROW_POSTO_ID))
				.creationDate(rs.getDate(ColetaRowMapper.ROW_CREATION_DATE))
				.qtdeNotEmail(rs.getInt(ColetaRowMapper.ROW_QTDE_NOT_EMAIL))
				.dataUltimoEmail(rs.getDate(ColetaRowMapper.ROW_DATA_ULTIMO_EMAIL))
				.integrationStatus(rs.getString(ColetaRowMapper.ROW_INTEGRATION_STATUS))
				.id(rs.getLong(ColetaRowMapper.ROW_ID))
				.build();
	}


}
