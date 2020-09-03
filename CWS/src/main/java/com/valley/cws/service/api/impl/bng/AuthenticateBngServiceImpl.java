package com.valley.cws.service.api.impl.bng;

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
import com.valley.cws.bean.api.bng.AdBean;
import com.valley.cws.bean.api.bng.GrantBngReqBean;
import com.valley.cws.bean.api.bng.GrantBngResBean;
import com.valley.cws.bean.api.bng.LoginBngReqBean;
import com.valley.cws.bean.api.bng.LoginBngResBean;
import com.valley.cws.bean.api.bng.RateBngAdBean;
import com.valley.cws.bean.api.bng.RateBngReqBean;
import com.valley.cws.bean.api.bng.RateBngResBean;
import com.valley.cws.bean.api.bng.RedirectBngReqBean;
import com.valley.cws.bean.api.bng.RedirectBngResBean;
import com.valley.cws.bean.api.bng.RegistrationBngReqBean;
import com.valley.cws.bean.api.bng.RegistrationBngResBean;
import com.valley.cws.bean.api.bng.ValidateOtpBngReqBean;
import com.valley.cws.bean.api.bng.ValidateOtpBngResBean;
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
import com.valley.cws.service.api.bng.IAuthenticateBngService;
import com.valley.cws.utils.ApacheHttpClientPost;
import com.valley.cws.utils.CommonUtils;
import com.valley.cws.utils.SendSms;

@Service
@Transactional(value = "transactionManager")
public class AuthenticateBngServiceImpl implements IAuthenticateBngService {
	private static final Logger LOGGER = Logger.getLogger(AuthenticateBngServiceImpl.class);

	@Autowired
	AuthenticateDao userProfileDao;

	@Autowired
	ShowAdService showAdService;

	@Autowired
	IRadiusService radiusService;

	@Autowired
	RadiusAuthenticateService radiusAuthenticateService;

	@Override
	public RedirectBngResBean validateMac(RedirectBngReqBean redirectBngReqBean) {
		LOGGER.info("START validateMac - [ mac address : " + redirectBngReqBean.getMacAddress() + " ] ");
		RedirectBngResBean redirectBngResBean;
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

		macAddress = CommonUtils.checkString(redirectBngReqBean.getMacAddress()).toUpperCase();
		redirectBngResBean = new RedirectBngResBean();
		redirectBngResBean.setMacAddress(macAddress);

		if (CommonUtils.notEmpty(macAddress)) {
			redirectBngResBean.setStatus(Constants.SUCCESS);
			userProfile = userProfileDao.findOne(macAddress);
			if (userProfile != null) {
				LOGGER.info("validateMac - login case - mac found - [ mac address : "
						+ redirectBngReqBean.getMacAddress() + " ] , [ user id : " + userProfile.getId() + " ] ");
				redirectBngResBean.setAction(Constants.LOGIN);
			} else {
				LOGGER.info("validateMac - registration case - mac not found - [ mac address : "
						+ redirectBngReqBean.getMacAddress() + " ] ");
				redirectBngResBean.setAction(Constants.REGISTRATION);
			}

			// for getting ad
			showAdStatus = this.showAdService.getvalue(KeyConstants.FIRST_TIME_SHOW_AD);
			LOGGER.info("validateMac - getting first time ad - [ ad status : " + showAdStatus + " ] , [ mac address : "
					+ redirectBngReqBean.getMacAddress() + " ] ");

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
								+ redirectBngReqBean.getMacAddress() + " ] ");
					}
				}
			}

			if (CommonUtils.isEmpty(adUrl))
				redirectBngResBean.setIsAd(false);
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

				redirectBngResBean.setAdUrls(adUrls);
				redirectBngResBean.setScreenType(CommonUtils.checkInteger(screenType));
				redirectBngResBean.setIsAd(true);
			}

		} else {
			redirectBngResBean.setStatus(Constants.FAILURE);
			redirectBngResBean.setReason(Constants.EMPTY_MAC);
		}
		LOGGER.info("END validateMac - [ mac address : " + redirectBngReqBean.getMacAddress() + " ] , [ response : "
				+ redirectBngResBean + " ] ");
		return redirectBngResBean;
	}

	@Override
	public RegistrationBngResBean registration(RegistrationBngReqBean registrationBngReqBean) {
		LOGGER.info("START registration - " + registrationBngReqBean);
		RegistrationBngResBean registrationBngResBean = null;
		String macAddress;
		WcaUserProfile userProfile;
		Integer randomPin;
		String mobileNumber;
		String sessionTimeout;
		Date dob;

		registrationBngResBean = new RegistrationBngResBean();
		macAddress = CommonUtils.checkString(registrationBngReqBean.getMacAddress()).toUpperCase();
		registrationBngResBean.setMacAddress(macAddress);

		userProfile = userProfileDao.findOne(macAddress);
		if (userProfile == null) {
			userProfile = new WcaUserProfile();
			userProfile.setMacAddress(macAddress);
			randomPin = CommonUtils.getPin();
			mobileNumber = CommonUtils.checkString(registrationBngReqBean.getMobileNumber());

			userProfile.setUserName(CommonUtils.checkString(registrationBngReqBean.getUserName()));
			userProfile.setEmailId(CommonUtils.checkString(registrationBngReqBean.getEmailId()));
			userProfile.setMobileNo(CommonUtils.checkString(registrationBngReqBean.getMobileNumber()));
			userProfile.setGender(CommonUtils.checkString(registrationBngReqBean.getGender()));
			userProfile.setIpAddress(CommonUtils.checkString(registrationBngReqBean.getIp()));
			userProfile.setSsid(CommonUtils.checkString(registrationBngReqBean.getSsid()));
			userProfile.setLastOtp(CommonUtils.checkString(randomPin));
			dob = CommonUtils.getDob(registrationBngReqBean.getDob());
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
			registrationBngResBean.setStatus(Constants.SUCCESS);
			registrationBngResBean.setReason(Constants.SUCCESS);

		} else {
			LOGGER.error("ERROR registration - user already registered - [ macAddress : " + macAddress + " ] ");
			registrationBngResBean.setStatus(Constants.FAILURE);
			registrationBngResBean.setReason(Constants.MAC_FOUND);
		}

		LOGGER.info("END registration - " + registrationBngResBean);
		return registrationBngResBean;
	}

	@Override
	public ValidateOtpBngResBean validateOtp(ValidateOtpBngReqBean validateOtpBngReqBean) {
		LOGGER.info("START validateOtp - " + validateOtpBngReqBean);
		ValidateOtpBngResBean validateOtpBngResBean;
		String macAddress;
		Integer otp;
		String mobileNumber;
		WcaUserProfile userProfile;
		Date expiryDate;
		Integer duration;
		WcaLoginHistory loginHistory;

		validateOtpBngResBean = new ValidateOtpBngResBean();
		macAddress = CommonUtils.checkString(validateOtpBngReqBean.getMacAddress()).toUpperCase();
		mobileNumber = CommonUtils.checkString(validateOtpBngReqBean.getMobileNumber());
		otp = CommonUtils.checkInteger(validateOtpBngReqBean.getOtp());

		validateOtpBngResBean.setMacAddress(macAddress);

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
					validateOtpBngResBean.setStatus(Constants.SUCCESS);
					validateOtpBngResBean.setMobileNumber(mobileNumber);
					validateOtpBngResBean.setUserName(userProfile.getUserName());
					validateOtpBngResBean.setEmailId(userProfile.getEmailId());
					validateOtpBngResBean.setGender(userProfile.getGender());
					validateOtpBngResBean.setSsid(userProfile.getSsid());

				} else {
					LOGGER.error("ERROR validateOtp - opt mismatch - [ requested otp : " + otp + " ] , [ db otp : "
							+ userProfile.getLastOtp() + " ] , [ macAddress : " + macAddress + " ] ");
					validateOtpBngResBean.setStatus(Constants.FAILURE);
					validateOtpBngResBean.setReason(Constants.OTP_MISMATCH);
				}

			} else {
				LOGGER.error("ERROR validateOtp - macAddress not found in db - [ macAddress : " + macAddress + " ] ");
				validateOtpBngResBean.setStatus(Constants.FAILURE);
				validateOtpBngResBean.setReason(Constants.MAC_NOT_FOUND);

			}
		} else {
			LOGGER.error("ERROR validateOtp - macAddress empty - [ macAddress : " + macAddress + " ] ");
			validateOtpBngResBean.setStatus(Constants.FAILURE);
			validateOtpBngResBean.setReason(Constants.EMPTY_MAC);

		}
		LOGGER.info("END validateOtp - " + validateOtpBngResBean);
		return validateOtpBngResBean;
	}

	@Override
	public LoginBngResBean login(LoginBngReqBean loginBngReqBean) {
		LOGGER.info("START login - " + loginBngReqBean);
		LoginBngResBean loginBngResBean = null;
		String macAddress;
		WcaUserProfile userProfile;
		Integer randomPin;
		String mobileNumber;
		String sessionTimeout = null;
		Date updatedDate;
		Date currentDate;

		loginBngResBean = new LoginBngResBean();
		macAddress = CommonUtils.checkString(loginBngReqBean.getMacAddress()).toUpperCase();
		loginBngResBean.setMacAddress(macAddress);

		userProfile = userProfileDao.findOne(macAddress);
		if (userProfile != null) {
			randomPin = CommonUtils.getPin();
			mobileNumber = CommonUtils.checkString(loginBngReqBean.getMobileNumber());
			if (CommonUtils.isEmpty(mobileNumber))
				mobileNumber = CommonUtils.checkString(userProfile.getMobileNo());

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
			loginBngResBean.setStatus(Constants.SUCCESS);
			loginBngResBean.setMobileNumber(mobileNumber);

		} else {
			LOGGER.error("ERROR login - mac not found in db - [ macAddress : " + macAddress + " ] ");
			loginBngResBean.setStatus(Constants.FAILURE);
			loginBngResBean.setReason(Constants.MAC_NOT_FOUND);
		}

		LOGGER.info("END login - " + loginBngResBean);
		return loginBngResBean;
	}

	@Override
	public RateBngResBean rateAds(RateBngReqBean rateBngReqBean) {
		LOGGER.info("START rateAds - " + rateBngReqBean);
		RateBngResBean rateBngResBean;
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
		List<RateBngAdBean> adList;

		isActivity = CommonUtils.checkBool(rateBngReqBean.getIsActivity());
		isSchedule = CommonUtils.checkBool(rateBngReqBean.getIsSchedule());
		isCampaign = CommonUtils.checkBool(rateBngReqBean.getIsCampaign());
		isPeriodic = CommonUtils.checkBool(rateBngReqBean.getIsPeriodic());

		mobileNumber = CommonUtils.checkString(rateBngReqBean.getMobileNumber());
		macAddress = CommonUtils.checkString(rateBngReqBean.getMacAddress()).toUpperCase();
		duration = CommonUtils.checkInteger(rateBngReqBean.getDuration());

		adList = rateBngReqBean.getAdList();

		rateBngResBean = new RateBngResBean();
		rateBngResBean.setMacAddress(macAddress);

		userProfile = this.userProfileDao.findOne(macAddress);
		if (adList != null && !adList.isEmpty()) {

			for (RateBngAdBean adBean : adList) {
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
			adType = CommonUtils.checkString(adType);
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
			rateBngResBean.setStatus(Constants.SUCCESS);
		} else {
			LOGGER.error("ERROR rateAds - user profile not found by mac address - [ macAddress : " + macAddress + " ] ");
			rateBngResBean.setStatus(Constants.FAILURE);
			rateBngResBean.setReason(Constants.GENERAL_ERROR);
		}

		// save radius duration
		if (!isActivity && !isCampaign && !isSchedule && !isPeriodic)
			saveRadiusDetails(mobileNumber, Constants.EMPTY, CommonUtils.checkString(duration));

		LOGGER.info("END rateAds - " + rateBngResBean);
		return rateBngResBean;
	}

	@Override
	public GrantBngResBean grant(GrantBngReqBean grantBngReqBean) {
		LOGGER.info("START grant - " + grantBngReqBean);
		GrantBngResBean grantBngResBean;
		WcaUserProfile userProfile;
		String macAddress;
		Boolean flag;

		macAddress = CommonUtils.checkString(grantBngReqBean.getMacAddress());
		grantBngResBean = new GrantBngResBean();
		grantBngResBean.setMacAddress(macAddress);

		if (CommonUtils.notEmpty(macAddress)) {
			userProfile = userProfileDao.findOne(macAddress);
			if (userProfile != null) {
				// grant logic
				flag = radiusAuthenticateService.isAuthenticated(
						CommonUtils.checkString(grantBngReqBean.getUserName()),
						CommonUtils.checkString(grantBngReqBean.getPassword()),
						CommonUtils.checkString(userProfile.getIpAddress()));
				if (flag) {
					grantBngResBean.setStatus(Constants.SUCCESS);
					grantBngResBean.setReason(Constants.SUCCESS);
				} else {
					grantBngResBean.setStatus(Constants.FAILURE);
					grantBngResBean.setReason(Constants.AUTH_FAILED);
				}
			} else {
				grantBngResBean.setStatus(Constants.FAILURE);
				grantBngResBean.setReason(Constants.MAC_NOT_FOUND);
			}
		} else {
			grantBngResBean.setStatus(Constants.FAILURE);
			grantBngResBean.setReason(Constants.EMPTY_MAC);
		}

		return grantBngResBean;
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
