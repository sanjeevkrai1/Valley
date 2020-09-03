package com.valley.cws.service.api.impl.bng;

import java.io.IOException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import net.jradius.client.RadiusClient;
import net.jradius.client.RadiusClientTransport;
import net.jradius.client.UDPClientTransport;
import net.jradius.dictionary.Attr_UserName;
import net.jradius.dictionary.vsa_cisco.Attr_CiscoAVPair;
import net.jradius.dictionary.vsa_cisco.Attr_CiscoAccountInfo;
import net.jradius.dictionary.vsa_cisco.Attr_CiscoSubscriberPassword;
import net.jradius.exception.RadiusException;
import net.jradius.packet.CoAACK;
import net.jradius.packet.CoARequest;
import net.jradius.packet.PacketFactory;
import net.jradius.packet.RadiusPacket;
import net.jradius.packet.RadiusRequest;
import net.jradius.packet.attribute.AttributeList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.valley.cws.utils.CommonUtils;
import com.valley.cws.utils.RadiusUtils;

@Service
public class RadiusAuthenticateService {
	private static final Logger LOGGER = Logger.getLogger(RadiusAuthenticateService.class);
	private static final String SHARED_SECRET = "shared_secret";
	private static final String REMOTE_ADDRESS = "remote_address";
	private static final String AUTH_PORT = "auth_port";
	private static final String ACCT_PORT = "acct_port";
	private static final String SOCKET_TIMEOUT = "socket_timeout";
	private static final String CISCO_ACNT_INFO_BEFORE = "cisco_acnt_info_before";
	private static final String CISCO_ACNT_INFO_AFTER = "cisco_acnt_info_after";
	private static final String CISCO_AV_PAIR = "cisco_av_pair";

	public Boolean isAuthenticated(String username, String password, String ipAddress) {
		LOGGER.info("START isAuthenticated - [ username : " + username + " ] , [ password : " + password
				+ " ] , [ ipAddress : " + ipAddress + " ] ");
		Boolean flag = false;
		RadiusClientTransport transport = null;
		RadiusClient radiusClient;
		RadiusRequest request;
		String sharedSecret;
		String remoteAddress;
		String authPort;
		String acctPort;
		String socketTimout;
		String ciscoAcntInfo;
		String ciscoAvPair;
		// String encryptPass;
		AttributeList[] acctAttributes = { new AttributeList(), new AttributeList(), new AttributeList(),
				new AttributeList() };

		sharedSecret = CommonUtils.propValue(SHARED_SECRET);
		remoteAddress = CommonUtils.propValue(REMOTE_ADDRESS);
		authPort = CommonUtils.propValue(AUTH_PORT);
		acctPort = CommonUtils.propValue(ACCT_PORT);
		socketTimout = CommonUtils.propValue(SOCKET_TIMEOUT);
		ciscoAcntInfo = CommonUtils.propValue(CISCO_ACNT_INFO_BEFORE) + ipAddress
				+ CommonUtils.propValue(CISCO_ACNT_INFO_AFTER);
		ciscoAvPair = CommonUtils.propValue(CISCO_AV_PAIR);

		LOGGER.info("isAuthenticated - property value - [ sharedSecret : " + sharedSecret + " ] , [ remoteAddress : "
				+ remoteAddress + " ] , [ authPort : " + authPort + " ] . [ acctPort : " + acctPort
				+ " ] , [ socketTimeout : " + socketTimout + " ] , [ ciscoAcntInfo : " + ciscoAcntInfo
				+ " ] , [ ciscoAvPair : " + ciscoAvPair + " ] ");
		try {
			transport = new UDPClientTransport();
			transport.setSharedSecret(CommonUtils.checkString(sharedSecret));
			transport.setAuthPort(CommonUtils.checkInteger(authPort));
			transport.setAcctPort(CommonUtils.checkInteger(acctPort));
			transport.setSocketTimeout(CommonUtils.checkInteger(socketTimout));
			transport.setRemoteInetAddress(InetAddress.getByName(remoteAddress));
		} catch (IOException ex) {
			ex.printStackTrace();
			LOGGER.error("ERROR isAuthenticated - " + ex.getMessage());
			// return false;
		}
		radiusClient = new RadiusClient(transport);
		request = PacketFactory.newPacket(CoARequest.CODE, radiusClient, acctAttributes[3]);
		request.setIdentifier(getRandom());
		request.addAttribute(new Attr_UserName(username));
		request.addAttribute(new Attr_CiscoAccountInfo(ciscoAcntInfo));
		request.addAttribute(new Attr_CiscoAVPair(ciscoAvPair));
		try {
		/*	encryptPass = new String(RadiusUtils.encodePapPassword(MessageDigest.getInstance("MD5"), new String(
					password).getBytes(), RadiusUtils.makeRFC2865RequestAuthenticator(MessageDigest.getInstance("MD5"),
					sharedSecret), sharedSecret));
*/
			byte len = (byte) (password.length()&0xff);

			// Encode the length into a first byte of the password (required by util)

			byte[] lenPassword = new byte[1 + len];

			lenPassword[0] = (byte) (len);

			System.arraycopy(password.getBytes(), 0, lenPassword, 1,
			password.length());

			byte[] authenticator =
			RadiusUtils.makeRFC2865RequestAuthenticator(MessageDigest.getInstance("MD5"),
					sharedSecret);

			byte[] encryptedValue = RadiusUtils.encodePapPassword(MessageDigest.getInstance("MD5"),
			lenPassword, authenticator, sharedSecret);

			byte[] result = new byte[authenticator.length + encryptedValue.length ];

			System.arraycopy(authenticator, 0, result, 0, authenticator.length);

			System.arraycopy(encryptedValue, 0, result, authenticator.length,
			encryptedValue.length);
			
			request.addAttribute(new Attr_CiscoSubscriberPassword(result));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			LOGGER.error("ERROR isAuthenticated - " + ex.getMessage());
			// return false;
		}

		try {
			RadiusPacket reply = radiusClient.changeOfAuth((CoARequest) request, 0);
			if (reply instanceof CoAACK) {
				System.out.println("Request is successful");
				flag = true;
			} else {
				System.out.println("Request unsuccessful");
				flag = true;
			}
		} catch (RadiusException ex) {
			ex.printStackTrace();
			LOGGER.error("ERROR isAuthenticated - " + ex.getMessage());
			// return false;
			return true;
		}

		return flag;
	}

	public static int getRandom() {
		return new Random().nextInt(255);
	}

	public static void main(String[] args) {
		// System.out.println(CommonUtils.checkByte(convertHex(192)));
		/*
		 * for (Byte b : getByteArr("192.168.10.62")) { System.out.println(b); }
		 */

		System.out.println(new RadiusAuthenticateService().isAuthenticated("admin", "123456", ""));
	}
}
