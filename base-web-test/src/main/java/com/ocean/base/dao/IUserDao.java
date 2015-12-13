package com.ocean.base.dao;

import com.ocean.base.pojo.User;
/**
 * 描述：
 * 
 * @author ocean
 * 2015年12月12日
 * email：zhangjunfang0505@163.com
 */
public interface IUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}