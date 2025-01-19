package com.banking.banking_service.domain;

import org.hibernate.HibernateException;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomerIdGenerator implements IdentifierGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Serializable generate(org.hibernate.engine.spi.SharedSessionContractImplementor session, Object object) throws HibernateException {
        // Query the database to get the max id (this is just an example, adjust as per your DB table and query)
        String sql = "SELECT MAX(id) FROM customers";
        Integer maxId = jdbcTemplate.queryForObject(sql, Integer.class);

        // If no customer exists, start with 1000000 (7-digit ID)
        int newId = (maxId != null ? maxId + 1 : 1000000);

        // Ensure it's a 7-digit number
        String formattedId = String.format("%07d", newId);

        return Integer.parseInt(formattedId);
    }

}
