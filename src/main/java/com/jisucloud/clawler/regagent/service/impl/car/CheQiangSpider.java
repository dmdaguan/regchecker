package com.jisucloud.clawler.regagent.service.impl.car;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "chexiang.com", 
		message = "车享网，现用名车享，是车享旗下基于PC端的业务承接平台，于2014年3月28日正式上线，依托上海汽车集团旗下各大品牌及数千家经销商，以用户为中心，通过线上线下无缝对接的电子商务模式，为用户提供一站式解决方案。", 
		platform = "chexiang", 
		platformName = "车享网", 
		tags = { "租车", "o2o" }, 
		testTelephones = { "18210538577", "18212345678" })
public class CheQiangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.chexiang.com/account/userIsExist.htm";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userName", account)
	                .add("userNameType", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.chexiang.com")
					.addHeader("Referer", "https://account.chexiang.com/account/m_register.htm?backUrl=http%3A%2F%2Fwww.chexiang.com%2F")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("isExist").equals("1")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
