package com.luotao.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : luo
 * @date : 2020/3/16 12:22
 */
@Aspect//切面注解
@Component
public class ServiceLogAspect {

    //注意导入的包 org.slf4j.Logger
    public static final Logger log= LoggerFactory.getLogger(ServiceLogAspect.class);
    /**
     * AOP通知：
     * 1.前置通知：在方法调用之前通知
     * 2.后置通知：在方法正常调用之后通知（不报错）
     * 3.环绕通知：在方法调用之前和调用之后都可以执行通知
     * 4.异常通知：方法调用过程中出现异常通知
     * 5.最终通知：在方法调用之后执行
     */


    /**
     *切面表达式
     * execution 代表所要执行的表达式主体
     * 第一个*代表返回类型为所有
     * 第二处包名表示aop监控的类所在的包
     * 第三处 ..代表该包以及其子包下的所有
     * 第四处 * 表示所有类
     * 第五处  *(..) 表示类中的所有方法（..表示参数为任意类型）
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.luotao.service.impl..*.*(..))")
    public Object  recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("===开始执行{}.{}===",
                                    joinPoint.getTarget().getClass(),  //某个类
                                    joinPoint.getSignature().getName()); //某方法
        //记录开始时间
        long beignTime=System.currentTimeMillis();
        //执行目标service
        Object result=joinPoint.proceed();

        //记录结束时间
        long endTime=System.currentTimeMillis();
        long takeTime=endTime-beignTime;
        if (takeTime >= 3000) {
            log.error("==执行结束，耗时：{}毫秒==",takeTime);
        }else if (takeTime >=2000) {
            log.warn("==执行结束，耗时：{}毫秒==",takeTime);
            
        }else{
            log.info("==执行结束，耗时：{}毫秒==",takeTime);
        }
        return result;
    }

}
