package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DatabaseUtil {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    public DatabaseUtil(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * 执行查询操作
     */
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        try {
            return jdbcTemplate.query(sql, rowMapper, args);
        } catch (Exception e) {
            log.error("Error executing query: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 执行查询单个对象
     */
    public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
        try {
            return jdbcTemplate.queryForObject(sql, requiredType, args);
        } catch (Exception e) {
            log.error("Error executing queryForObject: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 执行更新操作
     */
    public int update(String sql, Object... args) {
        try {
            return jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            log.error("Error executing update: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 批量更新操作
     */
    public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
        try {
            return jdbcTemplate.batchUpdate(sql, batchArgs);
        } catch (Exception e) {
            log.error("Error executing batch update: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 在事务中执行操作
     */
    public <T> T executeInTransaction(TransactionCallback<T> action) {
        return transactionTemplate.execute(status -> {
            try {
                return action.doInTransaction();
            } catch (Exception e) {
                log.error("Error executing in transaction: {}", e.getMessage());
            }
            return null;
        });
    }

    /**
     * 查询返回Map列表
     */
    public List<Map<String, Object>> queryForList(String sql, Object... args) {
        try {
            return jdbcTemplate.queryForList(sql, args);
        } catch (Exception e) {
            log.error("Error executing queryForList: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 执行DDL语句
     */
    public void execute(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            log.error("Error executing DDL: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 获取数据库连接
     */
    public Connection getConnection() throws SQLException {
        return jdbcTemplate.getDataSource().getConnection();
    }

    /**
     * 检查数据库连接
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(5);  // 5 seconds timeout
        } catch (SQLException e) {
            log.error("Database connection test failed: {}", e.getMessage());
            return false;
        }
    }
}

@FunctionalInterface
interface TransactionCallback<T> {
    T doInTransaction() throws Exception;
}