package com.jisucloud.clawler.regagent.service.impl._3c;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class KuaiKeJiSpider implements PapaSpider,AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "快科技(驱动之家旗下媒体)为您提供第一手的科技新闻资讯、产品评测、驱动下载等服务。老牌的驱动下载频道通过方便快捷的驱动分类、搜索服务,助您快速找到所需的驱动。";
	}

	@Override
	public String platform() {
		return "mydrivers";
	}

	@Override
	public String home() {
		return "mydrivers.com";
	}

	@Override
	public String platformName() {
		return "快科技";
	}

	@Override
	public String[] tags() {
		return new String[] {"系统驱动", "科技" ,"软件"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15510257873", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("http://passport.mydrivers.com/login.aspx");
			Thread.sleep(2000);
			chromeDriver.findElementById("txtusername").sendKeys(account);
			chromeDriver.findElementById("txtpassword").sendKeys("xas12qw1dsadsa");
			chromeDriver.findElementById("login").click();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTel;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

	@Override
	public HookTracker getHookTracker() {
		return HookTracker.builder().addUrl("v2/m/action/userlogin.aspx").isPOST().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		checkTel = contents.getTextContents().contains("密码");
	}

}
