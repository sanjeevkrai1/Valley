package com.valley.cws.controller;

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
import com.valley.cws.bean.api.tplink.AdBean;
import com.valley.cws.bean.api.tplink.LoginTplinkReqBean;
import com.valley.cws.bean.api.tplink.LoginTplinkResBean;
import com.valley.cws.bean.api.tplink.RedirectTplinkReqBean;
import com.valley.cws.bean.api.tplink.RedirectTplinkResBean;
import com.valley.cws.bean.api.tplink.RegistrationTplinkReqBean;
import com.valley.cws.bean.api.tplink.RegistrationTplinkResBean;
import com.valley.cws.bean.api.tplink.ValidateOtpTplinkReqBean;
import com.valley.cws.bean.api.tplink.ValidateOtpTplinkResBean;
import com.valley.cws.entity.WcaUserProfile;
import com.valley.cws.service.AuthenticateService;
import com.valley.cws.service.api.tplink.IAuthenticateTplinkService;
import com.valley.cws.utils.ApacheHttpClientPost;
import com.valley.cws.utils.CommonUtils;

@Controller
public class TpLinkAuthenticateController {
	
	public static final Logger LOGGER = Logger.getLogger(TpLinkAuthenticateController.class);

	@Autowired
	AuthenticateService service;
	
	@Autowired
	IAuthenticateTplinkService tpLinkService;
	
	@RequestMapping(value = "/home/tplink", method = RequestMethod.GET)
	public String homeTpLink(Model model,
			@RequestParam(value = "clientMac", required = false) String macAddress,
			@RequestParam(value = "target", required = false) String switchip) {

		LOGGER.info("Wifi Infra Hit with some Field homeTpLink:: [ clientmac ]  :: " + macAddress + " , [ targetip ] :: " + switchip);
		
		//switchip = "192.168.9.7";

		List<StaticAdBean> staticAds;
		List<AdBean> defaultAds;

		RedirectTplinkReqBean reqBean = new RedirectTplinkReqBean(macAddress);
		RedirectTplinkResBean bean = this.tpLinkService.validateMac(reqBean);
		WcaUserProfile userProfile = this.service.findOne(macAddress);

		String appUrl = CommonUtils.checkString(CommonUtils.propValue("app_url"));
		String GrantAccessURLForOpenAPP = "valley://com.valley.captive_portal/";
		model.addAttribute("GrantAccessURL", GrantAccessURLForOpenAPP);
		model.addAttribute("appUrl", appUrl);

		defaultAds = bean.getAdUrls();

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
				model.addAttribute("macAddress", macAddress);
				model.addAttribute("switchIp", switchip);
				model.addAttribute("access","Tplink");
				return "adAndPgPageTpLink";
			}

			model.addAttribute("macAddress", macAddress);
			model.addAttribute("switchIp", switchip);
			model.addAttribute("defaultAds", defaultAds);
			model.addAttribute("showAd", bean.getIsAd());
			model.addAttribute("access","Tplink");
			return "loginTpLink";
		} else {
			model.addAttribute("macAddress", macAddress);
			model.addAttribute("switchIp", switchip);
			model.addAttribute("defaultAds", defaultAds);
			model.addAttribute("showAd", bean.getIsAd());
			model.addAttribute("access","Tplink");
				return "registerTpLink";
		}

	}
	
	@RequestMapping(value = "/home/loginTpLink", method = RequestMethod.POST)
	public String loginTpLink(Model model, LoginTplinkReqBean loginTplinkReqBean) {

		LoginTplinkResBean loginTplinkResBean = tpLinkService.login(loginTplinkReqBean);

		model.addAttribute("actionUrl", loginTplinkResBean.getActionUrl());
		model.addAttribute("mobileNumber", loginTplinkResBean.getMobileNumber());
		model.addAttribute("macAddress", loginTplinkResBean.getMacAddress());

		return "verifyOtpTpLink";
	}

	@RequestMapping(value = "/home/registerTpLink", method = RequestMethod.POST)
	public String registerTpLink(Model model, RegistrationTplinkReqBean registrationTplinkReqBean) {

		String dateOfBirth = registrationTplinkReqBean.getDob();
		String dob = CommonUtils.convertDob(dateOfBirth);
		registrationTplinkReqBean.setDob(dob);

		RegistrationTplinkResBean registrationTplinkResBean = tpLinkService.registration(registrationTplinkReqBean);

		model.addAttribute("actionUrl", registrationTplinkResBean.getActionUrl());
		model.addAttribute("mobileNumber", registrationTplinkReqBean.getMobileNumber());
		model.addAttribute("macAddress", registrationTplinkResBean.getMacAddress());

		return "verifyOtpTpLink";
	}

	@RequestMapping(value = "/home/validateTpLink", method = RequestMethod.POST)
	public @ResponseBody String validateTpLink(Model model, @RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "macAddress", required = false) String macAddress,
			@RequestParam(value = "password", required = false) String password) {
		ValidateOtpTplinkReqBean validateOtpTplinkReqBean = new ValidateOtpTplinkReqBean();
		validateOtpTplinkReqBean.setMacAddress(macAddress);
		validateOtpTplinkReqBean.setMobileNumber(user);
		validateOtpTplinkReqBean.setOtp(password);

		ValidateOtpTplinkResBean ValidateOtpTplinkResBean = tpLinkService.validateOtp(validateOtpTplinkReqBean);
		String response = ValidateOtpTplinkResBean.getStatus();

		return response;
	}

}
