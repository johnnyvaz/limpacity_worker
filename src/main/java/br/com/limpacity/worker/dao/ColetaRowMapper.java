package br.com.limpacity.worker.dao;

import br.com.limpacity.worker.model.ColetaQrCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColetaRowMapper implements RowMapper<ColetaQrCode> {

	public static final String ROW_ID = "ID";
	public static final String ROW_UUID = "UUID";
	public static final String ROW_ATIVO = "ATIVO";
	public static final String ROW_POSTO_ID = "POSTO_ID";
	public static final String ROW_CREATION_DATE = "CREATION_DATE";
	public static final String ROW_INTEGRATION_STATUS = "INTEGRATION_STATUS";
	
	@Override
	public ColetaQrCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ColetaQrCode.builder()
				.id(rs.getLong(ColetaRowMapper.ROW_ID))
				.uuid(rs.getString(ColetaRowMapper.ROW_UUID))
				.ativo(rs.getBoolean(ColetaRowMapper.ROW_ATIVO))
				.postoId(rs.getLong(ColetaRowMapper.ROW_POSTO_ID))
				.creationDate(rs.getDate(ColetaRowMapper.ROW_CREATION_DATE))
				.integrationStatus(rs.getString(ColetaRowMapper.ROW_INTEGRATION_STATUS))
				.id(rs.getLong(ColetaRowMapper.ROW_ID))
				.build();
	}




}
