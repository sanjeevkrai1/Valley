package com.valley.cws.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valley.cws.bean.api.tplink.LoginTplinkReqBean;
import com.valley.cws.bean.api.tplink.LoginTplinkResBean;
import com.valley.cws.bean.api.tplink.RateTplinkReqBean;
import com.valley.cws.bean.api.tplink.RateTplinkResBean;
import com.valley.cws.bean.api.tplink.RedirectTplinkReqBean;
import com.valley.cws.bean.api.tplink.RedirectTplinkResBean;
import com.valley.cws.bean.api.tplink.RegistrationTplinkReqBean;
import com.valley.cws.bean.api.tplink.RegistrationTplinkResBean;
import com.valley.cws.bean.api.tplink.ValidateOtpTplinkReqBean;
import com.valley.cws.bean.api.tplink.ValidateOtpTplinkResBean;
import com.valley.cws.service.api.tplink.IAuthenticateTplinkService;

@Controller
public class TplinkAuthenticationController {

	@Autowired
	IAuthenticateTplinkService tplinkService;

	@RequestMapping(value = "redirect/tplink/")
	public @ResponseBody RedirectTplinkResBean redirectTplink(@RequestBody RedirectTplinkReqBean redirectTplinkReqBean) {

		return tplinkService.validateMac(redirectTplinkReqBean);
	}

	@RequestMapping(value = "registration/tplink/")
	public @ResponseBody RegistrationTplinkResBean registrationTplink(
			@RequestBody RegistrationTplinkReqBean registrationTplinkReqBean) {

		return tplinkService.registration(registrationTplinkReqBean);
	}

	@RequestMapping(value = "validateOtp/tplink/")
	public @ResponseBody ValidateOtpTplinkResBean validateOtpTplink(
			@RequestBody ValidateOtpTplinkReqBean validateOtpTplinkReqBean) {

		return tplinkService.validateOtp(validateOtpTplinkReqBean);
	}

	@RequestMapping(value = "login/tplink/")
	public @ResponseBody LoginTplinkResBean loginTplink(@RequestBody LoginTplinkReqBean loginTplinkReqBean) {

		return tplinkService.login(loginTplinkReqBean);
	}

	@RequestMapping(value = "rateAds/tplink/")
	public @ResponseBody RateTplinkResBean rateAdsTplink(@RequestBody RateTplinkReqBean rateTplinkReqBean) {

		return tplinkService.rateAds(rateTplinkReqBean);
	}
}
