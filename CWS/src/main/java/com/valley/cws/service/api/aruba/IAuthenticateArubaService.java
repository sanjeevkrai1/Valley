package com.valley.cws.service.api.aruba;

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

public interface IAuthenticateArubaService {

	RedirectArubaResBean validateMac(RedirectArubaReqBean redirectArubaReqBean);

	RegistrationArubaResBean registration(RegistrationArubaReqBean registrationArubaReqBean);

	ValidateOtpArubaResBean validateOtp(ValidateOtpArubaReqBean validateOtpArubaReqBean);

	LoginArubaResBean login(LoginArubaReqBean loginArubaReqBean);

	RateArubaResBean rateAds(RateArubaReqBean rateArubaReqBean);

	void saveRadiusDetails(String mobileNumber, String randomPin, String duration);
}
