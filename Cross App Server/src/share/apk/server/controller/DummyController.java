package share.apk.server.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import share.apk.server.dao.UserDAO;
import share.apk.server.dto.User;
import share.apk.server.dto.FacebookCredential;
import share.apk.server.dto.GooglePlusCredential;
import share.apk.server.dto.TwitterCredential;
import share.apk.server.exceptions.UserException;
import share.apk.server.management.ServerResult;

@Controller
public class DummyController {

	@Autowired
	UserDAO userDAO;

	@RequestMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> doWork() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		User asu = null;
		try {

			// apkShareUserDAO.addUser("junaid@gmail.com");
			asu = userDAO.getUser(1L);

			// FacebookCredential fb = new FacebookCredential();
			// fb.setFacebookID("00000000");
			// fb.setFacebookOAuthAccessToken("XXXXXXXXXxxxxxxxxxxxXXXXXXXXXX");
			//
			// asu.getCredentialsList().add(fb);
			//
			// apkShareUserDAO.updateUser(asu);

			// /------>> Result
			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "User Returned Successfully  ");
			map.put("user", asu);
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			map.put("user", asu);
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	private @ResponseBody
	Map<String, Object> createUser(Map map) throws Exception {

		// --------------->> Creating a User
		User asu = null;
		try {
			asu = new User();
			asu.setEmailID("saba@gmail.com");
			asu.setGcmID("ASjhjkasfuh879830sdf034udf3hs0HHsfdhdu343AS");
			asu.setPhoneNumber("+917092935653");

			FacebookCredential fb = new FacebookCredential();
			fb.setFacebookID("873328465");
			fb.setFacebookOAuthAccessToken("sdfhdfH9798772AKJHKUjjjs");

			TwitterCredential tc = new TwitterCredential();
			tc.setTwitterID("98758328237");
			tc.setTwitterOAuthAccessToken("kshfu328672cKJHHiqwr79235n");

			GooglePlusCredential gpc = new GooglePlusCredential();
			gpc.setGoolgePlusID("47584857");
			gpc.setGoolgePlusOAuthAccessToken("97JHS537254jhJHHhasif83452SJJ3");

			asu.getCredentialsList().add(gpc);
			asu.getCredentialsList().add(tc);
			asu.getCredentialsList().add(fb);

			userDAO.addUser(asu);
			// /------->> Result
			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "successfully returned shop.");
			map.put("user", asu);
		} catch (UserException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "User exception " + e.getMessage());
			map.put("user", asu);
			e.printStackTrace();
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			map.put("user", asu);
			e.printStackTrace();
		}

		return map;

	}
}
