package share.apk.server.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import share.apk.server.dao.ApkShareUserDAO;
import share.apk.server.dto.ApkShareUser;
import share.apk.server.dto.FacebookCredential;
import share.apk.server.dto.GooglePlusCredential;
import share.apk.server.dto.TwitterCredential;
import share.apk.server.exceptions.UserException;
import share.apk.server.management.ServerResult;

@Controller
public class DummyController {

	@Autowired
	ApkShareUserDAO apkShareUserDAO;

	@RequestMapping(value = "/dummyURL", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> doWork() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		ApkShareUser asu = null;
		try {
			apkShareUserDAO.addUser("sajid@gmail.com");
			asu = apkShareUserDAO.getApkShareUser("sajid@gmail.com");
			// /------>> Result
			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "successfully returned shop.");
			map.put("user", asu);
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			map.put("user", asu);
			e.printStackTrace();
		}

		return map;
	}

	private void createUser(Map map) throws Exception {

		// --------------->> Creating a User
		ApkShareUser asu = null;
		try {
			asu = new ApkShareUser();
			asu.setEmailID("guffy1267@gmail.com");
			asu.setGcmID("ASdfjgi4596fHIUHUU80sdf034udf3hs0HHsfdhdu343AS");
			asu.setPhoneNumber("+917042935653");

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

			apkShareUserDAO.addUser(asu);
			// /------>> Result
			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "successfully returned shop.");
			map.put("user", asu);
		} catch (UserException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "User excetpion " + e.getMessage());
			map.put("user", asu);
			e.printStackTrace();
		}

	}
}
