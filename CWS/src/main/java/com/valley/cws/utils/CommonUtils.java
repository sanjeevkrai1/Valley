package com.valley.cws.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import com.valley.cws.controller.ArubaAuthenticateController;

/**
 * Common util class for this application
 * 
 * @author akash.panday
 * 
 */
public class CommonUtils {

	public static final Logger LOGGER = Logger.getLogger(ArubaAuthenticateController.class);

	private CommonUtils() {

	}

	/**
	 * Gets the method name of currently executing method
	 * 
	 * @return
	 */

	public static Boolean isEmpty(Object obj) {
		return obj == null ? true : obj.toString().trim().equals("") ? true : false;
	}

	public static Boolean notEmpty(Object obj) {
		return obj == null ? false : obj.toString().trim().equals("") ? false : true;
	}

	public static String getMethodName() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];
		String methodName = e.getMethodName();
		return methodName;
	}

	public static String readClob(Clob clob) throws Exception {
		try {
			StringBuilder sb = new StringBuilder((int) clob.length());
			Reader r = clob.getCharacterStream();
			char[] cbuf = new char[2048];
			int n;
			while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
				sb.append(cbuf, 0, n);
			}

			String logStatus = sb.toString();
			logStatus = logStatus.replaceAll(",", "\n");
			return logStatus;
		} catch (Exception e) {
			return "";
		}

	}

	public static String checkString(Object obj) {
		try {
			return obj == null ? "" : obj.toString().trim();
		}

		catch (Exception e) {
			return "";
		}
	}

	public static Long checkLong(Object obj) {
		try {
			return Long.parseLong(obj.toString());
		} catch (Exception ex) {
			return null;
		}
	}

	public static Integer checkInteger(Object obj) {
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception ex) {
			try {
				return Integer.parseInt(CommonUtils.checkString(obj).split("\\.")[0]);
			} catch (Exception e) {
				return 0;
			}
		}
	}

	public static Byte checkByte(Object obj) {
		try {
			return Byte.parseByte(obj.toString());
		} catch (Exception ex) {
			try {
				return Byte.parseByte(CommonUtils.checkString(obj).split("\\.")[0]);
			} catch (Exception e) {
				return 0;
			}
		}
	}

	public static Boolean checkBool(Object obj) {
		if (obj == null)
			return false;
		else {
			if (checkString(obj).equalsIgnoreCase("true"))
				return true;
			else
				return false;
		}

	}

	public static Double checkDouble(Object obj) {
		try {
			return Double.parseDouble(obj.toString());
		} catch (Exception ex) {
			return 0.0;
		}
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static Integer CompareDate(Date UpdatedDate, Date CurrentDate) {
		try {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(UpdatedDate);
			c1.set(Calendar.HOUR, 0);
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			c1.set(Calendar.MILLISECOND, 0);

			Date d1 = c1.getTime();

			Calendar c2 = Calendar.getInstance();
			c2.setTime(CurrentDate);
			c2.set(Calendar.HOUR, 0);
			c2.set(Calendar.HOUR_OF_DAY, 0);
			c2.set(Calendar.MINUTE, 0);
			c2.set(Calendar.SECOND, 0);
			c2.set(Calendar.MILLISECOND, 0);

			Date d2 = c2.getTime();

			System.out.println(d1 + " , " + d2 + " , " + d1.compareTo(d2));
			return (d1.compareTo(d2));
		} catch (Exception ex) {
			return -1;
		}
	}

	public static Integer CompareDateTime(Date UpdatedDate, Date CurrentDate) {
		try {
			return (CurrentDate.compareTo(UpdatedDate));
		} catch (Exception ex) {
			return -1;
		}
	}

	public static Date subtractDay(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static String propValue(String key) {
		Properties prop = new Properties();
		InputStream input = null;
		String value = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("cws.properties");
			prop.load(input);
			value = prop.getProperty(key);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;

	}

	public static Integer sendURL(String url, String requestMethod) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod(requestMethod);

		int responseCode = con.getResponseCode();

		// LOGGER.info("Sending   request to URL : " + url);
		LOGGER.info("Response Codwwe : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("response body : " + response.toString());
		// print result
		return responseCode;
	}

	public static Integer sendURLHttps(String url, String requestMethod) throws Exception {

		URL obj = new URL(url);

		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		con.setRequestMethod(requestMethod);

		int responseCode = con.getResponseCode();

		// LOGGER.info("Sending   request to URL : " + url);
		LOGGER.info("Response Codwwe : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("response body : " + response.toString());
		// print result
		return responseCode;
	}

	public static Date getDate(String dateStr) {
		Date date = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static Integer getPin() {
		Random rand = new Random();
		Integer randomPIN = (rand.nextInt(900000) + 100000);
		return randomPIN;
	}

	public static Integer minToSec(Object obj) {
		if (checkInteger(obj) == 0)
			return 0;
		else
			return checkInteger(obj) * 60;
	}

	public static Integer secToMin(Object obj) {
		if (checkInteger(obj) == 0)
			return 0;
		else
			return checkInteger(obj) / 60;
	}

	public static Date addMinutes(Date date, Integer min) {
		long t = date.getTime();
		Date afterAddingMins = new Date(t + (CommonUtils.checkInteger(min) * 60000));
		return afterAddingMins;
	}

	public static Double decimalFormat(Double d) {
		Double formatD = 0d;
		try {
			DecimalFormat format = new DecimalFormat("##.#");
			formatD = checkDouble(format.format(d));
		} catch (Exception ex) {
			return d;
		}
		return formatD;
	}

	public static long ipToLong(InetAddress ip) {
		byte[] octets = ip.getAddress();
		long result = 0;
		for (byte octet : octets) {
			result <<= 8;
			result |= octet & 0xff;
		}
		return result;
	}

	public static Date getDob(Object obj) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		Date date = null;

		try {
			date = dateFormat.parse(checkString(obj));
		} catch (ParseException e) {
			System.out.println("ERROR : " + e.getMessage());
		}

		return date;
	}

	public static String convertDob(String dateOfBirth) {
		Date DOB = null;
		System.out.println("date of birth" + dateOfBirth);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			DOB = df.parse(dateOfBirth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
		String dob = df2.format(DOB);
		return dob;
	}

	public static void main(String... strings) {

		System.out.println(getDob("Oct 20, 2016"));
	}
}
