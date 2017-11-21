package com.mervyn.springboot.handler;

/**
 * 请求到达controller前出现的异常，如请求url不正确（404），这种异常其实可以不用处理
 * 这里不处理异常，因为BaseErrorController里已经帮我们写好了这类异常的处理返回
 */
/*@RestController
public class HttpExceptionHandler implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    public Result<Object> error(HttpServletResponse resp, HttpServletRequest req) {
        // 错误处理逻辑
        return Result.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常");
    }
}*/
