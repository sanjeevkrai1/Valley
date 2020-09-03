package com.valley.cws.service.api.impl.cisco;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valley.cws.bean.PolicyResBean;
import com.valley.cws.bean.RadiusBean;
import com.valley.cws.bean.StaticAdBean;
import com.valley.cws.bean.api.cisco.AdBean;
import com.valley.cws.bean.api.cisco.LoginCiscoReqBean;
import com.valley.cws.bean.api.cisco.LoginCiscoResBean;
import com.valley.cws.bean.api.cisco.RateCiscoAdBean;
import com.valley.cws.bean.api.cisco.RateCiscoReqBean;
import com.valley.cws.bean.api.cisco.RateCiscoResBean;
import com.valley.cws.bean.api.cisco.RedirectCiscoReqBean;
import com.valley.cws.bean.api.cisco.RedirectCiscoResBean;
import com.valley.cws.bean.api.cisco.RegistrationCiscoReqBean;
import com.valley.cws.bean.api.cisco.RegistrationCiscoResBean;
import com.valley.cws.bean.api.cisco.ValidateOtpCiscoReqBean;
import com.valley.cws.bean.api.cisco.ValidateOtpCiscoResBean;
import com.valley.cws.constants.Constants;
import com.valley.cws.constants.KeyConstants;
import com.valley.cws.dao.AuthenticateDao;
import com.valley.cws.entity.WcaAdData;
import com.valley.cws.entity.WcaAdShownHistory;
import com.valley.cws.entity.WcaDefaultAd;
import com.valley.cws.entity.WcaLoginHistory;
import com.valley.cws.entity.WcaUserProfile;
import com.valley.cws.service.IRadiusService;
import com.valley.cws.service.ShowAdService;
import com.valley.cws.service.api.cisco.IAuthenticateCiscoService;
import com.valley.cws.utils.ApacheHttpClientPost;
import com.valley.cws.utils.CommonUtils;
import com.valley.cws.utils.SendSms;

@Service
@Transactional(value = "transactionManager")
public class AuthenticateCiscoServiceImpl implements IAuthenticateCiscoService {
	private static final Logger LOGGER = Logger.getLogger(AuthenticateCiscoServiceImpl.class);
	private static final String SUCCESS_URL = "success_url";

	@Autowired
	AuthenticateDao userProfileDao;

	@Autowired
	ShowAdService showAdService;

	@Autowired
	IRadiusService radiusService;

	@Override
	public RedirectCiscoResBean validateMac(RedirectCiscoReqBean redirectCiscoReqBean) {
		LOGGER.info("START validateMac - [ mac address : " + redirectCiscoReqBean.getMacAddress() + " ] ");
		RedirectCiscoResBean redirectCiscoResBean;
		WcaUserProfile userProfile;
		String macAddress;
		String showAdStatus;
		WcaDefaultAd defaultAd;
		String adUrl = null;
		String adUrl1 = null;
		String adUrl2 = null;
		String adUrl3 = null;
		List<AdBean> adUrls;
		String physicalPath;
		String primaryCategory;
		PolicyResBean resBean;
		List<StaticAdBean> staticAds;
		Date updatedDate;
		Date currentDate;
		Integer compareDate;
		Boolean isSameDay = false;
		Integer screenType = null;

		macAddress = CommonUtils.checkString(redirectCiscoReqBean.getMacAddress()).toUpperCase();
		redirectCiscoResBean = new RedirectCiscoResBean();
		redirectCiscoResBean.setMacAddress(macAddress);

		if (CommonUtils.notEmpty(macAddress)) {
			redirectCiscoResBean.setStatus(Constants.SUCCESS);
			userProfile = userProfileDao.findOne(macAddress);
			if (userProfile != null) {
				LOGGER.info("validateMac - login case - mac found - [ mac address : "
						+ redirectCiscoReqBean.getMacAddress() + " ] , [ user id : " + userProfile.getId() + " ] ");
				redirectCiscoResBean.setAction(Constants.LOGIN);
			} else {
				LOGGER.info("validateMac - registration case - mac not found - [ mac address : "
						+ redirectCiscoReqBean.getMacAddress() + " ] ");
				redirectCiscoResBean.setAction(Constants.REGISTRATION);
			}

			// for getting ad
			showAdStatus = this.showAdService.getvalue(KeyConstants.FIRST_TIME_SHOW_AD);
			LOGGER.info("validateMac - getting first time ad - [ ad status : " + showAdStatus + " ] , [ mac address : "
					+ redirectCiscoReqBean.getMacAddress() + " ] ");

			physicalPath = showAdService.getvalue(KeyConstants.AD_URL);
			if (CommonUtils.checkString(showAdStatus).equalsIgnoreCase(Constants.ON)) {

				if (userProfile != null) {
					updatedDate = userProfile.getUpdatedOn();
					currentDate = new Date();
					compareDate = CommonUtils.CompareDate(updatedDate, currentDate);
					if (compareDate != 0) {
						primaryCategory = userProfile.getPrimaryCategory();
						if (CommonUtils.notEmpty(primaryCategory)) {
							resBean = ApacheHttpClientPost.getAd(macAddress);
							if (resBean != null && resBean.getIsStatic()) {
								screenType = CommonUtils.checkInteger(resBean.getStaticScreenType());
								staticAds = resBean.getStaticAds();
								adUrl = staticAds.get(0).getStaticAdUrl();
								if (screenType > 1 && staticAds.size() > 1) {
									adUrl1 = staticAds.get(1).getStaticAdUrl();
									adUrl1 = adUrl1.replace(Constants.RELATIVE_PATH, physicalPath);
								}

								if (screenType > 2 && staticAds.size() > 2) {
									adUrl2 = staticAds.get(2).getStaticAdUrl();
									adUrl2 = adUrl2.replace(Constants.RELATIVE_PATH, physicalPath);
								}

								if (screenType > 3 && staticAds.size() > 3) {
									adUrl3 = staticAds.get(3).getStaticAdUrl();
									adUrl3 = adUrl3.replace(Constants.RELATIVE_PATH, physicalPath);
								}
							}
						}
					} else
						isSameDay = true;
				}

				if (!isSameDay && CommonUtils.isEmpty(adUrl)) {
					defaultAd = userProfileDao.findAd(Constants.LOCATION_ALL);
					if (defaultAd != null) {
						adUrl = defaultAd.getFilePath();
					} else {
						LOGGER.error("ERROR - validateMac - default ad not configured for location all -  [ mac address : "
								+ redirectCiscoReqBean.getMacAddress() + " ] ");
					}
				}
			}

			if (CommonUtils.isEmpty(adUrl))
				redirectCiscoResBean.setIsAd(false);
			else {
				adUrl = CommonUtils.checkString(adUrl).replace(Constants.RELATIVE_PATH, physicalPath);
				adUrls = new ArrayList<AdBean>();
				adUrls.add(new AdBean(CommonUtils.checkString(adUrl)));
				if (CommonUtils.notEmpty(adUrl1))
					adUrls.add(new AdBean(CommonUtils.checkString(adUrl1)));
				if (CommonUtils.notEmpty(adUrl2))
					adUrls.add(new AdBean(CommonUtils.checkString(adUrl2)));
				if (CommonUtils.notEmpty(adUrl3))
					adUrls.add(new AdBean(CommonUtils.checkString(adUrl3)));

				redirectCiscoResBean.setAdUrls(adUrls);
				redirectCiscoResBean.setScreenType(CommonUtils.checkInteger(screenType));
				redirectCiscoResBean.setIsAd(true);
			}

		} else {
			redirectCiscoResBean.setStatus(Constants.FAILURE);
			redirectCiscoResBean.setReason(Constants.EMPTY_MAC);
		}
		LOGGER.info("END validateMac - [ mac address : " + redirectCiscoReqBean.getMacAddress() + " ] , [ response : "
				+ redirectCiscoResBean + " ] ");
		return redirectCiscoResBean;
	}

	@Override
	public RegistrationCiscoResBean registration(RegistrationCiscoReqBean registrationCiscoReqBean) {
		LOGGER.info("START registration - " + registrationCiscoReqBean);
		RegistrationCiscoResBean registrationCiscoResBean = null;
		String macAddress;
		WcaUserProfile userProfile;
		Integer randomPin;
		String mobileNumber;
		String actionUrl;
		String successUrl;
		String sessionTimeout;
		Date dob;

		registrationCiscoResBean = new RegistrationCiscoResBean();
		macAddress = CommonUtils.checkString(registrationCiscoReqBean.getMacAddress()).toUpperCase();
		registrationCiscoResBean.setMacAddress(macAddress);

		userProfile = userProfileDao.findOne(macAddress);
		if (userProfile == null) {
			userProfile = new WcaUserProfile();
			userProfile.setMacAddress(macAddress);
			randomPin = CommonUtils.getPin();
			mobileNumber = CommonUtils.checkString(registrationCiscoReqBean.getMobileNumber());
			actionUrl = CommonUtils.checkString(registrationCiscoReqBean.getLoginUrl());
			successUrl = CommonUtils.checkString(CommonUtils.propValue(SUCCESS_URL));

			userProfile.setUserName(CommonUtils.checkString(registrationCiscoReqBean.getUserName()));
			userProfile.setEmailId(CommonUtils.checkString(registrationCiscoReqBean.getEmailId()));
			userProfile.setMobileNo(CommonUtils.checkString(registrationCiscoReqBean.getMobileNumber()));
			userProfile.setGender(CommonUtils.checkString(registrationCiscoReqBean.getGender()));
			userProfile.setBaseGrantUrl(CommonUtils.checkString(actionUrl));
			userProfile.setContinueUrl(CommonUtils.checkString(registrationCiscoReqBean.getContinueUrl()));
			userProfile.setNodeMac(CommonUtils.checkString(registrationCiscoReqBean.getApMac()));
			userProfile.setApName(CommonUtils.checkString(registrationCiscoReqBean.getApName()));
			userProfile.setApTags(CommonUtils.checkString(registrationCiscoReqBean.getApTags()));
			userProfile.setLastOtp(CommonUtils.checkString(randomPin));
			dob = CommonUtils.getDob(registrationCiscoReqBean.getDob());
			if (dob != null)
				userProfile.setDob(dob);

			userProfile.setCreatedOn(new Date());

			userProfileDao.save(userProfile);
			System.out.println("GENERATED OTP : " + randomPin);
			LOGGER.info("registration - record saved - sending OTP - [ otp : " + randomPin + " ] , [ mobile No. : "
					+ mobileNumber + " ] , [ macAddress : " + macAddress + " ] ");
			SendSms.sendSms(randomPin, mobileNumber);

			// save radius bean
			sessionTimeout = showAdService.getvalue(KeyConstants.FIRST_TIME_ACCESS);
			saveRadiusDetails(mobileNumber, CommonUtils.checkString(randomPin), sessionTimeout);

			// set response
			registrationCiscoResBean.setStatus(Constants.SUCCESS);
			registrationCiscoResBean.setContinueUrl(CommonUtils.checkString(registrationCiscoReqBean.getContinueUrl()));
			registrationCiscoResBean.setSuccessUrl(successUrl);
			registrationCiscoResBean.setActionUrl(actionUrl);
			registrationCiscoResBean.setUserName(mobileNumber);

		} else {
			LOGGER.error("ERROR registration - user already registered - [ macAddress : " + macAddress + " ] ");
			registrationCiscoResBean.setStatus(Constants.FAILURE);
			registrationCiscoResBean.setReason(Constants.MAC_FOUND);
		}

		LOGGER.info("END registration - " + registrationCiscoResBean);
		return registrationCiscoResBean;
	}

	@Override
	public ValidateOtpCiscoResBean validateOtp(ValidateOtpCiscoReqBean validateOtpCiscoReqBean) {
		LOGGER.info("START validateOtp - " + validateOtpCiscoReqBean);
		ValidateOtpCiscoResBean validateOtpCiscoResBean;
		String macAddress;
		Integer otp;
		String mobileNumber;
		WcaUserProfile userProfile;
		Date expiryDate;
		Integer duration;
		WcaLoginHistory loginHistory;

		validateOtpCiscoResBean = new ValidateOtpCiscoResBean();
		macAddress = CommonUtils.checkString(validateOtpCiscoReqBean.getMacAddress()).toUpperCase();
		mobileNumber = CommonUtils.checkString(validateOtpCiscoReqBean.getMobileNumber());
		otp = CommonUtils.checkInteger(validateOtpCiscoReqBean.getOtp());

		validateOtpCiscoResBean.setMacAddress(macAddress);

		if (CommonUtils.notEmpty(macAddress)) {
			userProfile = userProfileDao.findOne(macAddress);
			if (userProfile != null) {
				if (CommonUtils.checkInteger(userProfile.getLastOtp()).equals(otp)) {
					duration = radiusService.getDuration(mobileNumber);
					expiryDate = CommonUtils.addMinutes(new Date(), duration);

					LOGGER.info("validateOtp - save expiry dates - [ expiry date : " + expiryDate
							+ " ] , [ duration : " + duration + " ] , [ macAddress : " + macAddress + " ] ");
					// update user profile
					userProfile.setUpdatedOn(new Date());
					userProfile.setExpiryDate(expiryDate);
					userProfile.setStartDate(new Date());

					userProfileDao.update(userProfile);

					// save into login history
					loginHistory = new WcaLoginHistory();
					loginHistory.setGrantDuration(duration);
					loginHistory.setWcaUserProfile(userProfile);
					loginHistory.setCreatedOn(new Date());
					loginHistory.setNodeMac(CommonUtils.checkString(userProfile.getNodeMac()));
					loginHistory.setOtpUsed(CommonUtils.checkString(otp));

					userProfileDao.saveLoginDetail(loginHistory);

					// set response bean
					validateOtpCiscoResBean.setStatus(Constants.SUCCESS);
					validateOtpCiscoResBean.setMobileNumber(mobileNumber);
					validateOtpCiscoResBean.setUserName(userProfile.getUserName());
					validateOtpCiscoResBean.setEmailId(userProfile.getEmailId());
					validateOtpCiscoResBean.setGender(userProfile.getGender());
					validateOtpCiscoResBean.setSsid(userProfile.getSsid());

				} else {
					LOGGER.error("ERROR validateOtp - opt mismatch - [ requested otp : " + otp + " ] , [ db otp : "
							+ userProfile.getLastOtp() + " ] , [ macAddress : " + macAddress + " ] ");
					validateOtpCiscoResBean.setStatus(Constants.FAILURE);
					validateOtpCiscoResBean.setReason(Constants.OTP_MISMATCH);
				}

			} else {
				LOGGER.error("ERROR validateOtp - macAddress not found in db - [ macAddress : " + macAddress + " ] ");
				validateOtpCiscoResBean.setStatus(Constants.FAILURE);
				validateOtpCiscoResBean.setReason(Constants.MAC_NOT_FOUND);

			}
		} else {
			LOGGER.error("ERROR validateOtp - macAddress empty - [ macAddress : " + macAddress + " ] ");
			validateOtpCiscoResBean.setStatus(Constants.FAILURE);
			validateOtpCiscoResBean.setReason(Constants.EMPTY_MAC);

		}
		LOGGER.info("END validateOtp - " + validateOtpCiscoResBean);
		return validateOtpCiscoResBean;
	}

	@Override
	public LoginCiscoResBean login(LoginCiscoReqBean loginCiscoReqBean) {
		LOGGER.info("START login - " + loginCiscoReqBean);
		LoginCiscoResBean loginCiscoResBean = null;
		String macAddress;
		WcaUserProfile userProfile;
		Integer randomPin;
		String mobileNumber;
		String sessionTimeout = null;
		String actionUrl;
		String successUrl;
		Date updatedDate;
		Date currentDate;

		loginCiscoResBean = new LoginCiscoResBean();
		macAddress = CommonUtils.checkString(loginCiscoReqBean.getMacAddress()).toUpperCase();
		loginCiscoResBean.setMacAddress(macAddress);

		userProfile = userProfileDao.findOne(macAddress);
		if (userProfile != null) {
			randomPin = CommonUtils.getPin();
			mobileNumber = CommonUtils.checkString(loginCiscoReqBean.getMobileNumber());
			if (CommonUtils.isEmpty(mobileNumber))
				mobileNumber = CommonUtils.checkString(userProfile.getMobileNo());
			actionUrl = CommonUtils.checkString(loginCiscoReqBean.getLoginUrl());
			successUrl = CommonUtils.checkString(CommonUtils.propValue(SUCCESS_URL));

			updatedDate = userProfile.getUpdatedOn();
			currentDate = new Date();
			Integer compareValue = CommonUtils.CompareDate(updatedDate, currentDate);
			if (compareValue != 0) {
				userProfile.setAdShownCount(0);
				userProfile.setImageCounter(0);
				userProfile.setVideoCounter(0);
				userProfile.setAudioCounter(0);
			}

			userProfile.setMobileNo(mobileNumber);
			userProfile.setBaseGrantUrl(CommonUtils.checkString(actionUrl));
			userProfile.setContinueUrl(CommonUtils.checkString(loginCiscoReqBean.getContinueUrl()));
			userProfile.setNodeMac(CommonUtils.checkString(loginCiscoReqBean.getApMac()));
			userProfile.setApName(CommonUtils.checkString(loginCiscoReqBean.getApName()));
			userProfile.setApTags(CommonUtils.checkString(loginCiscoReqBean.getApTags()));
			userProfile.setLastOtp(CommonUtils.checkString(randomPin));

			userProfileDao.update(userProfile);
			System.out.println("GENERATED OTP : " + randomPin);
			LOGGER.info("login - record updated - sending OTP - [ otp : " + randomPin + " ] , [ mobile No. : "
					+ mobileNumber + " ] , [ macAddress : " + macAddress + " ] ");
			SendSms.sendSms(randomPin, mobileNumber);

			// save radius bean
			if (compareValue != 0) {
				sessionTimeout = showAdService.getvalue(KeyConstants.FIRST_TIME_ACCESS);
			}

			saveRadiusDetails(mobileNumber, CommonUtils.checkString(randomPin), sessionTimeout);

			// set response
			loginCiscoResBean.setStatus(Constants.SUCCESS);
			loginCiscoResBean.setContinueUrl(CommonUtils.checkString(loginCiscoReqBean.getContinueUrl()));
			loginCiscoResBean.setSuccessUrl(successUrl);
			loginCiscoResBean.setActionUrl(actionUrl);
			loginCiscoResBean.setUserName(mobileNumber);
			loginCiscoResBean.setMobileNumber(mobileNumber);

		} else {
			LOGGER.error("ERROR login - mac not found in db - [ macAddress : " + macAddress + " ] ");
			loginCiscoResBean.setStatus(Constants.FAILURE);
			loginCiscoResBean.setReason(Constants.MAC_NOT_FOUND);
		}

		LOGGER.info("END login - " + loginCiscoResBean);
		return loginCiscoResBean;
	}

	@Override
	public RateCiscoResBean rateAds(RateCiscoReqBean rateCiscoReqBean) {
		LOGGER.info("START rateAds - " + rateCiscoReqBean);
		RateCiscoResBean rateCiscoResBean;
		Boolean isLikeCount;
		Boolean isDislikeCount;
		Boolean isRateCount;
		Boolean isActivity;
		Boolean isSchedule;
		Boolean isCampaign;
		Boolean isPeriodic;
		Integer ratePoint;
		Integer adId;
		String macAddress;
		Integer duration;
		String mobileNumber;
		WcaAdData adData;
		String adType = null;
		String opinion = null;
		WcaUserProfile userProfile;
		Integer totalWalletPoints = 0;
		WcaAdShownHistory adShownHistory;
		List<RateCiscoAdBean> adList;

		isActivity = CommonUtils.checkBool(rateCiscoReqBean.getIsActivity());
		isSchedule = CommonUtils.checkBool(rateCiscoReqBean.getIsSchedule());
		isCampaign = CommonUtils.checkBool(rateCiscoReqBean.getIsCampaign());
		isPeriodic = CommonUtils.checkBool(rateCiscoReqBean.getIsPeriodic());

		mobileNumber = CommonUtils.checkString(rateCiscoReqBean.getMobileNumber());
		macAddress = CommonUtils.checkString(rateCiscoReqBean.getMacAddress()).toUpperCase();
		duration = CommonUtils.checkInteger(rateCiscoReqBean.getDuration());

		adList = rateCiscoReqBean.getAdList();

		rateCiscoResBean = new RateCiscoResBean();
		rateCiscoResBean.setMacAddress(macAddress);

		userProfile = this.userProfileDao.findOne(macAddress);
		if (adList != null && !adList.isEmpty()) {

			for (RateCiscoAdBean adBean : adList) {
				opinion = null;
				isLikeCount = CommonUtils.checkBool(adBean.getIsLike());
				isDislikeCount = CommonUtils.checkBool(adBean.getIsDisLike());
				isRateCount = CommonUtils.checkBool(adBean.getIsRating());
				ratePoint = CommonUtils.checkInteger(adBean.getRatingPoint());
				adId = CommonUtils.checkInteger(adBean.getAdId());

				adData = showAdService.findAd(adId);
				if (adData != null) {
					// update ad data
					if (isLikeCount) {
						adData.setLikeCount(CommonUtils.checkInteger(adData.getLikeCount()) + 1);
						opinion = Constants.LIKE;
					}
					if (isDislikeCount) {
						adData.setDislikeCount(CommonUtils.checkInteger(adData.getDislikeCount()) + 1);
						opinion = Constants.DISLIKE;
					}
					if (isRateCount) {
						adData.setRateCount(CommonUtils.checkInteger(adData.getRateCount()) + 1);
						adData.setTotalRateCount(CommonUtils.checkInteger(adData.getTotalRateCount()) + ratePoint);
					}

					totalWalletPoints += CommonUtils.checkInteger(adData.getPoint());
					adType = CommonUtils.checkString(adData.getAdsType());
					showAdService.update(adData);

					// save ad shown history
					adShownHistory = new WcaAdShownHistory();

					adShownHistory.setWcaAdData(adData);
					adShownHistory.setWcaUserProfile(userProfile);
					if (CommonUtils.notEmpty(opinion))
						adShownHistory.setOpinion(opinion);

					if (ratePoint != 0)
						adShownHistory.setRating(ratePoint);

					if (!isPeriodic)
						adShownHistory.setFreeMinutes(duration);
					showAdService.saveAdHistory(adShownHistory);
				}
			}
		}

		// update user profile
		if (userProfile != null) {
			if (adType.equalsIgnoreCase(Constants.ADTYPE_IMAGE))
				userProfile.setImageCounter(CommonUtils.checkInteger(userProfile.getImageCounter()) + 1);

			else if (adType.equalsIgnoreCase(Constants.ADTYPE_VIDEO))
				userProfile.setVideoCounter(CommonUtils.checkInteger(userProfile.getVideoCounter()) + 1);

			else
				userProfile.setAudioCounter(CommonUtils.checkInteger(userProfile.getAudioCounter()) + 1);

			if (!isActivity && !isCampaign && !isSchedule && !isPeriodic)
				userProfile.setAdShownCount(CommonUtils.checkInteger(userProfile.getAdShownCount()) + 1);

			totalWalletPoints += CommonUtils.checkInteger(userProfile.getWalletPoint());
			userProfile.setWalletPoint(totalWalletPoints);
			userProfileDao.update(userProfile);
			rateCiscoResBean.setStatus(Constants.SUCCESS);
		} else {
			LOGGER.error("ERROR rateAds - user profile not found by mac address - [ macAddress : " + macAddress + " ] ");
			rateCiscoResBean.setStatus(Constants.FAILURE);
			rateCiscoResBean.setReason(Constants.GENERAL_ERROR);
		}

		// save radius duration
		if (!isActivity && !isCampaign && !isSchedule && !isPeriodic)
			saveRadiusDetails(mobileNumber, Constants.EMPTY, CommonUtils.checkString(duration));

		LOGGER.info("END rateAds - " + rateCiscoResBean);
		return rateCiscoResBean;
	}

	private void saveRadiusDetails(String mobileNumber, String randomPin, String duration) {
		RadiusBean radiusBean = new RadiusBean();

		radiusBean.setRadcheckUsername(mobileNumber);
		radiusBean.setRadcheckValue(randomPin);
		radiusBean.setRadcheckAttribute(Constants.RADCHECK_ATTR);
		radiusBean.setRadcheckOp(Constants.RADCHECK_OP);

		radiusBean.setRadreplyUsername(mobileNumber);
		radiusBean.setRadreplyValue(CommonUtils.checkString(CommonUtils.minToSec(duration)));
		radiusBean.setRadreplyAttribute(Constants.RADREPLY_ATTR);
		radiusBean.setRadreplyOp(Constants.RADREPLY_OP);

		radiusService.saveRadiusDetails(radiusBean);
	}
}
