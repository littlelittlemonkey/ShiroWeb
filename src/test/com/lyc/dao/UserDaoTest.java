package com.lyc.dao;

import com.lyc.entity.User;
import com.lyc.cache.JedisUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-dao.xml")
public class UserDaoTest extends TestCase {
    @Resource
    private UserDao userDao;
    @Autowired
    private JedisUtil jedisUtil;

    @Test
    public void testGetByUserName() throws Exception {
        User user = (User)userDao.getByUserName("jack");
        System.out.println(user.getPassword());
    }
    @Test
    public void testJedisUtil(){
        System.out.println("--testJedisUtil--");
        System.out.println(jedisUtil.getJedis().get("jack"));
        System.out.println(jedisUtil.getClass().getName());
    }

    public void testGetRoles() throws Exception {
    }

    public void testGetPermissions() throws Exception {
    }

}