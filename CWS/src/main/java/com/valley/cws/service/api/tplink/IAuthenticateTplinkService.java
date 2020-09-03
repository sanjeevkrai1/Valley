package com.valley.cws.service.api.tplink;

import com.valley.cws.bean.api.tplink.LoginTplinkReqBean;
import com.valley.cws.bean.api.tplink.LoginTplinkResBean;
import com.valley.cws.bean.api.tplink.RateTplinkReqBean;
import com.valley.cws.bean.api.tplink.RateTplinkResBean;
import com.valley.cws.bean.api.tplink.RedirectTplinkReqBean;
import com.valley.cws.bean.api.tplink.RedirectTplinkResBean;
import com.valley.cws.bean.api.tplink.RegistrationTplinkReqBean;
import com.valley.cws.bean.api.tplink.RegistrationTplinkResBean;
import com.valley.cws.bean.api.tplink.ValidateOtpTplinkReqBean;
import com.valley.cws.bean.api.tplink.ValidateOtpTplinkResBean;



public interface IAuthenticateTplinkService {

	RedirectTplinkResBean validateMac(RedirectTplinkReqBean redirectTplinkReqBean);

	RegistrationTplinkResBean registration(RegistrationTplinkReqBean registrationTplinkReqBean);

	ValidateOtpTplinkResBean validateOtp(ValidateOtpTplinkReqBean validateOtpTplinkReqBean);

	LoginTplinkResBean login(LoginTplinkReqBean loginTplinkReqBean);

	RateTplinkResBean rateAds(RateTplinkReqBean rateTplinkReqBean);
}
