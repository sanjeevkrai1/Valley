package com.valley.cws.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.valley.cws.bean.PlanInfo;
import com.valley.cws.bean.RedeemReqBean;
import com.valley.cws.bean.RedeemResBean;
import com.valley.cws.bean.WalletResBean;
import com.valley.cws.entity.WcaUserProfile;
import com.valley.cws.entity.WcaWalletPlan;
import com.valley.cws.service.AuthenticateService;
import com.valley.cws.service.ShowAdService;
import com.valley.cws.service.WalletService;
import com.valley.cws.utils.CommonUtils;

@RestController
public class RestApiController {

	public static final Logger LOGGER = Logger.getLogger(RestApiController.class);

	@Autowired
	AuthenticateService service;

	@Autowired
	ShowAdService showAdService;

	@Autowired
	WalletService walletService;

	@RequestMapping("/walletPlan")
	public WalletResBean walletPlan() {
		LOGGER.info("----Inside Wallet Plan Api----");
		List<PlanInfo> planList;
		PlanInfo planInfo;
		List<WcaWalletPlan> walletPlan;
		WalletResBean response = new WalletResBean();

		walletPlan = this.walletService.findAll();
		if (walletPlan != null) {
			planList = new ArrayList<>();
			for (WcaWalletPlan w : walletPlan) {
				planInfo = new PlanInfo();
				planInfo.setId(w.getId());
				planInfo.setGrantData(w.getGrantData());
				planInfo.setGrantMinute(w.getGrantMinute());
				planInfo.setPlanName(w.getPlanName());
				planInfo.setPlanPoint(w.getPlanPoint());
				planInfo.setPlanRate(w.getPlanRate());
				planList.add(planInfo);
			}
			response.setPlanInfo(planList);
		}
		return response;
	}

	@RequestMapping(value = "/redeemWalletPoint", method = RequestMethod.POST)
	public RedeemResBean reedemWallet(@RequestBody RedeemReqBean request) {

		System.out.println("Inside redeem API [ Client mac Address ] :: " + request.getMacAddress());

		String macAdress = request.getMacAddress();
		Long planId = request.getPlanId();
		RedeemResBean response = new RedeemResBean();

		WcaUserProfile userProfile = this.service.findOne(CommonUtils.checkString(macAdress));

		WcaWalletPlan walletPlan = this.walletService.findPlan(planId);

		Integer durationInSec = 60 * CommonUtils.checkInteger(walletPlan.getGrantMinute());

		Integer walletPoint = userProfile.getWalletPoint();
		if (walletPoint != null && walletPoint >= CommonUtils.checkInteger(walletPlan.getPlanPoint())) {
			walletPoint = walletPoint - CommonUtils.checkInteger(walletPlan.getPlanPoint());
			response.setSuccess("Successfully redeem wallet point.");
			response.setWalletpoint(walletPoint);
			response.setDuration(durationInSec);
		} else {
			response.setSuccess("You have not enough points to redeem your wallet.");
		}
		userProfile.setWalletPoint(walletPoint);
		service.update(userProfile);

		return response;
	}

}
