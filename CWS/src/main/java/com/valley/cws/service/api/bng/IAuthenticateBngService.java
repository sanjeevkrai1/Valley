package com.valley.cws.service.api.bng;

import com.valley.cws.bean.api.bng.GrantBngReqBean;
import com.valley.cws.bean.api.bng.GrantBngResBean;
import com.valley.cws.bean.api.bng.LoginBngReqBean;
import com.valley.cws.bean.api.bng.LoginBngResBean;
import com.valley.cws.bean.api.bng.RateBngReqBean;
import com.valley.cws.bean.api.bng.RateBngResBean;
import com.valley.cws.bean.api.bng.RedirectBngReqBean;
import com.valley.cws.bean.api.bng.RedirectBngResBean;
import com.valley.cws.bean.api.bng.RegistrationBngReqBean;
import com.valley.cws.bean.api.bng.RegistrationBngResBean;
import com.valley.cws.bean.api.bng.ValidateOtpBngReqBean;
import com.valley.cws.bean.api.bng.ValidateOtpBngResBean;

public interface IAuthenticateBngService {

	RedirectBngResBean validateMac(RedirectBngReqBean redirectBngReqBean);

	RegistrationBngResBean registration(RegistrationBngReqBean registrationBngReqBean);

	ValidateOtpBngResBean validateOtp(ValidateOtpBngReqBean validateOtpBngReqBean);

	LoginBngResBean login(LoginBngReqBean loginBngReqBean);

	RateBngResBean rateAds(RateBngReqBean rateBngReqBean);

	GrantBngResBean grant(GrantBngReqBean grantBngReqBean);
}
