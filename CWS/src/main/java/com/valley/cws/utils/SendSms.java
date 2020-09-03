package com.valley.cws.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSms {
	private static final String IS_SMS_SEND = "is_sms_send";

	public static String sendSms(Integer randomPIN, String mobileNum) {
		try {
			// Construct data 66dfedfbe79ef1c7e626f35a0bbed5a4bc2fe9ac
			// 3e2edef480c126472c8355dcd451ebd964f5c520
			// 229efe283e94a561ab788d5432141d57412ce8c4 Sagar Sir
			// sagar.redhat@gmail.com
			// 9353ffaca1d8ed007af79a18b83c4ba32fc3f7a0 sandeep1874n@gmail.com
			// e686b0a131f0629fbd0cb114cbfee530dfc8b7d1
			// himanshukumar024@gmail.com
			if (!CommonUtils.checkInteger(CommonUtils.propValue(IS_SMS_SEND)).equals(0)) {
				String user = "username=" + "himanshukumar024@gmail.com";
				String hash = "&hash=" + "e686b0a131f0629fbd0cb114cbfee530dfc8b7d1";
				String message = "&message=" + "Your One Time Password for activating wifi is " + randomPIN;
				String sender = "&sender=" + "TXTLCL";
				String numbers = "&numbers=" + "91" + mobileNum;

				// Send data
				HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
				String data = user + hash + numbers + message + sender;
				System.out.println(data);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
				conn.getOutputStream().write(data.getBytes("UTF-8"));
				final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				final StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					stringBuffer.append(line);
				}
				rd.close();

				System.out.println("response : " + stringBuffer.toString());
				return stringBuffer.toString();
			}
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
		return null;

	}
}
