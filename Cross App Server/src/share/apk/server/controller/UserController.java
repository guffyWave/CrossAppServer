package share.apk.server.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.PacketException;
import share.apk.server.exceptions.UserException;
import share.apk.server.jsonResponse.MessagePacketResult;
import share.apk.server.jsonResponse.UserResult;
import share.apk.server.management.FilePathConstants;
import share.apk.server.management.ServerResult;
import share.apk.service.GoogleCloudStorageService;

@Controller
public class UserController {

	@Autowired
	UserDAO userDAO;
	@Autowired
	PacketDAO packetDAO;
	@Autowired
	FileDAO fileDAO;

	GoogleCloudStorageService gcss;
	private static final Logger logger = Logger.getLogger(UserController.class
			.getName());

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
	Map<String, Object> createUser(
			@RequestParam(value = "emailID", required = true) String userMailID,
			@RequestParam(value = "gcmID", required = false) String gcmID,
			@RequestParam(value = "mobileNumber", required = false) String mobileNumber,
			@RequestParam(value = "displayName", required = true) String displayName,
			@RequestParam(value = "profilePic", required = false) MultipartFile multipartFile)
			throws Exception {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		// --------------->> Creating a User
		User u = null;
		try {

			u = new User();

			if (mobileNumber.equals("") && userMailID.equals("")) {
				throw new EmptyStringException(
						"At least one Mobile number or Email must be provided");
			}

			if (!userMailID.equals("")) {
				u.setEmailID(userMailID);
			} else {
				u.setEmailID("NOT_SET");
			}

			if (gcmID.equals("")) {
				u.setGcmID("NOT_SET");
			} else {
				u.setGcmID(gcmID);
			}

			if (!mobileNumber.equals("")) {
				u.setPhoneNumber(mobileNumber);
			} else {
				u.setPhoneNumber("000000000000");
			}

			if (!displayName.equals("")) {
				u.setDisplayName(displayName);
			} else {
				u.setDisplayName("NOT_SET");
			}

			FacebookCredential fb = new FacebookCredential();
			fb.setFacebookID("");
			fb.setFacebookOAuthAccessToken("");

			TwitterCredential tc = new TwitterCredential();
			tc.setTwitterID("");
			tc.setTwitterOAuthAccessToken("");

			GooglePlusCredential gpc = new GooglePlusCredential();
			gpc.setGoolgePlusID("");
			gpc.setGoolgePlusOAuthAccessToken("");

			u.getCredentialsList().add(fb);
			u.getCredentialsList().add(tc);
			u.getCredentialsList().add(gpc);

			userDAO.addUser(u);

			// /----------------->> Saving profile pic
			String profilePicPath = FilePathConstants.getInstance(u.getId()).PROFILE_PIC_PATH;
			if (!multipartFile.isEmpty()) {
				gcss = new GoogleCloudStorageService();
				gcss.init(
						profilePicPath + "/"
								+ multipartFile.getOriginalFilename(),
						multipartFile.getContentType());
				gcss.storeFile(multipartFile.getBytes());
				gcss.destroy();
				logger.info("You successfully uploaded file="
						+ multipartFile.getName());
			}

			u.setDisplayPicFileURI(profilePicPath + "/"
					+ multipartFile.getOriginalFilename());
			userDAO.updateUser(u);

			// /---------------------------->> Result
			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "Successfully created User");
			map.put("user", u);
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

	@RequestMapping(value = "/getFriends/{userID}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getFriends(@PathVariable("userID") Long userID) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		User u = null;
		try {
			u = userDAO.getUser(userID);

			List<UserResult> friendsList = new ArrayList<UserResult>();

			for (User f : u.getFriendsList()) {
				UserResult userResult = new UserResult();
				userResult.setId(f.getId());
				userResult.setDisplayName(f.getDisplayName());
				userResult.setDisplayPicFileURI(f.getDisplayPicFileURI() + "");
				userResult.setEmailID(f.getEmailID());
				userResult.setGcmID(f.getGcmID());
				userResult.setPhoneNumber(f.getPhoneNumber());
				userResult.setUserActivationStatus(f.isUserActivationStatus());

				friendsList.add(userResult);
			}

			// ---->> setting up fromUser json response

			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "Friends List Returned Successfully");
			map.put("friends", friendsList);
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

	@RequestMapping(value = "/setFriendship", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> setFriendship(
			@RequestParam(value = "oneUserID", required = true) Long oneUserID,
			@RequestParam(value = "secondUserID", required = true) Long secondUserID) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		User oneUser = null;
		User secondUser = null;

		try {
			oneUser = userDAO.getUser(oneUserID);
			secondUser = userDAO.getUser(secondUserID);

			// ---->> setting up fromUser json response
			oneUser.getFriendsList().add(secondUser);
			secondUser.getFriendsList().add(oneUser);

			userDAO.updateUser(oneUser);
			userDAO.updateUser(secondUser);

			map.put("result", ServerResult.SUCCESS);
			map.put("messsage",
					"Successfully established friendship between "
							+ oneUser.getDisplayName() + " and "
							+ secondUser.getDisplayName());
		} catch (LazyInitializationException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updateUser(
			@RequestParam(value = "userID", required = true) Long userID,
			@RequestParam(value = "emailID", required = false) String userMailID,
			@RequestParam(value = "gcmID", required = false) String gcmID,
			@RequestParam(value = "mobileNumber", required = false) String mobileNumber,
			@RequestParam(value = "displayName", required = false) String displayName,
			@RequestParam(value = "profilePic", required = false) MultipartFile multipartFile) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		User u = null;
		try {
			u = userDAO.getUser(userID);

			if (!userMailID.equals("")) {
				u.setEmailID(userMailID);
			}
			if (!gcmID.equals("")) {
				u.setGcmID(gcmID);
			}

			if (!mobileNumber.equals("")) {
				u.setPhoneNumber(mobileNumber);
			}

			if (!displayName.equals("")) {
				u.setDisplayName(displayName);
			}

			// /----------------->> Saving profile pic
			String profilePicPath = FilePathConstants.getInstance(u.getId()).PROFILE_PIC_PATH;
			if (!multipartFile.isEmpty()) {
				gcss = new GoogleCloudStorageService();
				gcss.init(
						profilePicPath + "/"
								+ multipartFile.getOriginalFilename(),
						multipartFile.getContentType());
				gcss.storeFile(multipartFile.getBytes());
				gcss.destroy();
				logger.info("You successfully uploaded file="
						+ multipartFile.getName());
			}

			u.setDisplayPicFileURI(profilePicPath + "/"
					+ multipartFile.getOriginalFilename());

			userDAO.updateUser(u);

			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "User successfully updated.");
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

	
	
}
