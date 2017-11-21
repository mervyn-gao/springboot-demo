package com.mervyn.springboot.service;

import com.mervyn.springboot.model.SystemLog;

/**
 * Created by mengran.gao on 2017/11/14.
 */
public interface SystemLogService {

    void add(SystemLog systemLog);

    void edit(SystemLog systemLog);
}
