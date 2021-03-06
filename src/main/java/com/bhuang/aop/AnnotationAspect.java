package com.bhuang.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * @author: huangbin
 * @description: 注解方式的切面类
 * @date: Created in 2019/4/29
 * @modified By:
 */
@Component
@Aspect
public class AnnotationAspect {
    /**
     * @Pointcut 声明切入点
     * 注意：1.作为切入点的方法必须返回void类型
     *      2.方法没有参数,用private修饰
     *      3.方法体为空
     *
     * execution 匹配方法执行的连接点
     * execution切入点指示符表达式：
     * execution( modifiers-pattern? 方法的修饰符模式（非必须）比如：public
     *            ret-type-pattern: 返回类型模式（必须）返回类型模式  比如：* 代表匹配任意的返回类型，String 代表返回类型为String类型
     *            declaring-type-pattern: 路径模式（非必须）方法所在的类路径 比如：com.bhuang.aop.TargetObject
     *            name-pattern: 方法名称（必须）比如：* 代表类中的所有方法, save* 类中以save开始的所以方法
     *            param-pattern:参数模式（必须）比如：() 匹配一个不接受任何参数的方法, (*) 匹配接受任意数量参数的方法, (String,*) 匹配一个接受两个参数的方法
     *            throws-pattern:异常模式（非必须）比如：throws Exception 抛出异常）
     */
    @Pointcut("execution(* com.bhuang.aop.TargetObjectByAnnotation.*(..))")
    public void point() {
    }

    /**
     * 前置通知 在方法调用前执行
     * point() 定义的切入点
     */
    @Before("point()")
    public void beforeAdvice() {
        System.out.println("注解方式-beforeAdvice");
    }

    /**
     * args 限定匹配特定的连接点，其中参数是指定类型的实例
     * 参数名称必须与通知的参数类型名称一致
     * 通知参数的类型必须与目标对象参数的类型一致（不一致拦截不到）
     * @param var1
     * @param var2
     */
    @Before("point() && args(var1,var2)")
    public void beforeAdvice2(String var1, String var2) {
        System.out.println("注解方式-beforeAdvice2  var1：" + var1 + "   var2：" + var2);
    }

    /**
     * 后置通知 在方法返回的时候执行
     */
    @AfterReturning(value = "point()")
    public void afterReturn() {
        System.out.println("注解方式-afterReturn");
    }

    /**
     * 异常通知 在方法抛出异常后执行
     */
    @AfterThrowing(value = "point()")
    public void afterThrow() {
        System.out.println("注解方式-afterThrow");
    }

    /**
     * 最终通知 无论方法如何结束，最终通知都会执行
     */
    @After(value = "point()")
    public void after() {
        System.out.println("注解方式-after");
    }

    /**
     * 环绕通知 控制方法的执行
     * 通知的第一个参数必须是ProceedingJoinPoint类型
     * 调用ProceedingJoinPoint的proceed()方法会导致后台的连接点方法执行（调用被通知的方法）
     * 注意：如果没有调用proceed()方法，那么通知实际上会阻塞对被通知方法的调用
     */
    @Around("point()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            System.out.println("注解方式-proceed before");
            Object object = point.proceed();
            System.out.println("注解方式-proceed after:" + (ObjectUtils.isEmpty(object) ? "空返回值" : object.toString()) + "  args:"
                    + CollectionUtils.arrayToList(point.getArgs()).toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
