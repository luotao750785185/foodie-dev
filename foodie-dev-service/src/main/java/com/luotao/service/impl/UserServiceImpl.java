package com.luotao.service.impl;

import com.luotao.pojo.bo.UserBO;
import com.luotao.enums.Sex;
import com.luotao.mapper.UsersMapper;
import com.luotao.pojo.Users;
import com.luotao.service.UserService;
import com.luotao.utils.DateUtil;
import com.luotao.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author : luo
 * @date : 2020/3/14 23:44
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    @Transactional(propagation = Propagation.SUPPORTS)//查询用SUPPORTS即可
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample=new Example(Users.class);
        Example.Criteria userCriteria=userExample.createCriteria();//userCriteria中有很多方法
        userCriteria.andEqualTo("username",username);//username与数据库中的值对比
        Users result=usersMapper.selectOneByExample(userExample);//查询是否有一行
        return result==null?false:true;
    }
    @Transactional(propagation = Propagation.REQUIRED)//增删改
    @Override
    public Users createUser(UserBO userBO) {
        String userId=sid.nextShort();
        Users user=new Users();
        user.setId(userId);
        //用户名
        user.setUsername(userBO.getUsername());
        //用户昵称同用户名
        user.setNickname(userBO.getUsername());
        //默认头像
        user.setFace(USER_FACE);
        //设置默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        //设置默认性别(不直接写012，用更公用化的枚举)
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        try {
            //上传的密码不要以明文的方式，用md5加密的形式更加安全，因为服务器被攻破了就完全暴露了
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        usersMapper.insert(user);
        //返回给页面是为了在页面显示一些用户信息
        return user;
    }
    @Transactional(propagation = Propagation.SUPPORTS)//查询
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample=new Example(Users.class);
        Example.Criteria userCriteria=userExample.createCriteria();//userCriteria中有很多方法
        userCriteria.andEqualTo("username",username);//username与数据库中的值对比
        userCriteria.andEqualTo("password",password);//password与数据库中的值对比
        Users result=usersMapper.selectOneByExample(userExample);//查询username，username是否有一行
        return result;
    }
}
