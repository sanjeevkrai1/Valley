package com.valley.cws.service.api.impl.tplink;

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
import com.valley.cws.bean.api.tplink.AdBean;
import com.valley.cws.bean.api.tplink.LoginTplinkReqBean;
import com.valley.cws.bean.api.tplink.LoginTplinkResBean;
import com.valley.cws.bean.api.tplink.RateTplinkAdBean;
import com.valley.cws.bean.api.tplink.RateTplinkReqBean;
import com.valley.cws.bean.api.tplink.RateTplinkResBean;
import com.valley.cws.bean.api.tplink.RedirectTplinkReqBean;
import com.valley.cws.bean.api.tplink.RedirectTplinkResBean;
import com.valley.cws.bean.api.tplink.RegistrationTplinkReqBean;
import com.valley.cws.bean.api.tplink.RegistrationTplinkResBean;
import com.valley.cws.bean.api.tplink.ValidateOtpTplinkReqBean;
import com.valley.cws.bean.api.tplink.ValidateOtpTplinkResBean;
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
import com.valley.cws.service.api.tplink.IAuthenticateTplinkService;
import com.valley.cws.utils.ApacheHttpClientPost;
import com.valley.cws.utils.CommonUtils;
import com.valley.cws.utils.SendSms;

@Service
@Transactional(value = "transactionManager")
public class AuthenticateTplinkServiceImpl implements IAuthenticateTplinkService {
	private static final Logger LOGGER = Logger.getLogger(AuthenticateTplinkServiceImpl.class);
	private static final String TP_URL_BEFORE = "tp_url_before";
	private static final String TP_URL_AFTER = "tp_url_after";

	@Autowired
	AuthenticateDao userProfileDao;

	@Autowired
	ShowAdService showAdService;

	@Autowired
	IRadiusService radiusService;

	@Override
	public RedirectTplinkResBean validateMac(RedirectTplinkReqBean redirectTplinkReqBean) {
		LOGGER.info("START validateMac - [ mac address : " + redirectTplinkReqBean.getMacAddress() + " ] ");
		RedirectTplinkResBean redirectTplinkResBean;
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

		macAddress = CommonUtils.checkString(redirectTplinkReqBean.getMacAddress()).toUpperCase();
		redirectTplinkResBean = new RedirectTplinkResBean();
		redirectTplinkResBean.setMacAddress(macAddress);

		if (CommonUtils.notEmpty(macAddress)) {
			redirectTplinkResBean.setStatus(Constants.SUCCESS);
			userProfile = userProfileDao.findOne(macAddress);
			if (userProfile != null) {
				LOGGER.info("validateMac - login case - mac found - [ mac address : " + redirectTplinkReqBean.getMacAddress()
						+ " ] , [ user id : " + userProfile.getId() + " ] ");
				redirectTplinkResBean.setAction(Constants.LOGIN);
			} else {
				LOGGER.info("validateMac - registration case - mac not found - [ mac address : " + redirectTplinkReqBean.getMacAddress()
						+ " ] ");
				redirectTplinkResBean.setAction(Constants.REGISTRATION);
			}

			// for getting ad
			showAdStatus = this.showAdService.getvalue(KeyConstants.FIRST_TIME_SHOW_AD);
			LOGGER.info("validateMac - getting first time ad - [ ad status : " + showAdStatus + " ] , [ mac address : "
					+ redirectTplinkReqBean.getMacAddress() + " ] ");

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
								+ redirectTplinkReqBean.getMacAddress() + " ] ");
					}
				}
			}

			if (CommonUtils.isEmpty(adUrl))
				redirectTplinkResBean.setIsAd(false);
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

				redirectTplinkResBean.setAdUrls(adUrls);
				redirectTplinkResBean.setScreenType(CommonUtils.checkInteger(screenType));
				redirectTplinkResBean.setIsAd(true);
			}

		} else {
			redirectTplinkResBean.setStatus(Constants.FAILURE);
			redirectTplinkResBean.setReason(Constants.EMPTY_MAC);
		}
		LOGGER.info("END validateMac - [ mac address : " + redirectTplinkReqBean.getMacAddress() + " ] , [ response : "
				+ redirectTplinkResBean + " ] ");
		return redirectTplinkResBean;
	}

	@Override
	public RegistrationTplinkResBean registration(RegistrationTplinkReqBean registrationTplinkReqBean) {
		LOGGER.info("START registration - " + registrationTplinkReqBean);
		RegistrationTplinkResBean registrationTplinkResBean = null;
		String macAddress;
		WcaUserProfile userProfile;
		Integer randomPin;
		String mobileNumber;
		String actionUrl;
		String sessionTimeout;
		Date dob;

		registrationTplinkResBean = new RegistrationTplinkResBean();
		macAddress = CommonUtils.checkString(registrationTplinkReqBean.getMacAddress()).toUpperCase();
		registrationTplinkResBean.setMacAddress(macAddress);

		userProfile = userProfileDao.findOne(macAddress);
		if (userProfile == null) {
			userProfile = new WcaUserProfile();
			userProfile.setMacAddress(macAddress);
			randomPin = CommonUtils.getPin();
			mobileNumber = CommonUtils.checkString(registrationTplinkReqBean.getMobileNumber());
			actionUrl = CommonUtils.propValue(TP_URL_BEFORE) + CommonUtils.checkString(registrationTplinkReqBean.getSwitchIp())
					+ CommonUtils.propValue(TP_URL_AFTER);

			userProfile.setUserName(CommonUtils.checkString(registrationTplinkReqBean.getUserName()));
			userProfile.setEmailId(CommonUtils.checkString(registrationTplinkReqBean.getEmailId()));
			userProfile.setMobileNo(CommonUtils.checkString(registrationTplinkReqBean.getMobileNumber()));
			userProfile.setGender(CommonUtils.checkString(registrationTplinkReqBean.getGender()));
			userProfile.setBaseGrantUrl(CommonUtils.checkString(actionUrl));
			userProfile.setSsid(CommonUtils.checkString(registrationTplinkReqBean.getSsid()));
			userProfile.setLastOtp(CommonUtils.checkString(randomPin));
			dob = CommonUtils.getDob(registrationTplinkReqBean.getDob());
			if (dob != null)
				userProfile.setDob(dob);

			userProfile.setCreatedOn(new Date());

			userProfileDao.save(userProfile);
			System.out.println("GENERATED OTP : " + randomPin);
			LOGGER.info("registration - record saved - sending OTP - [ otp : " + randomPin + " ] , [ mobile No. : " + mobileNumber
					+ " ] , [ macAddress : " + macAddress + " ] ");
			SendSms.sendSms(randomPin, mobileNumber);

			// save radius bean
			sessionTimeout = showAdService.getvalue(KeyConstants.FIRST_TIME_ACCESS);
			saveRadiusDetails(mobileNumber, CommonUtils.checkString(randomPin), sessionTimeout);

			// set response
			registrationTplinkResBean.setStatus(Constants.SUCCESS);
			registrationTplinkResBean.setActionUrl(actionUrl);
			registrationTplinkResBean.setUserName(mobileNumber);

		} else {
			LOGGER.error("ERROR registration - user already registered - [ macAddress : " + macAddress + " ] ");
			registrationTplinkResBean.setStatus(Constants.FAILURE);
			registrationTplinkResBean.setReason(Constants.MAC_FOUND);
		}

		LOGGER.info("END registration - " + registrationTplinkResBean);
		return registrationTplinkResBean;
	}

	@Override
	public ValidateOtpTplinkResBean validateOtp(ValidateOtpTplinkReqBean validateOtpTplinkReqBean) {
		LOGGER.info("START validateOtp - " + validateOtpTplinkReqBean);
		ValidateOtpTplinkResBean validateOtpTplinkResBean;
		String macAddress;
		Integer otp;
		String mobileNumber;
		WcaUserProfile userProfile;
		Date expiryDate;
		Integer duration;
		WcaLoginHistory loginHistory;

		validateOtpTplinkResBean = new ValidateOtpTplinkResBean();
		macAddress = CommonUtils.checkString(validateOtpTplinkReqBean.getMacAddress()).toUpperCase();
		mobileNumber = CommonUtils.checkString(validateOtpTplinkReqBean.getMobileNumber());
		otp = CommonUtils.checkInteger(validateOtpTplinkReqBean.getOtp());

		validateOtpTplinkResBean.setMacAddress(macAddress);

		if (CommonUtils.notEmpty(macAddress)) {
			userProfile = userProfileDao.findOne(macAddress);
			if (userProfile != null) {
				if (CommonUtils.checkInteger(userProfile.getLastOtp()).equals(otp)) {
					duration = radiusService.getDuration(mobileNumber);
					expiryDate = CommonUtils.addMinutes(new Date(), duration);

					LOGGER.info("validateOtp - save expiry dates - [ expiry date : " + expiryDate + " ] , [ duration : " + duration
							+ " ] , [ macAddress : " + macAddress + " ] ");
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
					validateOtpTplinkResBean.setStatus(Constants.SUCCESS);
					validateOtpTplinkResBean.setMobileNumber(mobileNumber);
					validateOtpTplinkResBean.setUserName(userProfile.getUserName());
					validateOtpTplinkResBean.setEmailId(userProfile.getEmailId());
					validateOtpTplinkResBean.setGender(userProfile.getGender());
					validateOtpTplinkResBean.setSsid(userProfile.getSsid());

				} else {
					LOGGER.error("ERROR validateOtp - opt mismatch - [ requested otp : " + otp + " ] , [ db otp : "
							+ userProfile.getLastOtp() + " ] , [ macAddress : " + macAddress + " ] ");
					validateOtpTplinkResBean.setStatus(Constants.FAILURE);
					validateOtpTplinkResBean.setReason(Constants.OTP_MISMATCH);
				}

			} else {
				LOGGER.error("ERROR validateOtp - macAddress not found in db - [ macAddress : " + macAddress + " ] ");
				validateOtpTplinkResBean.setStatus(Constants.FAILURE);
				validateOtpTplinkResBean.setReason(Constants.MAC_NOT_FOUND);

			}
		} else {
			LOGGER.error("ERROR validateOtp - macAddress empty - [ macAddress : " + macAddress + " ] ");
			validateOtpTplinkResBean.setStatus(Constants.FAILURE);
			validateOtpTplinkResBean.setReason(Constants.EMPTY_MAC);

		}
		LOGGER.info("END validateOtp - " + validateOtpTplinkResBean);
		return validateOtpTplinkResBean;
	}

	@Override
	public LoginTplinkResBean login(LoginTplinkReqBean loginTplinkReqBean) {
		LOGGER.info("START login - " + loginTplinkReqBean);
		LoginTplinkResBean loginTplinkResBean = null;
		String macAddress;
		WcaUserProfile userProfile;
		Integer randomPin;
		String mobileNumber;
		String sessionTimeout = null;
		String actionUrl;
		Date updatedDate;
		Date currentDate;

		loginTplinkResBean = new LoginTplinkResBean();
		macAddress = CommonUtils.checkString(loginTplinkReqBean.getMacAddress()).toUpperCase();
		loginTplinkResBean.setMacAddress(macAddress);

		userProfile = userProfileDao.findOne(macAddress);
		if (userProfile != null) {
			randomPin = CommonUtils.getPin();
			mobileNumber = CommonUtils.checkString(loginTplinkReqBean.getMobileNumber());
			if (CommonUtils.isEmpty(mobileNumber))
				mobileNumber = CommonUtils.checkString(userProfile.getMobileNo());
			actionUrl = CommonUtils.propValue(TP_URL_BEFORE) + CommonUtils.checkString(loginTplinkReqBean.getSwitchIp())
					+ CommonUtils.propValue(TP_URL_AFTER);

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
			userProfile.setSsid(CommonUtils.checkString(loginTplinkReqBean.getSsid()));
			userProfile.setLastOtp(CommonUtils.checkString(randomPin));

			userProfileDao.update(userProfile);
			System.out.println("GENERATED OTP : " + randomPin);
			LOGGER.info("login - record updated - sending OTP - [ otp : " + randomPin + " ] , [ mobile No. : " + mobileNumber
					+ " ] , [ macAddress : " + macAddress + " ] ");
			SendSms.sendSms(randomPin, mobileNumber);

			// save radius bean
			if (compareValue != 0) {
				sessionTimeout = showAdService.getvalue(KeyConstants.FIRST_TIME_ACCESS);
			}

			saveRadiusDetails(mobileNumber, CommonUtils.checkString(randomPin), sessionTimeout);

			// set response
			loginTplinkResBean.setStatus(Constants.SUCCESS);
			loginTplinkResBean.setActionUrl(actionUrl);
			loginTplinkResBean.setMobileNumber(mobileNumber);

		} else {
			LOGGER.error("ERROR login - mac not found in db - [ macAddress : " + macAddress + " ] ");
			loginTplinkResBean.setStatus(Constants.FAILURE);
			loginTplinkResBean.setReason(Constants.MAC_NOT_FOUND);
		}

		LOGGER.info("END login - " + loginTplinkResBean);
		return loginTplinkResBean;
	}

	@Override
	public RateTplinkResBean rateAds(RateTplinkReqBean rateTplinkReqBean) {
		LOGGER.info("START rateAds - " + rateTplinkReqBean);
		RateTplinkResBean rateTplinkResBean;
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
		List<RateTplinkAdBean> adList;

		isActivity = CommonUtils.checkBool(rateTplinkReqBean.getIsActivity());
		isSchedule = CommonUtils.checkBool(rateTplinkReqBean.getIsSchedule());
		isCampaign = CommonUtils.checkBool(rateTplinkReqBean.getIsCampaign());
		isPeriodic = CommonUtils.checkBool(rateTplinkReqBean.getIsPeriodic());

		mobileNumber = CommonUtils.checkString(rateTplinkReqBean.getMobileNumber());
		macAddress = CommonUtils.checkString(rateTplinkReqBean.getMacAddress()).toUpperCase();
		duration = CommonUtils.checkInteger(rateTplinkReqBean.getDuration());

		adList = rateTplinkReqBean.getAdList();

		rateTplinkResBean = new RateTplinkResBean();
		rateTplinkResBean.setMacAddress(macAddress);

		userProfile = this.userProfileDao.findOne(macAddress);
		if (adList != null && !adList.isEmpty()) {

			for (RateTplinkAdBean adBean : adList) {
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
			rateTplinkResBean.setStatus(Constants.SUCCESS);
		} else {
			LOGGER.error("ERROR rateAds - user profile not found by mac address - [ macAddress : " + macAddress + " ] ");
			rateTplinkResBean.setStatus(Constants.FAILURE);
			rateTplinkResBean.setReason(Constants.GENERAL_ERROR);
		}

		// save radius duration
		if (!isActivity && !isCampaign && !isSchedule && !isPeriodic)
			saveRadiusDetails(mobileNumber, Constants.EMPTY, CommonUtils.checkString(duration));

		LOGGER.info("END rateAds - " + rateTplinkResBean);
		return rateTplinkResBean;
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
