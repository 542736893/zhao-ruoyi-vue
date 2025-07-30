package com.ruoyi.module.user.mapper;

import com.ruoyi.module.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserMapper 测试类
 *
 * @author ruoyi
 */
@SpringBootTest
@ActiveProfiles("test")
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUserList() {
        User user = new User();
        List<User> users = userMapper.selectUserList(user);
        assertNotNull(users);
        System.out.println("查询到用户数量: " + users.size());
        for (User u : users) {
            System.out.println("用户: " + u.getUsername() + " - 年龄: " + u.getAge());
        }
    }

    @Test
    public void testSelectUserByUsername() {
        // 先插入一个测试用户
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setAge(25);
        userMapper.insertUser(testUser);

        User user = userMapper.selectUserByUsername("testuser");
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        System.out.println("查询用户: " + user);

        // 清理测试数据
        userMapper.deleteUserById(user.getId());
    }

    @Test
    public void testSelectUserById() {
        // 先插入一个测试用户
        User testUser = new User();
        testUser.setUsername("testuser2");
        testUser.setAge(30);
        userMapper.insertUser(testUser);

        User user = userMapper.selectUserById(testUser.getId());
        assertNotNull(user);
        assertEquals(testUser.getId(), user.getId());
        System.out.println("查询用户: " + user);

        // 清理测试数据
        userMapper.deleteUserById(user.getId());
    }

    @Test
    public void testCheckUsernameUnique() {
        // 先插入一个测试用户
        User testUser = new User();
        testUser.setUsername("uniquetest");
        testUser.setAge(28);
        userMapper.insertUser(testUser);

        User user = userMapper.checkUsernameUnique("uniquetest");
        assertNotNull(user);
        assertEquals("uniquetest", user.getUsername());

        User nonExistUser = userMapper.checkUsernameUnique("nonexist");
        assertNull(nonExistUser);

        // 清理测试数据
        userMapper.deleteUserById(testUser.getId());
    }

    @Test
    public void testInsertAndDeleteUser() {
        User testUser = new User();
        testUser.setUsername("inserttest");
        testUser.setAge(35);

        int result = userMapper.insertUser(testUser);
        assertEquals(1, result);
        assertNotNull(testUser.getId());
        System.out.println("插入用户成功，ID: " + testUser.getId());

        // 删除测试用户
        int deleteResult = userMapper.deleteUserById(testUser.getId());
        assertEquals(1, deleteResult);
        System.out.println("删除用户成功");
    }

    @Test
    public void testCountUsers() {
        int count = userMapper.countUsers();
        assertTrue(count >= 0);
        System.out.println("用户总数: " + count);
    }
}
