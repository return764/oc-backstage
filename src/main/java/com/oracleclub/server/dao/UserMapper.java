package com.oracleclub.server.dao;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.param.UserQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (User)表数据库访问层
 *
 * @author RETURN
 * @since 2020-08-13 22:01:00
 */
@Repository
public interface UserMapper extends BaseDao<User,Long> {

    User findByEmail(@Param("email") String email);

    User findByStuNum(@Param("stuNum") String stuNum);

    IPage<User> findAllExistWithParams(IPage<User> pageable,@Param("u") UserQueryParam userParam);

    User findUserById(Long id);

    int updateUserInfoBy(@Param("u") User user);

    int updateLoginTimeBy(@Param("u") User user);

    int getUserCount();
}
