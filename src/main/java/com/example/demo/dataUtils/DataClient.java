package com.example.demo.dataUtils;

import com.example.demo.data.SysUser;
import com.example.demo.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DataClient {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    public static void main(String[] args) {
        for (SysUser sysUser : viewData()) {
            System.out.println(sysUser);
        }
    }

    public static void insertData(String tag, String name, String isCp) {
        String insertSql = "insert into sys_user value(null,?,?,?)";
        jdbcTemplate.update(insertSql, tag, name, isCp);
    }

    public static Long searchData(String tag) {
        String searchSql = "select count(id) from sys_user where tag = ?";
        return jdbcTemplate.queryForObject(searchSql, Long.class, tag);
    }

    public static List<SysUser> viewData() {
        String viewSql = "select * from sys_user";
        return jdbcTemplate.query(viewSql, new BeanPropertyRowMapper<SysUser>(SysUser.class));
    }
}
