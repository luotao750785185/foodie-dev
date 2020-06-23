package com.luotao.controller;

import com.luotao.pojo.Orders;
import com.luotao.service.center.MyOrdersService;
import com.luotao.utils.IMOOCJSONResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author : luo
 * @date : 2020/3/21 19:55
 */
public  class BaseController {
    /**
     * final（终态、只读）:
     * 1.修饰类时，此类永远不能被继承，类中的所有方法都隐式为final修饰的
     * 2.修饰方法时，此方法不能被子类重写。如果此方法被private修饰（类中private修饰的方法隐式为final的）
     *   ，子类无法继承此方法，子类可以有一样的方法（此时不属于重写，属于新建）。
     * 3.修饰变量时，初始化后此值不能再改变；修饰对象引用后，引用不再改变（因而指向的内存空间也不能再改变，
     *        就不能再指向别的对象了，但是指向的对象的属性是可以改变的）
     * 4.final修饰的方法比普通方法快，因为它在编译时就静态绑定了，而不需在运行时动态绑定
     */
    public static final Integer COMMON_PAGE_SIZE=10;
    public static final Integer PAGE_SIZE=20;

    public static final String FOODIE_SHOPCART = "shopcart";

    // 支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";		// produce

    // 成功 -> 支付中心 -> 平台
    //                       |-> 回调通知的url
    String payReturnUrl = "http://111.231.66.209:8088/foodie-dev-api/orders/notifyMerchantOrderPaid";

    // 用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces" +
            File.separator + "images" +
            File.separator + "foodie" +
            File.separator + "faces";
//    public static final String IMAGE_USER_FACE_LOCATION = "/workspaces/images/foodie/faces";


    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public IMOOCJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return IMOOCJSONResult.errorMsg("订单不存在！");
        }
        return IMOOCJSONResult.ok(order);
    }

}
