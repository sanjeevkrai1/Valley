package com.valley.cws.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.valley.cws.utils.CommonUtils;

@Controller
public class BngAuthenticateController {
	
	public static final Logger LOGGER = Logger.getLogger(BngAuthenticateController.class);
	
	@RequestMapping(value = "/home/bng", method = RequestMethod.GET)
	public String homeAruba(Model model) {

		LOGGER.info("Bng request come from browser");
		String appUrl = CommonUtils.checkString(CommonUtils.propValue("app_url"));
		String GrantAccessURLForOpenAPP = "valley://com.valley.captive_portal/";
		model.addAttribute("GrantAccessURL", GrantAccessURLForOpenAPP);
		model.addAttribute("appUrl", appUrl);
		
		return "loginBng";
	}
}
