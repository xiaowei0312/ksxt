package com.sxsram.ssm.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class IoTest {
	public static void main(String[] args) {
		try {
			FileUtils.copyFile(new File("D:\\filecommit\\validfilelist\\" +"5b4472dc-3e6d-4236-a5fc-051ae70f72bf.zip" ),
					new File("D:\\filecommit\\tmp\\" + "111111_张三_毕设预答辩_123456.zip"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
