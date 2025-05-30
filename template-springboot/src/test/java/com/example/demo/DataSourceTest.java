package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DataSourceTest {

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    @Qualifier("secondaryDataSource")
    private DataSource secondaryDataSource;

    @Test
    void testDataSources() throws SQLException {
        Assertions.assertNotNull(primaryDataSource);
        Assertions.assertNotNull(secondaryDataSource);

        try (Connection conn = primaryDataSource.getConnection()) {
            Assertions.assertTrue(conn.isValid(1));
        }

        try (Connection conn = secondaryDataSource.getConnection()) {
            Assertions.assertTrue(conn.isValid(1));
        }
    }
}