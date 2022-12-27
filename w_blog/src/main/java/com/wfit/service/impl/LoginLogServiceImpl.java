package com.wfit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.domain.Result;
import com.wfit.domain.dto.UserAgentDTO;
import com.wfit.domain.entity.LoginLog;
import com.wfit.mapper.LoginLogMapper;
import com.wfit.service.LoginLogService;
import com.wfit.utils.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private UserAgentUtils userAgentUtils;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    @Transactional
    public void saveLoginLog(LoginLog loginLog) {

        UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(loginLog.getUserAgent());
        loginLog.setOs(userAgentDTO.getOs());
        loginLog.setBrowser(userAgentDTO.getBrowser());
        loginLogMapper.saveLoginLog(loginLog);
    }

    @Override
    public Result loginLogs(String[] date, Integer pageNum, Integer pageSize) {


        String beginTime = null;
        String endTime = null;

        if(date.length == 2){
             beginTime = date[0];
             endTime = date[1];
        }

        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);


        List<LoginLog> loginLogs = loginLogMapper.selectLoginLogsByDate(beginTime,endTime);

        PageInfo<LoginLog> pageInfo = new PageInfo<LoginLog>(loginLogs);

        return Result.ok("请求成功",pageInfo);
    }

    @Override
    public Result deleteLoginLog(Long id) {


        loginLogMapper.deleteLoginLog(id);

        return Result.ok("删除成功");
    }


}
