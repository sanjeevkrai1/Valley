package com.valley.cws.service.api.impl.aruba;

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
import com.valley.cws.bean.api.aruba.AdBean;
import com.valley.cws.bean.api.aruba.LoginArubaReqBean;
import com.valley.cws.bean.api.aruba.LoginArubaResBean;
import com.valley.cws.bean.api.aruba.RateArubaAdBean;
import com.valley.cws.bean.api.aruba.RateArubaReqBean;
import com.valley.cws.bean.api.aruba.RateArubaResBean;
import com.valley.cws.bean.api.aruba.RedirectArubaReqBean;
import com.valley.cws.bean.api.aruba.RedirectArubaResBean;
import com.valley.cws.bean.api.aruba.RegistrationArubaReqBean;
import com.valley.cws.bean.api.aruba.RegistrationArubaResBean;
import com.valley.cws.bean.api.aruba.ValidateOtpArubaReqBean;
import com.valley.cws.bean.api.aruba.ValidateOtpArubaResBean;
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
import com.valley.cws.service.api.aruba.IAuthenticateArubaService;
import com.valley.cws.utils.ApacheHttpClientPost;
import com.valley.cws.utils.CommonUtils;
import com.valley.cws.utils.SendSms;

@Service
@Transactional(value = "transactionManager")
public class AuthenticateArubaServiceImpl implements IAuthenticateArubaService {
	private static final Logger LOGGER = Logger.getLogger(AuthenticateArubaServiceImpl.class);
	private static final String ACTION_URL_BEFORE = "action_url_before";
	private static final String ACTION_URL_AFTER = "action_url_after";

	@Autowired
	AuthenticateDao userProfileDao;

	@Autowired
	ShowAdService showAdService;

	@Autowired
	IRadiusService radiusService;

	@Override
	public RedirectArubaResBean validateMac(RedirectArubaReqBean redirectArubaReqBean) {
		LOGGER.info("START validateMac - [ mac address : " + redirectArubaReqBean.getMacAddress() + " ] ");
		RedirectArubaResBean redirectArubaResBean;
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
		java.util.List<StaticAdBean> staticAds;
		Date updatedDate;
		Date currentDate;
		Integer compareDate;
		Boolean isSameDay = false;
		Integer screenType = null;

		macAddress = CommonUtils.checkString(redirectArubaReqBean.getMacAddress()).toUpperCase();
		redirectArubaResBean = new RedirectArubaResBean();
		redirectArubaResBean.setMacAddress(macAddress);

		if (CommonUtils.notEmpty(macAddress)) {
			redirectArubaResBean.setStatus(Constants.SUCCESS);
			userProfile = userProfileDao.findOne(macAddress);
			if (userProfile != null) {
				LOGGER.info("validateMac - login case - mac found - [ mac address : "
						+ redirectArubaReqBean.getMacAddress() + " ] , [ user id : " + userProfile.getId() + " ] ");
				redirectArubaResBean.setAction(Constants.LOGIN);
			} else {
				LOGGER.info("validateMac - registration case - mac not found - [ mac address : "
						+ redirectArubaReqBean.getMacAddress() + " ] ");
				redirectArubaResBean.setAction(Constants.REGISTRATION);
			}

			// for getting ad
			showAdStatus = this.showAdService.getvalue(KeyConstants.FIRST_TIME_SHOW_AD);
			LOGGER.info("validateMac - getting first time ad - [ ad status : " + showAdStatus + " ] , [ mac address : "
					+ redirectArubaReqBean.getMacAddress() + " ] ");

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
									adUrl1 = CommonUtils.checkString(adUrl1).replace(Constants.RELATIVE_PATH,
											physicalPath);
								}

								if (screenType > 2 && staticAds.size() > 2) {
									adUrl2 = staticAds.get(2).getStaticAdUrl();
									adUrl2 = CommonUtils.checkString(adUrl2).replace(Constants.RELATIVE_PATH,
											physicalPath);
								}

								if (screenType > 3 && staticAds.size() > 3) {
									adUrl3 = staticAds.get(3).getStaticAdUrl();
									adUrl3 = CommonUtils.checkString(adUrl3).replace(Constants.RELATIVE_PATH,
											physicalPath);
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
								+ redirectArubaReqBean.getMacAddress() + " ] ");
					}
				}
			}

			if (CommonUtils.isEmpty(adUrl))
				redirectArubaResBean.setIsAd(false);
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

				redirectArubaResBean.setAdUrls(adUrls);
				redirectArubaResBean.setScreenType(CommonUtils.checkInteger(screenType));
				redirectArubaResBean.setIsAd(true);
			}

		} else {
			redirectArubaResBean.setStatus(Constants.FAILURE);
			redirectArubaResBean.setReason(Constants.EMPTY_MAC);
		}
		LOGGER.info("END validateMac - [ mac address : " + redirectArubaReqBean.getMacAddress() + " ] , [ response : "
				+ redirectArubaResBean + " ] ");
		return redirectArubaResBean;
	}

	@Override
	public RegistrationArubaResBean registration(RegistrationArubaReqBean registrationArubaReqBean) {
		LOGGER.info("START registration - " + registrationArubaReqBean);
		RegistrationArubaResBean registrationArubaResBean = null;
		String macAddress;
		WcaUserProfile userProfile;
		Integer randomPin;
		String mobileNumber;
		String actionUrl;
		String sessionTimeout;
		Date dob;

		registrationArubaResBean = new RegistrationArubaResBean();
		macAddress = CommonUtils.checkString(registrationArubaReqBean.getMacAddress()).toUpperCase();
		registrationArubaResBean.setMacAddress(macAddress);

		userProfile = userProfileDao.findOne(macAddress);
		if (userProfile == null) {
			userProfile = new WcaUserProfile();
			userProfile.setMacAddress(macAddress);
			randomPin = CommonUtils.getPin();
			mobileNumber = CommonUtils.checkString(registrationArubaReqBean.getMobileNumber());
			actionUrl = CommonUtils.propValue(ACTION_URL_BEFORE)
					+ CommonUtils.checkString(registrationArubaReqBean.getSwitchIp())
					+ CommonUtils.propValue(ACTION_URL_AFTER);

			userProfile.setUserName(CommonUtils.checkString(registrationArubaReqBean.getUserName()));
			userProfile.setEmailId(CommonUtils.checkString(registrationArubaReqBean.getEmailId()));
			userProfile.setMobileNo(CommonUtils.checkString(registrationArubaReqBean.getMobileNumber()));
			userProfile.setGender(CommonUtils.checkString(registrationArubaReqBean.getGender()));
			userProfile.setBaseGrantUrl(CommonUtils.checkString(actionUrl));
			userProfile.setContinueUrl(CommonUtils.checkString(registrationArubaReqBean.getUrl()));
			userProfile.setNodeMac(CommonUtils.checkString(registrationArubaReqBean.getApName()));
			userProfile.setClientIp(CommonUtils.checkString(registrationArubaReqBean.getClientIp()));
			userProfile.setSsid(CommonUtils.checkString(registrationArubaReqBean.getSsid()));
			userProfile.setLastOtp(CommonUtils.checkString(randomPin));
			dob = CommonUtils.getDob(registrationArubaReqBean.getDob());
			if (dob != null)
				userProfile.setDob(dob);

			userProfile.setCreatedOn(new Date());

			userProfileDao.save(userProfile);
			LOGGER.info("registration - record saved - sending OTP - [ otp : " + randomPin + " ] , [ mobile No. : "
					+ mobileNumber + " ] , [ macAddress : " + macAddress + " ] ");
			SendSms.sendSms(randomPin, mobileNumber);

			// save radius bean
			sessionTimeout = showAdService.getvalue(KeyConstants.FIRST_TIME_ACCESS);
			saveRadiusDetails(mobileNumber, CommonUtils.checkString(randomPin), sessionTimeout);

			// set response
			registrationArubaResBean.setStatus(Constants.SUCCESS);
			registrationArubaResBean.setLogin(Constants.AAA_LOGIN);
			registrationArubaResBean.setCmd(Constants.AAA_CMD);
			registrationArubaResBean.setUrl(CommonUtils.checkString(registrationArubaReqBean.getUrl()));
			registrationArubaResBean.setActionUrl(actionUrl);
			registrationArubaResBean.setMobileNumber(mobileNumber);

		} else {
			LOGGER.error("ERROR registration - user already registered - [ macAddress : " + macAddress + " ] ");
			registrationArubaResBean.setStatus(Constants.FAILURE);
			registrationArubaResBean.setReason(Constants.MAC_FOUND);
		}

		LOGGER.info("END registration - " + registrationArubaResBean);
		return registrationArubaResBean;
	}

	@Override
	public ValidateOtpArubaResBean validateOtp(ValidateOtpArubaReqBean validateOtpArubaReqBean) {
		LOGGER.info("START validateOtp - " + validateOtpArubaReqBean);
		ValidateOtpArubaResBean validateOtpArubaResBean;
		String macAddress;
		Integer otp;
		String mobileNumber;
		WcaUserProfile userProfile;
		Date expiryDate;
		Integer duration;
		WcaLoginHistory loginHistory;

		validateOtpArubaResBean = new ValidateOtpArubaResBean();
		macAddress = CommonUtils.checkString(validateOtpArubaReqBean.getMacAddress()).toUpperCase();
		mobileNumber = CommonUtils.checkString(validateOtpArubaReqBean.getMobileNumber());
		otp = CommonUtils.checkInteger(validateOtpArubaReqBean.getOtp());

		validateOtpArubaResBean.setMacAddress(macAddress);

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
					validateOtpArubaResBean.setStatus(Constants.SUCCESS);
					validateOtpArubaResBean.setMobileNumber(mobileNumber);
					validateOtpArubaResBean.setUserName(userProfile.getUserName());
					validateOtpArubaResBean.setEmailId(userProfile.getEmailId());
					validateOtpArubaResBean.setGender(userProfile.getGender());
					validateOtpArubaResBean.setSsid(userProfile.getSsid());

				} else {
					LOGGER.error("ERROR validateOtp - opt mismatch - [ requested otp : " + otp + " ] , [ db otp : "
							+ userProfile.getLastOtp() + " ] , [ macAddress : " + macAddress + " ] ");
					validateOtpArubaResBean.setStatus(Constants.FAILURE);
					validateOtpArubaResBean.setReason(Constants.OTP_MISMATCH);
				}

			} else {
				LOGGER.error("ERROR validateOtp - macAddress not found in db - [ macAddress : " + macAddress + " ] ");
				validateOtpArubaResBean.setStatus(Constants.FAILURE);
				validateOtpArubaResBean.setReason(Constants.MAC_NOT_FOUND);

			}
		} else {
			LOGGER.error("ERROR validateOtp - macAddress empty - [ macAddress : " + macAddress + " ] ");
			validateOtpArubaResBean.setStatus(Constants.FAILURE);
			validateOtpArubaResBean.setReason(Constants.EMPTY_MAC);

		}
		LOGGER.info("END validateOtp - " + validateOtpArubaResBean);
		return validateOtpArubaResBean;
	}

	@Override
	public LoginArubaResBean login(LoginArubaReqBean loginArubaReqBean) {
		LOGGER.info("START login - " + loginArubaReqBean);
		LoginArubaResBean loginArubaResBean = null;
		String macAddress;
		WcaUserProfile userProfile;
		Integer randomPin;
		String mobileNumber;
		String sessionTimeout = null;
		String actionUrl;
		Date updatedDate;
		Date currentDate;

		loginArubaResBean = new LoginArubaResBean();
		macAddress = CommonUtils.checkString(loginArubaReqBean.getMacAddress()).toUpperCase();
		loginArubaResBean.setMacAddress(macAddress);

		userProfile = userProfileDao.findOne(macAddress);
		if (userProfile != null) {
			randomPin = CommonUtils.getPin();
			mobileNumber = CommonUtils.checkString(loginArubaReqBean.getMobileNumber());
			if (CommonUtils.isEmpty(mobileNumber))
				mobileNumber = CommonUtils.checkString(userProfile.getMobileNo());
			actionUrl = CommonUtils.propValue(ACTION_URL_BEFORE)
					+ CommonUtils.checkString(loginArubaReqBean.getSwitchIp())
					+ CommonUtils.propValue(ACTION_URL_AFTER);

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
			userProfile.setContinueUrl(CommonUtils.checkString(loginArubaReqBean.getUrl()));
			userProfile.setNodeMac(CommonUtils.checkString(loginArubaReqBean.getApName()));
			userProfile.setClientIp(CommonUtils.checkString(loginArubaReqBean.getClientIp()));
			userProfile.setSsid(CommonUtils.checkString(loginArubaReqBean.getSsid()));
			userProfile.setLastOtp(CommonUtils.checkString(randomPin));

			userProfileDao.update(userProfile);
			LOGGER.info("login - record updated - sending OTP - [ otp : " + randomPin + " ] , [ mobile No. : "
					+ mobileNumber + " ] , [ macAddress : " + macAddress + " ] ");
			SendSms.sendSms(randomPin, mobileNumber);

			// save radius bean
			if (compareValue != 0) {
				sessionTimeout = showAdService.getvalue(KeyConstants.FIRST_TIME_ACCESS);
			}

			saveRadiusDetails(mobileNumber, CommonUtils.checkString(randomPin), sessionTimeout);

			// set response
			loginArubaResBean.setStatus(Constants.SUCCESS);
			loginArubaResBean.setLogin(Constants.AAA_LOGIN);
			loginArubaResBean.setCmd(Constants.AAA_CMD);
			loginArubaResBean.setUrl(CommonUtils.checkString(loginArubaReqBean.getUrl()));
			loginArubaResBean.setActionUrl(actionUrl);
			loginArubaResBean.setMobileNumber(mobileNumber);

		} else {
			LOGGER.error("ERROR login - mac not found in db - [ macAddress : " + macAddress + " ] ");
			loginArubaResBean.setStatus(Constants.FAILURE);
			loginArubaResBean.setReason(Constants.MAC_NOT_FOUND);
		}

		LOGGER.info("END login - " + loginArubaResBean);
		return loginArubaResBean;
	}

	@Override
	public RateArubaResBean rateAds(RateArubaReqBean rateArubaReqBean) {
		LOGGER.info("START rateAds - " + rateArubaReqBean);
		RateArubaResBean rateArubaResBean;
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
		List<RateArubaAdBean> adList;

		isActivity = CommonUtils.checkBool(rateArubaReqBean.getIsActivity());
		isSchedule = CommonUtils.checkBool(rateArubaReqBean.getIsSchedule());
		isCampaign = CommonUtils.checkBool(rateArubaReqBean.getIsCampaign());
		isPeriodic = CommonUtils.checkBool(rateArubaReqBean.getIsPeriodic());

		mobileNumber = CommonUtils.checkString(rateArubaReqBean.getMobileNumber());
		macAddress = CommonUtils.checkString(rateArubaReqBean.getMacAddress()).toUpperCase();
		duration = CommonUtils.checkInteger(rateArubaReqBean.getDuration());

		adList = rateArubaReqBean.getAdList();

		rateArubaResBean = new RateArubaResBean();
		rateArubaResBean.setMacAddress(macAddress);

		userProfile = this.userProfileDao.findOne(macAddress);
		if (adList != null && !adList.isEmpty()) {

			for (RateArubaAdBean adBean : adList) {
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
			rateArubaResBean.setStatus(Constants.SUCCESS);
		} else {
			LOGGER.error("ERROR rateAds - user profile not found by mac address - [ macAddress : " + macAddress + " ] ");
			rateArubaResBean.setStatus(Constants.FAILURE);
			rateArubaResBean.setReason(Constants.GENERAL_ERROR);
		}

		// save radius duration
		if (!isActivity && !isCampaign && !isSchedule && !isPeriodic)
			saveRadiusDetails(mobileNumber, Constants.EMPTY, CommonUtils.checkString(duration));

		LOGGER.info("END rateAds - " + rateArubaResBean);
		return rateArubaResBean;
	}

	@Override
	public void saveRadiusDetails(String mobileNumber, String randomPin, String duration) {
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
