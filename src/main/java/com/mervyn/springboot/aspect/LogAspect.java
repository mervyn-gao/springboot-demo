package com.mervyn.springboot.aspect;

import com.mervyn.springboot.annotation.Log;
import com.mervyn.springboot.model.SystemLog;
import com.mervyn.springboot.model.User;
import com.mervyn.springboot.service.SystemLogService;
import com.mervyn.springboot.util.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by mengran.gao on 2017/11/14.
 */
//@Aspect
//@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final ThreadLocal<Date> beginTimeThreadLocal =
            new NamedThreadLocal<>("ThreadLocal beginTime");
    private static final ThreadLocal<SystemLog> logThreadLocal =
            new NamedThreadLocal<>("ThreadLocal log");

    private static final ThreadLocal<User> currentUser = new NamedThreadLocal<>("ThreadLocal user");

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private SystemLogService systemLogService;


    @Pointcut("@annotation(com.mervyn.springboot.annotation.Log)")
    public void logAspect() {
    }

    @Before("logAspect()")
    public void before() {
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）
        if (logger.isDebugEnabled()) {//这里日志级别为debug
            logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .format(beginTime), request.getRequestURI());
        }

        //读取session中的用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("ims_user");
        currentUser.set(user);
    }

    @After("logAspect()")
    public void doAfter(JoinPoint joinPoint) {
        User user = currentUser.get();
        if (user != null) {
            String title = "";
            String type = "info";                       //日志类型(info:入库,error:错误)
            String remoteAddr = request.getRemoteAddr();//请求的IP
            String requestUri = request.getRequestURI();//请求的Uri
            String method = request.getMethod();        //请求的方法类型(post/get)
            Map<String, String[]> params = request.getParameterMap(); //请求提交的参数

            try {
                title = getControllerMethodDescription2(joinPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 打印JVM信息。
            long beginTime = beginTimeThreadLocal.get().getTime();//得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis();  //2、结束时间
            if (logger.isDebugEnabled()) {
                logger.debug("计时结束：{}  URI: {}  耗时： {}   最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime),
                        request.getRequestURI(),
                        endTime - beginTime,
                        Runtime.getRuntime().maxMemory() / 1024 / 1024,
                        Runtime.getRuntime().totalMemory() / 1024 / 1024,
                        Runtime.getRuntime().freeMemory() / 1024 / 1024,
                        (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
            }

            SystemLog log = new SystemLog();
            log.setTitle(title);
            log.setType(type);
            log.setRemoteAddr(remoteAddr);
            log.setRequestUri(requestUri);
            log.setMethod(method);
            log.setParams(JsonUtils.toJsonString(params));
            log.setUserId(String.valueOf(user.getId()));
            Date operateTime = beginTimeThreadLocal.get();
            log.setOperateTime(operateTime);
            log.setTimeout(endTime - beginTime);

            //1.直接执行保存操作
            //this.logService.createSystemLog(log);

            //2.优化:异步保存日志
            //new SaveLogThread(log, logService).start();

            //3.再优化:通过线程池来执行日志保存
            threadPoolTaskExecutor.execute(new SaveLogThread(log, systemLogService));
            logThreadLocal.set(log);
        }
    }

    /**
     * 异常通知 记录操作报错日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "logAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        SystemLog log = logThreadLocal.get();
        log.setType("error");
        log.setException(e.toString());
//        new UpdateLogThread(log, logService).start();
    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint
     * @return discription
     */
    public static String getServiceMthodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        return log.desc();
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return discription
     */
    public static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        return log.desc();
    }

    /**
     * 保存日志线程
     */
    private static class SaveLogThread implements Runnable {
        private SystemLog systemLog;
        private SystemLogService systemLogService;

        public SaveLogThread(SystemLog systemLog, SystemLogService systemLogService) {
            this.systemLog = systemLog;
            this.systemLogService = systemLogService;
        }

        @Override
        public void run() {
            systemLogService.add(systemLog);
        }
    }

    /**
     * 日志更新线程
     */
    private static class UpdateLogThread extends Thread {
        private SystemLog systemLog;
        private SystemLogService systemLogService;

        public UpdateLogThread(SystemLog systemLog, SystemLogService systemLogService) {
            super(UpdateLogThread.class.getSimpleName());
            this.systemLog = systemLog;
            this.systemLogService = systemLogService;
        }

        @Override
        public void run() {
            this.systemLogService.edit(systemLog);
        }
    }
}
