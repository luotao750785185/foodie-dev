package com.luotao.controller;

import com.luotao.pojo.bo.UserBO;
import com.luotao.pojo.Users;
import com.luotao.service.UserService;
import com.luotao.utils.CookieUtils;
import com.luotao.utils.IMOOCJSONResult;
import com.luotao.utils.JsonUtils;
import com.luotao.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : luo
 * @date : 2020/3/14 23:35
 */
//@ApiIgnore //不显示在swagger文档
@Api(value = "注册登录", tags = {"用于注册登录的相关登录"})//描述接口信息，文档中就显示tags中的了
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")//GET必须大写
    @GetMapping("/usernameIsExist")//查询所以用get请求
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {
        //1.判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        //3.请求成功，用户名不存在
        return IMOOCJSONResult.ok();//HttpStatus.OK是200状态码，可以查看HttpStatus看具体的代码
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")//提交所以用post请求
    public IMOOCJSONResult register(@RequestBody UserBO userBO,
                                    HttpServletRequest request,
                                    HttpServletResponse response ) {//@RequestBody 是接收json对象的
        //0.判断用户名密码不能为空
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //1.查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        //2.密码长度不能小于六位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不少于6位");
        }
        //3.判断两次密码是否相同
        if (!password.equals(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("两次密码不一致");
        }
        //4.实现注册
        Users result = userService.createUser(userBO);
        result=setNullProperty(result);
        //isEncode是是否加密以免浏览器直接看见值，"user"必须与前端的匹配，前端是按这个键取值的
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(result),true);
        //3.请求成功，用户名不存在
        return IMOOCJSONResult.ok();//HttpStatus.OK是200状态码，可以查看HttpStatus看具体的代码
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")//提交所以用post请求
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response ) throws Exception {//@RequestBody 是接收json对象的
        //0.判断用户名密码不能为空
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //1.实现登录
        Users usersResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password) );//异常提交上级
        if (usersResult==null) {
            return IMOOCJSONResult.errorMsg("用户名或密码错误");
        }
        usersResult=setNullProperty(usersResult);
        /**1、isEncode是是否加密以免浏览器直接看见值，
         * 2、"user"必须与前端的匹配，前端是按这个键取值的
         *  3、这种方式是将cookie在后端生成返回给前端，这样后端就是无状态的（不存在session了，不会占服务器内存，京东就是这样的），
         *  所以登录或者注册后可以在前端F12->Application->cookies的请求中看见user这样的cookie键值对。
         */
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(usersResult),true);
        //2.登录成功，这里不能直接返回数据库返回的对象
        return IMOOCJSONResult.ok(usersResult);//HttpStatus.OK是200状态码，可以查看官方HttpStatus类看具体的代码
    }
    //去掉一些敏感信息（当然也可以在实体类中属性加@JsonIgnore,这样在返回前端时自动忽略相应属性，但是不介意这样做）
    private Users setNullProperty( Users userResult){
        userResult.setPassword(null);
        userResult.setBirthday(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        return userResult;

    }
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")//查询所以用get请求
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response ) {
        //1、清除用户的cookie
        CookieUtils.deleteCookie(request,response,"user");

        //2、清空购物车

        //3、分布式会话中清除用户数据

        return IMOOCJSONResult.ok();//HttpStatus.OK是200状态码，可以查看HttpStatus看具体的代码
    }

}
