package br.com.limpacity.worker.dao;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

class ColetaQrCodeModelRowMapperTest {

	@Test
	void testNewColeta() throws SQLException {

		final Long ROW_ID_VALUE = 1L;
		final String ROW_UUID_VALUE = "e4eb4c0a-2042-4dc1-a7be-1403248fd955";
		final Long ROW_POSTO_ID_VALUE = 1L;
		final String ROW_INTEGRATION_STATUS_VALUE = "E";

		// given
		final ColetaRowMapper rowMapper = new ColetaRowMapper();
		final ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(resultSet.getLong(ColetaRowMapper.ROW_ID)).thenReturn(ROW_ID_VALUE);
		Mockito.when(resultSet.getString(ColetaRowMapper.ROW_UUID)).thenReturn(ROW_UUID_VALUE);
		Mockito.when(resultSet.getBoolean(ColetaRowMapper.ROW_ATIVO)).thenReturn(true);
		Mockito.when(resultSet.getLong(ColetaRowMapper.ROW_POSTO_ID)).thenReturn(ROW_POSTO_ID_VALUE);
		Mockito.when(resultSet.getString(ColetaRowMapper.ROW_INTEGRATION_STATUS)).thenReturn(ROW_INTEGRATION_STATUS_VALUE);

		// when
		final ColetaQrCodeModel dto = rowMapper.mapRow(resultSet, 1);

		// then
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(),ROW_ID_VALUE);
		Assertions.assertEquals(dto.getUuid(),ROW_UUID_VALUE);
		Assertions.assertEquals(dto.getAtivo(), true);
//		Assertions.assertEquals(dto.getPostoId(),ROW_POSTO_ID_VALUE);
		Assertions.assertEquals(dto.getIntegrationStatus(),ROW_INTEGRATION_STATUS_VALUE);

	}

}
