package com.valley.cws.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valley.cws.bean.api.bng.GrantBngReqBean;
import com.valley.cws.bean.api.bng.GrantBngResBean;
import com.valley.cws.bean.api.bng.LoginBngReqBean;
import com.valley.cws.bean.api.bng.LoginBngResBean;
import com.valley.cws.bean.api.bng.RateBngReqBean;
import com.valley.cws.bean.api.bng.RateBngResBean;
import com.valley.cws.bean.api.bng.RedirectBngReqBean;
import com.valley.cws.bean.api.bng.RedirectBngResBean;
import com.valley.cws.bean.api.bng.RegistrationBngReqBean;
import com.valley.cws.bean.api.bng.RegistrationBngResBean;
import com.valley.cws.bean.api.bng.ValidateOtpBngReqBean;
import com.valley.cws.bean.api.bng.ValidateOtpBngResBean;
import com.valley.cws.service.api.bng.IAuthenticateBngService;

@Controller
public class BngAuthenticationController {

	@Autowired
	IAuthenticateBngService bngService;

	@RequestMapping(value = "redirect/bng/")
	public @ResponseBody RedirectBngResBean redirectBng(@RequestBody RedirectBngReqBean redirectBngReqBean) {

		return bngService.validateMac(redirectBngReqBean);
	}

	@RequestMapping(value = "registration/bng/")
	public @ResponseBody RegistrationBngResBean registrationBng(
			@RequestBody RegistrationBngReqBean registrationBngReqBean) {

		return bngService.registration(registrationBngReqBean);
	}

	@RequestMapping(value = "validateOtp/bng/")
	public @ResponseBody ValidateOtpBngResBean validateOtpBng(@RequestBody ValidateOtpBngReqBean validateOtpBngReqBean) {

		return bngService.validateOtp(validateOtpBngReqBean);
	}

	@RequestMapping(value = "login/bng/")
	public @ResponseBody LoginBngResBean loginBng(@RequestBody LoginBngReqBean loginBngReqBean) {

		return bngService.login(loginBngReqBean);
	}

	@RequestMapping(value = "rateAds/bng/")
	public @ResponseBody RateBngResBean rateAdsBng(@RequestBody RateBngReqBean rateBngReqBean) {

		return bngService.rateAds(rateBngReqBean);
	}

	@RequestMapping(value = "grant/bng/")
	public @ResponseBody GrantBngResBean grantBng(@RequestBody GrantBngReqBean grantBngReqBean) {

		return bngService.grant(grantBngReqBean);
	}
}
