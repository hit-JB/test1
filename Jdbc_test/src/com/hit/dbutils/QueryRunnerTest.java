package com.hit.dbutils;

import com.hit.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.Connection;

public class QueryRunnerTest {
    @Test
    public void testInsert () throws Exception
    {
        QueryRunner runner = new QueryRunner();
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        int i = runner.update(connection,sql, "caixunkun", "caixunkun@.com", "19980228");
        System.out.println("Insert "+i+"data");
    }
}
