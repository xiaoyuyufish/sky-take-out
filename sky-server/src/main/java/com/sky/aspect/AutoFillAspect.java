package com.sky.aspect;

import com.sky.annotation.Autofill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && " +
            "@annotation(com.sky.annotation.Autofill)")
    public void autoFillPointCut() {}

    @Before("autoFillPointCut()")
    public void autofill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始进行公共字段自动填充");
        //获取连接点处的方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //通过方法签名获取
        Autofill autofill = signature.getMethod().getAnnotation(Autofill.class);
        //获取方法上的操作类型
        OperationType value = autofill.value();
        //获取方法上的参数->实体对象
        Object[] args = joinPoint.getArgs();
        Object entity = args[0];

        if(value == OperationType.INSERT) {
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            setCreateTime.invoke(entity, LocalDateTime.now());
            setUpdateTime.invoke(entity, LocalDateTime.now());
            setCreateUser.invoke(entity, BaseContext.getCurrentId());
            setUpdateUser.invoke(entity, BaseContext.getCurrentId());

        }
        else if(value == OperationType.UPDATE) {
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            setUpdateTime.invoke(entity, LocalDateTime.now());
            setUpdateUser.invoke(entity, BaseContext.getCurrentId());
        }
    }
}
