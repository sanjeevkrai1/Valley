package com.valley.cws.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valley.cws.bean.ShowAdRequestBean;
import com.valley.cws.bean.api.aruba.LoginArubaReqBean;
import com.valley.cws.constants.Constants;
import com.valley.cws.entity.WcaAdData;
import com.valley.cws.entity.WcaAdShownHistory;
import com.valley.cws.entity.WcaIpRanges;
import com.valley.cws.entity.WcaUserProfile;
import com.valley.cws.service.AuthenticateService;
import com.valley.cws.service.ShowAdService;
import com.valley.cws.service.api.aruba.IAuthenticateArubaService;
import com.valley.cws.utils.CommonUtils;

@Controller
public class ArubaRateController {

	public static final Logger LOGGER = Logger.getLogger(ArubaRateController.class);

	@Autowired
	ShowAdService service;

	@Autowired
	AuthenticateService authService;

	@Autowired
	IAuthenticateArubaService arubaService;

	@RequestMapping(value = "/home/grant", method = RequestMethod.POST)
	public String grant(ShowAdRequestBean request, Model model, @RequestParam(value = "grantTime", required = false) String grantTime,
			@RequestParam(value = "baseGrantUrl", required = false) String baseGrantUrl,
			@RequestParam(value = "adIds", required = false) String adIds,
			@RequestParam(value = "userId", required = false) String userProfileID,
			@RequestParam(value = "opinion", required = false) String opinion,
			@RequestParam(value = "access", required = false) String access,
			@RequestParam(value = "ratePoint", required = false) String rating, LoginArubaReqBean loginArubaReqBean) {

		LOGGER.info("[ opinion :: ] " + opinion + "[ Rating ] " + rating + " , [ Login Aruba ]" + loginArubaReqBean + ", [ Ad Ids ]"
				+ adIds + ", [ access ]" + access);

		Integer adId;
		Integer duration;
		WcaAdData adData;
		String adType = null;
		WcaUserProfile userProfile;
		Integer totalWalletPoints = 0;
		WcaAdShownHistory adShownHistory;
		List<String> adList;

		List<WcaIpRanges> ipranges;
		String location = null;
		long ipStart;
		long ipEnd;
		long ipToTest;

		userProfile = this.authService.findById(CommonUtils.checkInteger(userProfileID));

		adList = new ArrayList<String>(Arrays.asList(adIds.split("\\s*,\\s*")));

		if (adList != null && !adList.isEmpty()) {

			for (String adBean : adList) {
				adId = CommonUtils.checkInteger(adBean);
				adData = service.findAd(adId);
				if (adData != null) {
					// update ad data
					totalWalletPoints += CommonUtils.checkInteger(adData.getPoint());
					adType = CommonUtils.checkString(adData.getAdsType());
					// save ad shown history
					adShownHistory = new WcaAdShownHistory();
					adShownHistory.setWcaAdData(adData);
					adShownHistory.setWcaUserProfile(userProfile);

					String[] opinionArr = opinion.split(",");
					for (String str : opinionArr) {
						String[] opinionValue = str.split("\\|");
						if (CommonUtils.checkInteger(opinionValue[0]).equals(adId)) {
							if (CommonUtils.notEmpty(opinion))
								adShownHistory.setOpinion(opinionValue[1]);
						}
					}

					String[] ratingArr = rating.split(",");
					for (String str : ratingArr) {
						String[] ratingValue = str.split("\\|");
						if (CommonUtils.checkInteger(ratingValue[0]).equals(adId)) {
							if (rating != null && rating != "")
								adShownHistory.setRating(CommonUtils.checkInteger(ratingValue[1]));
						}
					}

					adShownHistory.setFreeMinutes(CommonUtils.checkInteger(grantTime));
					service.saveAdHistory(adShownHistory);
				}
			}
		}

		if (userProfile != null) {
			if (adType.equalsIgnoreCase(Constants.ADTYPE_IMAGE)) {
				userProfile.setImageCounter(CommonUtils.checkInteger(userProfile.getImageCounter()) + 1);
				duration = request.getStaticFreeMinute();
			} else if (adType.equalsIgnoreCase(Constants.ADTYPE_VIDEO)) {
				userProfile.setVideoCounter(CommonUtils.checkInteger(userProfile.getVideoCounter()) + 1);
				duration = request.getVideoFreeMinute();
			} else {
				userProfile.setAudioCounter(CommonUtils.checkInteger(userProfile.getAudioCounter()) + 1);
				duration = request.getAudioFreeMinute();
			}
			userProfile.setAdShownCount(CommonUtils.checkInteger(userProfile.getAdShownCount()) + 1);

			totalWalletPoints += CommonUtils.checkInteger(userProfile.getWalletPoint());
			userProfile.setWalletPoint(totalWalletPoints);
			authService.update(userProfile);
			arubaService.saveRadiusDetails(CommonUtils.checkString(userProfile.getMobileNo()), Constants.EMPTY,
					CommonUtils.checkString(duration));
		}

		if (access != null && access.equalsIgnoreCase("Tplink")) {
			model.addAttribute("macAddress", loginArubaReqBean.getMacAddress());
			model.addAttribute("switchIp", loginArubaReqBean.getSwitchIp());
			return "loginTpLink";
		} else {
			ipranges = this.authService.findAllIp();

			for (WcaIpRanges wcaIpRanges : ipranges) {
				try {
					ipStart = CommonUtils.ipToLong(InetAddress.getByName(wcaIpRanges.getIpStart()));
					ipEnd = CommonUtils.ipToLong(InetAddress.getByName(wcaIpRanges.getIpEnd()));
					ipToTest = CommonUtils.ipToLong(InetAddress.getByName(loginArubaReqBean.getClientIp()));
					if (ipToTest >= ipStart && ipToTest <= ipEnd) {
						location = wcaIpRanges.getLocation();
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}

			model.addAttribute("macAddress", loginArubaReqBean.getMacAddress());
			model.addAttribute("ssid", loginArubaReqBean.getSsid());
			model.addAttribute("clientIp", loginArubaReqBean.getClientIp());
			model.addAttribute("apname", loginArubaReqBean.getApName());
			model.addAttribute("vcName", loginArubaReqBean.getVcName());
			model.addAttribute("switchIp", loginArubaReqBean.getSwitchIp());
			model.addAttribute("url", loginArubaReqBean.getUrl());
			model.addAttribute("cmd", "authenticate");

			if (location != null && location.equalsIgnoreCase("Ambience Mall")) {
				return "loginAmbienceAruba";
			} else if (location != null && location.contains("IGI Airport")) {
				return "loginIGIAruba";
			} else
				return "loginAruba";
		}
	}

	@RequestMapping(value = "/home/rate", method = RequestMethod.POST)
	public @ResponseBody Integer rate(ShowAdRequestBean request, Model model, HttpServletResponse res, HttpServletRequest req) {
		LOGGER.info("Ad Id :: " + request.getAdId() + "Like Count" + request.getLikeCount());
		Integer likeCount = request.getLikeCount();
		Integer dislikeCount = request.getDisLikeCount();
		Integer returnValue = 0;

		WcaAdData adData = this.service.findAd(CommonUtils.checkInteger(request.getAdId()));
		if (adData != null) {
			if (likeCount != null) {
				adData.setLikeCount(request.getLikeCount());
				returnValue = likeCount;
			}
			if (dislikeCount != null) {
				adData.setDislikeCount(request.getDisLikeCount());
				returnValue = dislikeCount;
			}
			adData = this.service.update(adData);
		}
		res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
		System.out.println("ORIGIN : " + req.getHeader("Origin"));
		return returnValue;
	}

	@RequestMapping(value = "/home/rateCount", method = RequestMethod.POST)
	public @ResponseBody double rateCount(ShowAdRequestBean request, Model model, HttpServletResponse res, HttpServletRequest req) {
		LOGGER.info("Ad Id :: " + request.getAdId() + "rate Count" + request.getRateCount() + "Total point Count"
				+ request.getTotalRatePoint());
		int rateCount = request.getRateCount();
		int totalRatePoint = request.getTotalRatePoint();
		double returnValue = 0.0;
		WcaAdData adData = this.service.findAd(CommonUtils.checkInteger(request.getAdId()));
		if (adData != null) {
			adData.setRateCount(rateCount);
			adData.setTotalRateCount(totalRatePoint);
			double ratePercent = (double) totalRatePoint / rateCount;
			System.out.println("Rate Percent" + ratePercent);
			returnValue = CommonUtils.round(ratePercent, 1);
		}
		adData = this.service.update(adData);
		res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
		System.out.println("ORIGIN : " + req.getHeader("Origin"));
		return returnValue;
	}

}
