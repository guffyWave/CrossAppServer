package share.apk.server.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import share.apk.server.dao.FileDAO;
import share.apk.server.dao.PacketDAO;
import share.apk.server.dao.UserDAO;
import share.apk.server.dto.FacebookCredential;
import share.apk.server.dto.GooglePlusCredential;
import share.apk.server.dto.Message;
import share.apk.server.dto.MessagePacket;
import share.apk.server.dto.Packet;
import share.apk.server.dto.PacketStatus;
import share.apk.server.dto.TwitterCredential;
import share.apk.server.dto.User;
import share.apk.server.exceptions.PacketException;
import share.apk.server.exceptions.UserException;
import share.apk.server.jsonResponse.MessagePacketResult;
import share.apk.server.jsonResponse.UserResult;
import share.apk.server.management.ServerResult;

@Controller
public class UserController {

	@Autowired
	UserDAO userDAO;
	@Autowired
	PacketDAO packetDAO;
	@Autowired
	FileDAO fileDAO;

	@RequestMapping(value = "/getUser/{userID}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getUser(@PathVariable("userID") Long userID) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		User u = null;
		try {
			u = userDAO.getUser(userID);

			// ---->> setting up fromUser json response
			UserResult userResult = new UserResult();
			userResult.setId(u.getId());
			userResult.setDisplayName(u.getDisplayName());
			userResult.setDisplayPicFileURI(u.getDisplayPicFileURI());
			userResult.setEmailID(u.getEmailID());
			userResult.setGcmID(u.getGcmID());
			userResult.setPhoneNumber(u.getPhoneNumber());
			userResult.setUserActivationStatus(u.isUserActivationStatus());

			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "User Returned Successfully");
			map.put("user", userResult);
		} catch (LazyInitializationException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			map.put("user", u);
			e.printStackTrace();
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	private @ResponseBody
	Map<String, Object> createUser(@RequestParam("emailID") String userMailID,
			@RequestParam("gcmID") String gcmID,
			@RequestParam("mobileNumber") String mobileNumber,
			@RequestParam("displayName") String displayName) throws Exception {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		// --------------->> Creating a User
		User asu = null;
		try {
			asu = new User();
			asu.setEmailID(userMailID);
			asu.setGcmID(gcmID);
			asu.setPhoneNumber(mobileNumber);
			asu.setDisplayName(displayName);

			FacebookCredential fb = new FacebookCredential();
			fb.setFacebookID("873328465");
			fb.setFacebookOAuthAccessToken("sdfhdfH9798772AKJHKUjjjs");

			TwitterCredential tc = new TwitterCredential();
			tc.setTwitterID("98758328237");
			tc.setTwitterOAuthAccessToken("kshfu328672cKJHHiqwr79235n");

			GooglePlusCredential gpc = new GooglePlusCredential();
			gpc.setGoolgePlusID("47584857");
			gpc.setGoolgePlusOAuthAccessToken("97JHS537254jhJHHhasif83452SJJ3");

			asu.getCredentialsList().add(fb);
			asu.getCredentialsList().add(tc);
			asu.getCredentialsList().add(gpc);

			userDAO.addUser(asu);
			// /------->> Result
			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "Successfully created User");
			map.put("user", asu);
		} catch (UserException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "User exception " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

}
