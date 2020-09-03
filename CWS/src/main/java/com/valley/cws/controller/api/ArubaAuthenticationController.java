package com.valley.cws.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valley.cws.bean.api.aruba.LoginArubaReqBean;
import com.valley.cws.bean.api.aruba.LoginArubaResBean;
import com.valley.cws.bean.api.aruba.RateArubaReqBean;
import com.valley.cws.bean.api.aruba.RateArubaResBean;
import com.valley.cws.bean.api.aruba.RedirectArubaReqBean;
import com.valley.cws.bean.api.aruba.RedirectArubaResBean;
import com.valley.cws.bean.api.aruba.RegistrationArubaReqBean;
import com.valley.cws.bean.api.aruba.RegistrationArubaResBean;
import com.valley.cws.bean.api.aruba.ValidateOtpArubaReqBean;
import com.valley.cws.bean.api.aruba.ValidateOtpArubaResBean;
import com.valley.cws.service.api.aruba.IAuthenticateArubaService;

@Controller
public class ArubaAuthenticationController {

	@Autowired
	IAuthenticateArubaService arubaService;

	@RequestMapping(value = "redirect/aruba/")
	public @ResponseBody RedirectArubaResBean redirectAruba(@RequestBody RedirectArubaReqBean redirectArubaReqBean) {

		return arubaService.validateMac(redirectArubaReqBean);
	}

	@RequestMapping(value = "registration/aruba/")
	public @ResponseBody RegistrationArubaResBean registrationAruba(
			@RequestBody RegistrationArubaReqBean registrationArubaReqBean) {

		return arubaService.registration(registrationArubaReqBean);
	}

	@RequestMapping(value = "validateOtp/aruba/")
	public @ResponseBody ValidateOtpArubaResBean validateOtpAruba(
			@RequestBody ValidateOtpArubaReqBean validateOtpArubaReqBean) {

		return arubaService.validateOtp(validateOtpArubaReqBean);
	}

	@RequestMapping(value = "login/aruba/")
	public @ResponseBody LoginArubaResBean loginAruba(@RequestBody LoginArubaReqBean loginArubaReqBean) {

		return arubaService.login(loginArubaReqBean);
	}

	@RequestMapping(value = "rateAds/aruba/")
	public @ResponseBody RateArubaResBean rateAdsAruba(@RequestBody RateArubaReqBean rateArubaReqBean) {

		return arubaService.rateAds(rateArubaReqBean);
	}
}
