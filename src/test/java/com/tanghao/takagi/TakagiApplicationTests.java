package com.tanghao.takagi;

import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TakagiApplicationTests {

	@Test
	void contextLoads() {
		String password = "123456";
		System.out.println(password + " (md5Hex)-> " + SecureUtil.md5(password));
	}

}
