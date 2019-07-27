package com.example.demo.dao.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.common.jdbc.JdbcTemplateBaseDao;
import com.example.demo.common.jdbc.SqlBuilder;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDaoImpl extends JdbcTemplateBaseDao implements UserDao {
    private Logger logger = LoggerFactory.getLogger(UserDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        if (jdbcTemplate.getDataSource() instanceof DruidDataSource) {
            logger.info("验证确实通过@Bean注入了DruiDataSource数据源");
        }
        SqlBuilder sqlBuilder = new SqlBuilder("select * from `user`");
        return super.query(sqlBuilder);

    }

    @Override
    public User get(Integer id) {
        return super.queryForObject(id);
    }

    @Override
    public void update(Integer id, String username, String password) {
        SqlBuilder sqlBuilder = new SqlBuilder("UPDATE `USER`");
        sqlBuilder.set("username", username);
        sqlBuilder.set("password", password);
        super.update(id, sqlBuilder);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    public void add(String username, String password) {
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        super.add(user);
    }

    @Override
    protected Class<?> getEntityClass() {
        return User.class;
    }
}
