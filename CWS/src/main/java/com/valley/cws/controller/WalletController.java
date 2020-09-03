package com.valley.cws.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.valley.cws.bean.api.aruba.LoginArubaReqBean;
import com.valley.cws.entity.WcaUserProfile;
import com.valley.cws.entity.WcaWalletPlan;
import com.valley.cws.service.AuthenticateService;
import com.valley.cws.service.ShowAdService;
import com.valley.cws.service.WalletService;
import com.valley.cws.utils.CommonUtils;

@Controller
public class WalletController {

	public static final Logger LOGGER = Logger.getLogger(WalletController.class);

	@Autowired
	AuthenticateService service;

	@Autowired
	ShowAdService showAdService;

	@Autowired
	WalletService walletService;

	@RequestMapping(value = "/home/purchase")
	public String purchase(Model model, @RequestParam(value = "userId", required = false) String userId, LoginArubaReqBean loginArubaReqBean) {

		System.out.println("user Id profile ::" + userId);

		String order_Id = "ORDS";
		String customer_Id = "CUST";
		String industry_Type_Id = "Telecom";
		String channel = "WEB";

		WcaUserProfile userProfile = this.service.findById(CommonUtils.checkInteger(userId));

		Integer walletPoint = userProfile.getWalletPoint();
		if (walletPoint != null) {
			System.out.println(walletPoint);
			model.addAttribute("walletPoint", walletPoint);
		}

		List<WcaWalletPlan> walletPlan = this.walletService.findAll();

		List<WcaWalletPlan> rateList = new ArrayList<>();
		List<WcaWalletPlan> pointList = new ArrayList<>();

		if (walletPlan != null) {
			for (WcaWalletPlan w : walletPlan) {
				if (w.getPlanPoint() != null && w.getPlanPoint() != 0 && w.getPlanRate() != null && w.getPlanRate() != 0) {
					rateList.add(w);
					pointList.add(w);
				} else if (w.getPlanPoint() == null || w.getPlanPoint() == 0) {
					rateList.add(w);
				} else if (w.getPlanRate() == null || w.getPlanRate() == 0) {
					pointList.add(w);
				}

			}
		}

		LOGGER.info("Size of wallet :: " + pointList.size() + " Size of Payment" + rateList.size());

		Random rand = new Random();
		Integer randomPIN = (rand.nextInt(900000) + 100000);
		order_Id = order_Id + "_" + randomPIN;

		customer_Id = customer_Id + "_" + userProfile.getId();

		model.addAttribute("walletList", pointList);
		model.addAttribute("paymentList", rateList);
		model.addAttribute("orderId", order_Id);
		model.addAttribute("customerId", customer_Id);
		model.addAttribute("industryId", industry_Type_Id);
		model.addAttribute("channel", channel);

		model.addAttribute("userProfile", userProfile.getId());

		model.addAttribute("macAddress", loginArubaReqBean.getMacAddress());
		model.addAttribute("ssid", loginArubaReqBean.getSsid());
		model.addAttribute("clientIp", loginArubaReqBean.getClientIp());
		model.addAttribute("apname", loginArubaReqBean.getApName());
		model.addAttribute("vcName", loginArubaReqBean.getVcName());
		model.addAttribute("switchIp", loginArubaReqBean.getSwitchIp());
		model.addAttribute("url", loginArubaReqBean.getUrl());
		model.addAttribute("cmd", "authenticate");
		model.addAttribute("access", "Tplink");

		return "pg";
	}

	@RequestMapping(value = "/home/redeem", method = RequestMethod.POST)
	public String reedemWallet(Model model, @RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "plan", required = false) String plan, @RequestParam(value = "grant", required = false) String grant,
			@RequestParam(value = "access", required = false) String access, LoginArubaReqBean loginArubaReqBean) {

		System.out.println("user Id profile rrrrrrrrrrr ::" + userId);
		WcaUserProfile userProfile = this.service.findById(CommonUtils.checkInteger(userId));

		Integer walletPoint = userProfile.getWalletPoint();
		if (walletPoint != null && walletPoint >= CommonUtils.checkInteger(plan)) {
			walletPoint = walletPoint - CommonUtils.checkInteger(plan);
		} else {
			model.addAttribute("error", "You have not enough points to redeem your wallet");
		}
		userProfile.setWalletPoint(walletPoint);
		service.update(userProfile);

		String baseGrantUrl = userProfile.getBaseGrantUrl();
		String continueUrl = userProfile.getContinueUrl();
		Integer durationInSec = 60 * CommonUtils.checkInteger(grant);

		String GrantAccessURL = baseGrantUrl + "?continue_url=" + continueUrl + "&duration=" + durationInSec;
		LOGGER.info("Wifi Infra Grant Access URL :: [ URL ] " + GrantAccessURL);

		if (access != null && access.equalsIgnoreCase("Tplink")) {
			model.addAttribute("macAddress", loginArubaReqBean.getMacAddress());
			model.addAttribute("switchIp", loginArubaReqBean.getSwitchIp());
			return "loginTpLink";
		} else {
			model.addAttribute("GrantAccessURL", GrantAccessURL);
			model.addAttribute("macAddress", loginArubaReqBean.getMacAddress());
			model.addAttribute("ssid", loginArubaReqBean.getSsid());
			model.addAttribute("clientIp", loginArubaReqBean.getClientIp());
			model.addAttribute("apname", loginArubaReqBean.getApName());
			model.addAttribute("vcName", loginArubaReqBean.getVcName());
			model.addAttribute("switchIp", loginArubaReqBean.getSwitchIp());
			model.addAttribute("url", loginArubaReqBean.getUrl());
			model.addAttribute("cmd", "authenticate");

			return "loginAruba";
		}
	}

}
