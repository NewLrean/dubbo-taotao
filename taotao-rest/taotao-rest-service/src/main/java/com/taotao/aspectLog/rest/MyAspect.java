package com.taotao.aspectLog.rest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;


@Aspect
@Transactional
public class MyAspect {



    @Pointcut("execution(* com.taotao.rest.*.*(..))")
    private void myPointCut() {

    }

    //前置通知
    @Before("myPointCut()")
    public void myBefore(JoinPoint joinPoint) {
        System.out.println("前置通知:方法名称："+joinPoint.getSignature().getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        System.out.println(time);
    }

    //后置通知
    @AfterReturning("myPointCut()")
    public void myAfterReturning(JoinPoint joinPoint) {
        System.out.println("后置通知：已执行完毕"+joinPoint.getSignature().getName());

    }

    //环绕通知
    @Around("myPointCut()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕开始");
        //执行当前目标方法

        Object object = proceedingJoinPoint.proceed();
        //结束
        System.out.println("环绕结束");
        return object;

    }

    //异常通知
    @AfterThrowing(value = "myPointCut()", throwing = "e")
    public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.out.println("异常通知：异常方法"+joinPoint.getSignature().getName());
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " occurs excetion:" + e);
    }

    //最终通知
    @After("myPointCut()")
    public void myAfter() {
        System.out.println("最终通知");
    }
}
