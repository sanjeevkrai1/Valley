package com.valley.cws.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valley.cws.bean.PolicyResBean;
import com.valley.cws.bean.StaticAdBean;
import com.valley.cws.bean.api.aruba.AdBean;
import com.valley.cws.bean.api.aruba.LoginArubaReqBean;
import com.valley.cws.bean.api.aruba.LoginArubaResBean;
import com.valley.cws.bean.api.aruba.RedirectArubaReqBean;
import com.valley.cws.bean.api.aruba.RedirectArubaResBean;
import com.valley.cws.bean.api.aruba.RegistrationArubaReqBean;
import com.valley.cws.bean.api.aruba.RegistrationArubaResBean;
import com.valley.cws.bean.api.aruba.ValidateOtpArubaReqBean;
import com.valley.cws.bean.api.aruba.ValidateOtpArubaResBean;
import com.valley.cws.entity.WcaIpRanges;
import com.valley.cws.entity.WcaUserProfile;
import com.valley.cws.service.AuthenticateService;
import com.valley.cws.service.ShowAdService;
import com.valley.cws.service.api.aruba.IAuthenticateArubaService;
import com.valley.cws.utils.ApacheHttpClientPost;
import com.valley.cws.utils.CommonUtils;

@Controller
public class ArubaAuthenticateController {

	public static final Logger LOGGER = Logger.getLogger(ArubaAuthenticateController.class);

	@Autowired
	AuthenticateService service;

	@Autowired
	ShowAdService showAdService;

	@Autowired
	IAuthenticateArubaService arubaService;

	@RequestMapping(value = "/home/aruba", method = RequestMethod.GET)
	public String homeAruba(Model model, @RequestParam(value = "cmd", required = false) String cmd,
			@RequestParam(value = "mac", required = false) String macAddress,
			@RequestParam(value = "essid", required = false) String essid, @RequestParam(value = "ip", required = false) String ip,
			@RequestParam(value = "apname", required = false) String apname,
			@RequestParam(value = "vcname", required = false) String vcname,
			@RequestParam(value = "switchip", required = false) String switchip, @RequestParam(value = "url", required = false) String url) {

		LOGGER.info("Wifi Infra Hit with some Field :: [ cmd ]  :: " + cmd + " , [ mac ]  :: " + macAddress + " , [ essid ] :: " + essid
				+ " , [ ip ] :: " + ip + " , [ apname ] :: " + apname + " , [ vcname ] :: " + vcname + " , [ switchip ] :: " + switchip
				+ " , [ url ] :: " + url);

		List<StaticAdBean> staticAds;
		List<AdBean> defaultAds;
		List<WcaIpRanges> ipranges;
		String location = null;
		long ipStart;
		long ipEnd;
		long ipToTest;

		RedirectArubaReqBean reqBean = new RedirectArubaReqBean(macAddress);
		RedirectArubaResBean bean = this.arubaService.validateMac(reqBean);
		WcaUserProfile userProfile = this.service.findOne(macAddress);
		ipranges = this.service.findAllIp();

		for (WcaIpRanges wcaIpRanges : ipranges) {
			try {
				ipStart = CommonUtils.ipToLong(InetAddress.getByName(wcaIpRanges.getIpStart()));
				ipEnd = CommonUtils.ipToLong(InetAddress.getByName(wcaIpRanges.getIpEnd()));
				ipToTest = CommonUtils.ipToLong(InetAddress.getByName(ip));
				if (ipToTest >= ipStart && ipToTest <= ipEnd) {
					location = wcaIpRanges.getLocation();
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		String appUrl = CommonUtils.checkString(CommonUtils.propValue("app_url"));
		String GrantAccessURLForOpenAPP = "valley://com.valley.captive_portal/";
		model.addAttribute("GrantAccessURL", GrantAccessURLForOpenAPP);
		model.addAttribute("appUrl", appUrl);

		defaultAds = bean.getAdUrls();
		String actionUrl = "http://" + switchip + "/cgi-	bin/login";

		if (bean.getAction() != null && bean.getAction().equalsIgnoreCase("Login")) {

			if (!bean.getIsAd()) {
				PolicyResBean resBean = ApacheHttpClientPost.getAd(macAddress);
				System.out.println("Both Date are same");
				String adIds = "";
				if(resBean != null){
				staticAds = resBean.getStaticAds();
				if (staticAds != null && !staticAds.isEmpty()) {
					for (StaticAdBean adBean : staticAds) {
						adIds += CommonUtils.checkString(adBean.getStaticAdId()) + ",";
					}
				}
				model.addAttribute("staticAdsList", staticAds);
				}
				model.addAttribute("adIds", adIds);
				model.addAttribute("adBean", resBean);
				model.addAttribute("userId", userProfile.getId());
				model.addAttribute("baseGrantUrl", CommonUtils.checkString(userProfile.getBaseGrantUrl()));
				model.addAttribute("macAddress", macAddress);
				model.addAttribute("ssid", essid);
				model.addAttribute("clientIp", ip);
				model.addAttribute("apname", apname);
				model.addAttribute("vcName", vcname);
				model.addAttribute("switchIp", switchip);
				model.addAttribute("url", url);
				model.addAttribute("cmd", "authenticate");
				model.addAttribute("actionUrl", actionUrl);
				return "adAndPgPageAruba";
			}

			model.addAttribute("macAddress", macAddress);
			model.addAttribute("ssid", essid);
			model.addAttribute("clientIp", ip);
			model.addAttribute("apname", apname);
			model.addAttribute("vcName", vcname);
			model.addAttribute("switchIp", switchip);
			model.addAttribute("url", url);
			model.addAttribute("cmd", "authenticate");
			model.addAttribute("actionUrl", actionUrl);
			model.addAttribute("staticScreenType", bean.getScreenType());
			model.addAttribute("defaultAds", defaultAds);
			model.addAttribute("showAd", bean.getIsAd());
			if (location != null && location.equalsIgnoreCase("Ambience Mall")) {
				return "loginAmbienceAruba";
			} else if (location != null && location.contains("IGI Airport")) {
				return "loginIGIAruba";
			} else
				return "loginAruba";
		} else {
			model.addAttribute("macAddress", macAddress);
			model.addAttribute("ssid", essid);
			model.addAttribute("clientIp", ip);
			model.addAttribute("apname", apname);
			model.addAttribute("vcName", vcname);
			model.addAttribute("switchIp", switchip);
			model.addAttribute("baseGrantUrl", actionUrl);
			model.addAttribute("continueUrl", url);
			model.addAttribute("url", url);
			model.addAttribute("cmd", "authenticate");
			model.addAttribute("actionUrl", actionUrl);
			model.addAttribute("staticScreenType", bean.getScreenType());
			model.addAttribute("defaultAds", defaultAds);
			model.addAttribute("showAd", bean.getIsAd());
			if (location != null && location.equalsIgnoreCase("Ambience Mall")) {
				return "registerAmbienceAruba";
			} else if (location != null && location.contains("IGI Airport")) {
				return "registerIGIAruba";
			} else
				return "registerAruba";
		}

	}

	@RequestMapping(value = "/home/loginAruba", method = RequestMethod.POST)
	public String loginAruba(Model model, LoginArubaReqBean loginArubaReqBean) {

		LoginArubaResBean loginArubaResBean = arubaService.login(loginArubaReqBean);

		model.addAttribute("url", loginArubaResBean.getUrl());
		model.addAttribute("actionUrl", loginArubaResBean.getActionUrl());
		model.addAttribute("cmd", "authenticate");
		model.addAttribute("Login", "Log In");
		model.addAttribute("mobileNumber", loginArubaResBean.getMobileNumber());
		model.addAttribute("macAddress", loginArubaResBean.getMacAddress());

		return "verifyOtpAruba";
	}

	@RequestMapping(value = "/home/registerAruba", method = RequestMethod.POST)
	public String registerAruba(Model model, RegistrationArubaReqBean registrationArubaReqBean) {

		String dateOfBirth = registrationArubaReqBean.getDob();
		String dob = CommonUtils.convertDob(dateOfBirth);
		registrationArubaReqBean.setDob(dob);

		RegistrationArubaResBean registrationArubaResBean = arubaService.registration(registrationArubaReqBean);

		model.addAttribute("url", registrationArubaResBean.getUrl());
		model.addAttribute("actionUrl", registrationArubaResBean.getActionUrl());
		model.addAttribute("cmd", "authenticate");
		model.addAttribute("Login", "Log In");
		model.addAttribute("mobileNumber", registrationArubaResBean.getMobileNumber());
		model.addAttribute("macAddress", registrationArubaResBean.getMacAddress());

		return "verifyOtpAruba";
	}

	@RequestMapping(value = "/home/validateAruba", method = RequestMethod.POST)
	public @ResponseBody String validate(Model model, @RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "macAddress", required = false) String macAddress,
			@RequestParam(value = "password", required = false) String password) {
		ValidateOtpArubaReqBean validateOtpArubaReqBean = new ValidateOtpArubaReqBean();
		validateOtpArubaReqBean.setMacAddress(macAddress);
		validateOtpArubaReqBean.setMobileNumber(user);
		validateOtpArubaReqBean.setOtp(password);

		ValidateOtpArubaResBean validateOtpArubaResBean = arubaService.validateOtp(validateOtpArubaReqBean);
		String response = validateOtpArubaResBean.getStatus();

		return response;
	}

	public static void main(String[] args) {
		String str = "162|like,163|dislike,";
		String[] strArr = str.split(",");
		System.out.println(strArr.length);
		String[] id1 = strArr[0].split("\\|");
		System.out.println(id1.length + " , " + id1[0] + " , " + id1[1]);
		String[] id2 = strArr[1].split("\\|");
		System.out.println(id2.length + " , " + id2[0] + " , " + id2[1]);

	}
}
