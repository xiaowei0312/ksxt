package com.sxsram.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxsram.ssm.entity.Log;
import com.sxsram.ssm.mapper.LogMapper;

@Service("LogService")
public class LogService {
	@Autowired
	private LogMapper logMapper;

	public void addLog(Log log) {
		System.out.println(log);
		logMapper.addLog(log);
	}
}
