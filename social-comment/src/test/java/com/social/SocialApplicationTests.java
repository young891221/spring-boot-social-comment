package com.social;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SocialApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialApplicationTests {

	@Test
	public void contextLoads() throws UnsupportedEncodingException {
		String keyTest = "xvz1evFS4wEEPTGEFPHBog:L8qq9PZyRg6ieKGEKhZolGC0vJWLw8iEJ88DRdyOg";
		String key = "qynoCusuv0j4RsAEc4QEKHJBb:Z5Qc2wWqLd78ZSIGZt49gElElOAcYMY8dIYnWhz96MKnicueDO";

		base64Encode(keyTest);
		base64Encode(key);
	}

	private void base64Encode(String keyTest) throws UnsupportedEncodingException {
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode(keyTest.getBytes("UTF-8"));
		System.out.println(new String(encodedBytes));
	}

}
