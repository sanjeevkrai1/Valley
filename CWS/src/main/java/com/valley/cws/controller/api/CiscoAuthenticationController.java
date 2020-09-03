package com.valley.cws.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valley.cws.bean.api.cisco.LoginCiscoReqBean;
import com.valley.cws.bean.api.cisco.LoginCiscoResBean;
import com.valley.cws.bean.api.cisco.RateCiscoReqBean;
import com.valley.cws.bean.api.cisco.RateCiscoResBean;
import com.valley.cws.bean.api.cisco.RedirectCiscoReqBean;
import com.valley.cws.bean.api.cisco.RedirectCiscoResBean;
import com.valley.cws.bean.api.cisco.RegistrationCiscoReqBean;
import com.valley.cws.bean.api.cisco.RegistrationCiscoResBean;
import com.valley.cws.bean.api.cisco.ValidateOtpCiscoReqBean;
import com.valley.cws.bean.api.cisco.ValidateOtpCiscoResBean;
import com.valley.cws.service.api.cisco.IAuthenticateCiscoService;

@Controller
public class CiscoAuthenticationController {

	@Autowired
	IAuthenticateCiscoService ciscoService;

	@RequestMapping(value = "redirect/cisco/")
	public @ResponseBody RedirectCiscoResBean redirectCisco(@RequestBody RedirectCiscoReqBean redirectCiscoReqBean) {

		return ciscoService.validateMac(redirectCiscoReqBean);
	}

	@RequestMapping(value = "registration/cisco/")
	public @ResponseBody RegistrationCiscoResBean registrationCisco(
			@RequestBody RegistrationCiscoReqBean registrationCiscoReqBean) {

		return ciscoService.registration(registrationCiscoReqBean);
	}

	@RequestMapping(value = "validateOtp/cisco/")
	public @ResponseBody ValidateOtpCiscoResBean validateOtpCisco(
			@RequestBody ValidateOtpCiscoReqBean validateOtpCiscoReqBean) {

		return ciscoService.validateOtp(validateOtpCiscoReqBean);
	}

	@RequestMapping(value = "login/cisco/")
	public @ResponseBody LoginCiscoResBean loginCisco(@RequestBody LoginCiscoReqBean loginCiscoReqBean) {

		return ciscoService.login(loginCiscoReqBean);
	}

	@RequestMapping(value = "rateAds/cisco/")
	public @ResponseBody RateCiscoResBean rateAdsCisco(@RequestBody RateCiscoReqBean rateCiscoReqBean) {

		return ciscoService.rateAds(rateCiscoReqBean);
	}
}
