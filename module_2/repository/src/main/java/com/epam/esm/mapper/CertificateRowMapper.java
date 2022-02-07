package com.epam.esm.mapper;

import com.epam.esm.model.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CertificateRowMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate.builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .description(rs.getString(3))
                .price(rs.getBigDecimal(4))
                .duration(rs.getInt(5))
                .createDate(rs.getTimestamp(6).toLocalDateTime())
                .lastUpdateDate(rs.getTimestamp(7).toLocalDateTime())
                .build();
    }
}