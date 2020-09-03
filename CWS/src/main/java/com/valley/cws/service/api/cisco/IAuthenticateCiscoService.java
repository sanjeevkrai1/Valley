package com.valley.cws.service.api.cisco;

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



public interface IAuthenticateCiscoService {

	RedirectCiscoResBean validateMac(RedirectCiscoReqBean redirectCiscoReqBean);

	RegistrationCiscoResBean registration(RegistrationCiscoReqBean registrationCiscoReqBean);

	ValidateOtpCiscoResBean validateOtp(ValidateOtpCiscoReqBean validateOtpCiscoReqBean);

	LoginCiscoResBean login(LoginCiscoReqBean loginCiscoReqBean);

	RateCiscoResBean rateAds(RateCiscoReqBean rateCiscoReqBean);
}
