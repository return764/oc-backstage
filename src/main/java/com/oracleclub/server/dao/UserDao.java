package com.oracleclub.server.dao;


import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.User;

/**
 * (User)表数据库访问层
 *
 * @author RETURN
 * @since 2020-08-13 22:01:00
 */
public interface UserDao extends BaseDao<User,Long> {

    User findByEmail(String email);
    User findByStuNum(String stuNum);
}