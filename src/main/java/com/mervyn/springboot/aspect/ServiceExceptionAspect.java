package com.mervyn.springboot.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by mengran.gao on 2017/7/7.
 */
@Component
@Aspect
//@Order(1)
public class ServiceExceptionAspect {

//    @Resource
//    private LocaleMessageSourceService localeMessageSourceService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.mervyn.springboot.service.impl..*.*(..))")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LOGGER.info("===============service统一异常处理开始=================");
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
//        result.setMessage(localeMessageSourceService.getMessage(result.getMessage()));
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(result);
        LOGGER.info("===============service统一异常处理返回结果：" + mapper.writeValueAsString(result) + "=================");
        LOGGER.info("===============service统一异常处理结束=================");
        return result;
    }

}
