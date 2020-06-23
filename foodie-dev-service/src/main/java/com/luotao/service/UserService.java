package com.luotao.service;

import com.luotao.pojo.bo.UserBO;
import com.luotao.pojo.Users;

/**
 * @author : luo
 * @date : 2020/3/14 23:37
 */
public interface UserService {
    /**
    * 判断用户名是否存在
     * @return
     */
    boolean queryUsernameIsExist(String username);
    /**
     * 创建用户
     * @return
     */
    public Users createUser(UserBO userBO);

    /**
     * 检索用户名和密码是否匹配
     * @return
     */
    public Users queryUserForLogin(String username,String password);

}
