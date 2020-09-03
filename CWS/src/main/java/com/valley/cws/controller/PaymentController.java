package com.valley.cws.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paytm.merchant.CheckSumServiceHelper;
import com.valley.cws.bean.PolicyResBean;
import com.valley.cws.bean.api.aruba.LoginArubaReqBean;
import com.valley.cws.constants.PaytmConstants;
import com.valley.cws.entity.WcaBuyingHistory;
import com.valley.cws.entity.WcaUserProfile;
import com.valley.cws.entity.WcaWalletPlan;
import com.valley.cws.service.AuthenticateService;
import com.valley.cws.service.PaymentService;
import com.valley.cws.service.WalletService;
import com.valley.cws.utils.ApacheHttpClientPost;
import com.valley.cws.utils.CommonUtils;

@Controller
public class PaymentController {

	public static final Logger LOGGER = Logger.getLogger(PaymentController.class);

	@Autowired
	AuthenticateService service;

	@Autowired
	PaymentService paymentService;

	@Autowired
	WalletService walletService;

	@RequestMapping(value = "/home/buyPack", method = RequestMethod.POST)
	public @ResponseBody String buyPack(Model model,HttpSession session, @RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "TXN_AMOUNT", required = false) String txnAmt,
			@RequestParam(value = "grantTime", required = false) String grantTime,
			@RequestParam(value = "planId", required = false) String planId,
			@RequestParam(value = "ORDER_ID", required = false) String order_Id,
			@RequestParam(value = "CUST_ID", required = false) String customerId,
			@RequestParam(value = "industryId", required = false) String industryId,
			@RequestParam(value = "channel", required = false) String channel, HttpServletRequest request,
			LoginArubaReqBean loginArubaReqBean) {

		LOGGER.info("After select plan and Buy :: [ Rate in Rs ]  :: " + txnAmt + " , [ grantTime ]  :: " + grantTime
				+ " , [ order_Id ] :: " + order_Id + " , [ customerId ] :: " + customerId + " , [ industryId ] :: " + industryId
				+ " , [ channel ] :: " + channel + " , [ userId ] :: " + userId + " ,[Plan Id ] :: " + planId);

            session.setAttribute("loginArubaReqBean", loginArubaReqBean);
		
		WcaWalletPlan policyPack = this.walletService.findPlan(CommonUtils.checkLong(planId));

		WcaUserProfile userProfile = this.service.findById(CommonUtils.checkInteger(userId));

		WcaBuyingHistory buyingHistory = new WcaBuyingHistory();
		buyingHistory.setOrderId(order_Id);
		buyingHistory.setStatus("Pending");
		buyingHistory.setTxnDate(new Date());
		buyingHistory.setWcaUserProfile(userProfile);
		buyingHistory.setWcaWalletPlan(policyPack);
		buyingHistory.setCreatedOn(new Date());
		buyingHistory.setTxnInitiateTime(new Date());
		buyingHistory = this.paymentService.save(buyingHistory);

		TreeMap<String, String> parameters = new TreeMap<String, String>();
		StringBuilder outputHtml = new StringBuilder();

		parameters.put("TXN_AMOUNT", txnAmt);
		parameters.put("ORDER_ID", order_Id);
		parameters.put("CUST_ID", customerId);
		parameters.put("MID", PaytmConstants.MID);
		parameters.put("CHANNEL_ID", PaytmConstants.CHANNEL_ID);
		parameters.put("INDUSTRY_TYPE_ID", PaytmConstants.INDUSTRY_TYPE_ID);
		parameters.put("WEBSITE", PaytmConstants.WEBSITE);
		parameters.put("MOBILE_NO", "9876543210");
		parameters.put("EMAIL", "test@gmail.com");
		parameters.put("CALLBACK_URL", "http://localhost:8080/CWS/home/pgResponse");

		String checkSum;
		try {
			checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PaytmConstants.MERCHANT_KEY, parameters);

			System.out.println("checkSum " + checkSum);
			outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
			outputHtml.append("<html>");
			outputHtml.append("<head>");
			outputHtml.append("<title>Merchant Check Out Page</title>");
			outputHtml.append("</head>");
			outputHtml.append("<body>");
			outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
			outputHtml.append("<form method='post' action='" + PaytmConstants.PAYTM_URL + "' name='f1'>");
			outputHtml.append("<table border='1'>");
			outputHtml.append("<tbody>");

			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				outputHtml.append("<input type='hidden' name='" + key + "' value='" + value + "'>");
			}
			outputHtml.append("<input type='hidden' name='CHECKSUMHASH' value='" + checkSum + "'>");
			outputHtml.append("</tbody>");
			outputHtml.append("</table>");
			outputHtml.append("<script type='text/javascript'>");
			outputHtml.append("document.f1.submit();");
			outputHtml.append("</script>");
			outputHtml.append("</form>");
			outputHtml.append("</body>");
			outputHtml.append("</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(outputHtml.toString());
		return outputHtml.toString();
	}

	@RequestMapping(value = "/home/pgResponse", method = RequestMethod.POST)
	public String pgResponse(Model model, HttpServletRequest request,HttpSession session) {

		Enumeration<String> paramNames = request.getParameterNames();

		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		String paytmChecksum = "";
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if (paramName.equals("CHECKSUMHASH")) {
				paytmChecksum = mapData.get(paramName)[0];
			} else {
				parameters.put(paramName, mapData.get(paramName)[0]);
			}
		}
		boolean isValideChecksum = false;
		String outputHTML = "";

		String bankName = parameters.get("BANKNAME");
		String bankTxnId = parameters.get("BANKTXNID");
		String currency = parameters.get("CURRENCY");
		String gatewayName = parameters.get("GATEWAYNAME");
		String mId = parameters.get("MID");
		String orderId = parameters.get("ORDERID");
		String paymentMode = parameters.get("PAYMENTMODE");
		String respCode = parameters.get("RESPCODE");
		String respMsg = parameters.get("RESPMSG");
		String status = parameters.get("STATUS");
		String txnAmount = parameters.get("TXNAMOUNT");
		String txnDate = parameters.get("TXNDATE");
		String txnId = parameters.get("TXNID");

		LOGGER.info("Responce Detail :: [ BANKNAME ]  :: " + bankName + " , [ BANKTXNID ]  :: " + bankTxnId + " , [ CURRENCY ] :: "
				+ currency + " , [ GATEWAYNAME ] :: " + gatewayName + " , [ MID ] :: " + mId + " , [ ORDERID ] :: " + orderId
				+ " , [ PAYMENTMODE ] :: " + paymentMode + " , [ RESPCODE ]  :: " + respCode + " , [ RESPMSG ] :: " + respMsg
				+ " , [ STATUS ] :: " + status + " , [ TXNAMOUNT ] :: " + txnAmount + " , [ TXNDATE ] :: " + txnDate + " , [ TXNID ] :: "
				+ txnId);

		WcaBuyingHistory buyingHistory = this.paymentService.findOne(orderId);

		WcaWalletPlan policyPack = buyingHistory.getWcaWalletPlan();
		WcaUserProfile userProfile = buyingHistory.getWcaUserProfile();

		String baseGrantUrl = userProfile.getBaseGrantUrl();
		String continueUrl = userProfile.getContinueUrl();
		Integer durationInSec = 60 * CommonUtils.checkInteger(policyPack.getGrantMinute());
		String GrantAccessURL = baseGrantUrl + "?continue_url=" + continueUrl + "&duration=" + durationInSec;

		buyingHistory.setBankName(bankName);
		buyingHistory.setBankTxnId(bankTxnId);
		buyingHistory.setCurrency(currency);
		buyingHistory.setGatewayName(gatewayName);
		buyingHistory.setmId(mId);
		buyingHistory.setPaymentMode(paymentMode);
		buyingHistory.setResponseCode(respCode);
		buyingHistory.setResponseMsg(respMsg);
		buyingHistory.setStatus(status);
		buyingHistory.setTxnAmount(CommonUtils.checkDouble(txnAmount));
		buyingHistory.setTxnId(txnId);
		buyingHistory.setTxnCompletionTime(new Date());
		buyingHistory = this.paymentService.update(buyingHistory);
		
		LoginArubaReqBean loginArubaReqBean=(LoginArubaReqBean) session.getAttribute("loginArubaReqBean");

		try {
			isValideChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(PaytmConstants.MERCHANT_KEY, parameters,
					paytmChecksum);
			if (isValideChecksum && parameters.containsKey("RESPCODE")) {
				if (parameters.get("RESPCODE").equals("01")) {

					System.out.println("Inside Session " + buyingHistory.getWcaUserProfile() + " and Duration "
							+ policyPack.getGrantMinute());
					model.addAttribute("GrantAccessURL", GrantAccessURL);
					
					model.addAttribute("macAddress", loginArubaReqBean.getMacAddress());
					model.addAttribute("ssid", loginArubaReqBean.getSsid());
					model.addAttribute("clientIp", loginArubaReqBean.getClientIp());
					model.addAttribute("apname", loginArubaReqBean.getApName());
					model.addAttribute("vcName", loginArubaReqBean.getVcName());
					model.addAttribute("switchIp", loginArubaReqBean.getSwitchIp());
					model.addAttribute("url", loginArubaReqBean.getUrl());
					model.addAttribute("cmd", "authenticate");
					
					return "loginAruba";

					// outputHTML = parameters.toString();
				} else {

					if (userProfile != null) {

						Date updatedDate = userProfile.getUpdatedOn();
						Date currentDate = new Date();
						Integer compareValue = CommonUtils.CompareDate(updatedDate, currentDate);

						if (compareValue == 0) {
							PolicyResBean resBean = ApacheHttpClientPost.getAd(userProfile.getMacAddress());
							System.out.println("Inside here");
							System.out.println(resBean);
							model.addAttribute("adBean", resBean);
							model.addAttribute("userId", userProfile.getId());
							model.addAttribute("baseGrantUrl", CommonUtils.checkString(userProfile.getBaseGrantUrl()));
							return "adAndPgPage";
						}
						// outputHTML="<b>Payment Failed.</b>";
					}
				}
			} else {
				outputHTML = "<b>Checksum mismatched.</b>";
			}
		} catch (Exception e) {
			outputHTML = e.toString();
		}
		return outputHTML.toString();
	}

}
