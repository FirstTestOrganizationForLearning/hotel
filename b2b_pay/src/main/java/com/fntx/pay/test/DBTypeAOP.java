package com.fntx.pay.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-10)//保证该AOP在@Transactional之前执行
@Aspect
public class DBTypeAOP {
 
    @Pointcut("@annotation(TargetDataSource)")
    public void dbType() {
    }
    @Before("dbType()")
    public void before(JoinPoint joinPoint) throws Throwable {
        //获取当前的指定的数据源;
        System.out.println("---------------before---------------");
        TargetDataSource targetDataSource = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(TargetDataSource.class);
        String dsId = targetDataSource.value();
        //如果不在我们注入的所有的数据源范围之内，那么输出警告信息，系统自动使用默认的数据源。
        if (!DbContextHolder.containsDataSource(dsId)) {
            System.err.println("数据源[{}]不存在，使用默认数据源 > {}"+targetDataSource.value()+joinPoint.getSignature());
        } else {
            System.out.println("Use DataSource : {} > {}"+targetDataSource.value()+joinPoint.getSignature());
            //找到的话，那么设置到动态数据源上下文中。
            DbContextHolder.setDbType(targetDataSource.value());
        }
    }
 
    @After("dbType()")
    public void after(JoinPoint joinPoint) {
        System.out.println("--------------after----------------");
        TargetDataSource targetDataSource = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(TargetDataSource.class);
        System.out.println("Revert DataSource : {} > {}"+targetDataSource.value()+joinPoint.getSignature());
        //方法执行完毕之后，销毁当前数据源信息，进行垃圾回收。
        DbContextHolder.clearDbType();
    }


}
