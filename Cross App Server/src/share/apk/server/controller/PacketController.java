package share.apk.server.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import share.apk.server.communications.GCMServer;
import share.apk.server.dao.FileDAO;
import share.apk.server.dao.PacketDAO;
import share.apk.server.dao.UserDAO;
import share.apk.server.dto.APKFile;
import share.apk.server.dto.File;
import share.apk.server.dto.FilePacket;
import share.apk.server.dto.Message;
import share.apk.server.dto.MessagePacket;
import share.apk.server.dto.PacketStatus;
import share.apk.server.dto.User;
import share.apk.server.exceptions.PacketException;
import share.apk.server.exceptions.UserException;
import share.apk.server.jsonResponse.MessagePacketResult;
import share.apk.server.jsonResponse.UserResult;
import share.apk.server.management.ServerResult;
import share.apk.service.GoogleCloudStorageService;

@Controller
public class PacketController {
	@Autowired
	UserDAO userDAO;
	@Autowired
	PacketDAO packetDAO;
	@Autowired
	FileDAO fileDAO;
	@Autowired
	GCMServer gcmServer;

	GoogleCloudStorageService gcss;

	private static final Logger logger = Logger
			.getLogger(PacketController.class.getName());

	@RequestMapping(value = "/sendMessage", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	private @ResponseBody
	Map<String, Object> createMessagePacket(
			@RequestParam("fromUserID") Long fromUserID,
			@RequestParam("toUserID") Long toUserID,
			@RequestParam("message") String message) throws Exception {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		// --------------->> Creating a User
		try {
			User fromUser = userDAO.getUser(fromUserID);
			User toUser = userDAO.getUser(toUserID);

			Message m = new Message();
			m.setMessage(message);

			MessagePacket messagePacket = new MessagePacket();
			messagePacket.setFromUser(fromUser);
			messagePacket.setToUser(toUser);
			messagePacket.setMessage(m);
			messagePacket.setStatus(PacketStatus.PENDING);

			fromUser.getOutBoxPacketList().add(messagePacket);
			toUser.getInBoxPacketList().add(messagePacket);

			packetDAO.storePacket(messagePacket);
			userDAO.updateUser(fromUser);
			userDAO.updateUser(toUser);

			List<String> gcmIdList = new ArrayList<>();
			gcmIdList.add(toUser.getGcmID());

			// ------->> Sending the message--------->>
			gcmServer.sendMessageToDevice(gcmIdList, fromUser.getDisplayName(),
					message, "");

			// /------->> Result
			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "Successfully stored message packet");
			// map.put("message", messagePacket);
		} catch (UserException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "User exception " + e.getMessage());
			e.printStackTrace();
		} catch (PacketException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Packet exception " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "/createFilePacket", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	private @ResponseBody
	Map<String, Object> createFilePacket(
			@RequestParam("fromUserID") Long fromUserID,
			@RequestParam("toUserID") Long toUserID,
			@RequestParam("pakageName") String packageName,
			@RequestParam("appVersionName") String appVersionName,
			@RequestParam("versionCode") Long versionCode,
			@RequestParam("tags") String tags,
			@RequestParam("timeStamp") String timeStamp,
			@RequestParam("file") MultipartFile multipartFile) throws Exception {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		try {
			if (!multipartFile.isEmpty()) {
				gcss = new GoogleCloudStorageService();
				gcss.init(multipartFile.getOriginalFilename(),
						multipartFile.getContentType());
				gcss.storeFile(multipartFile.getBytes());
				gcss.destroy();
				logger.info("You successfully uploaded file="
						+ multipartFile.getName());

				// /--->> creating user
				User fromUser = userDAO.getUser(fromUserID);
				User toUser = userDAO.getUser(toUserID);

				// /--->> creating file
				APKFile apkFile = new APKFile();
				apkFile.setFileName(multipartFile.getOriginalFilename());
				apkFile.setPackageName(packageName);
				apkFile.setAppVersionName(appVersionName);
				apkFile.setFileSize(multipartFile.getSize());
				apkFile.setFileURI("/some/blobstore/filelocation");
				apkFile.setVersionCode(1L);
				List<String> tagsList = new ArrayList<>();
				// /----->> preparing tag list
				for (String t : tags.split(",")) {
					tagsList.add(t);
				}
				apkFile.setTags(tagsList);

				// /--->> creating file packet
				FilePacket filePacket = new FilePacket();
				filePacket.setFromUser(fromUser);
				filePacket.setToUser(toUser);
				filePacket.setStatus(PacketStatus.PENDING);
				filePacket.setFile(apkFile);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				Date timeStampDate = sdf.parse(timeStamp);
				filePacket.setTimeStamp(timeStampDate);

				fromUser.getOutBoxPacketList().add(filePacket);
				toUser.getInBoxPacketList().add(filePacket);

				List<String> gcmIdList = new ArrayList<>();
				gcmIdList.add(toUser.getGcmID());

				// ------->> Sending the file message--------->>
				gcmServer.sendMessageToDevice(gcmIdList,
						fromUser.getDisplayName(), fromUser.getDisplayName()
								+ "has sent " + apkFile.getFileName(), "");

				fileDAO.addFile(apkFile);
				packetDAO.storePacket(filePacket);
				userDAO.updateUser(fromUser);
				userDAO.updateUser(toUser);
				// /------->> Result
				map.put("result", ServerResult.SUCCESS);
				map.put("messsage", "Successfully stored file packet");
				// map.put("message", messagePacket);
			}
		} catch (UserException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "User exception " + e.getMessage());
			e.printStackTrace();
		} catch (PacketException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Packet exception " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "/getMessage/{packetID}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	private @ResponseBody
	Map<String, Object> getMessage(@PathVariable("packetID") Long packetID)
			throws Exception {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		// --------------->> Creating a User
		try {
			MessagePacket packet = (MessagePacket) packetDAO
					.getPacket(packetID);
			User packetFromUser = packet.getFromUser();
			User packetToUser = packet.getToUser();

			MessagePacketResult messagePacketResult = new MessagePacketResult();
			messagePacketResult.setId(packet.getId());

			// ---->> setting up fromUser json response
			UserResult fromUserResult = new UserResult();
			fromUserResult.setId(packetFromUser.getId());
			fromUserResult.setDisplayName(packetFromUser.getDisplayName());
			fromUserResult.setDisplayPicFileURI(packetFromUser
					.getDisplayPicFileURI());
			fromUserResult.setEmailID(packetFromUser.getEmailID());
			fromUserResult.setGcmID(packetFromUser.getGcmID());
			fromUserResult.setPhoneNumber(packetFromUser.getPhoneNumber());
			fromUserResult.setUserActivationStatus(packetFromUser
					.isUserActivationStatus());
			messagePacketResult.setFromUserResult(fromUserResult);

			// ---->> setting up toUser json response
			UserResult toUserResult = new UserResult();
			toUserResult.setId(packetToUser.getId());
			toUserResult.setDisplayName(packetToUser.getDisplayName());
			toUserResult.setDisplayPicFileURI(packetToUser
					.getDisplayPicFileURI());
			toUserResult.setEmailID(packetToUser.getEmailID());
			toUserResult.setGcmID(packetToUser.getGcmID());
			toUserResult.setPhoneNumber(packetToUser.getPhoneNumber());
			toUserResult.setUserActivationStatus(packetToUser
					.isUserActivationStatus());
			messagePacketResult.setToUserResult(toUserResult);

			messagePacketResult.setPacketStatus(packet.getStatus());
			messagePacketResult.setTimeStamp(packet.getTimeStamp());
			messagePacketResult.setMessage(packet.getMessage());

			// /------->> Result
			map.put("result", ServerResult.SUCCESS);
			map.put("messsage", "Successfully returned packet");
			map.put("packet", messagePacketResult);
		} catch (PacketException e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Packet exception " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			map.put("result", ServerResult.EXCEPTION);
			map.put("messsage", "Exception " + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	// @RequestMapping(value = "/getUserInboxPackets/{userID}", produces =
	// MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	// private @ResponseBody
	// Map<String, Object> getUserInboxPackets(@PathVariable("userID") Long
	// packetID)
	// throws Exception {
	//
	// Map<String, Object> map = new LinkedHashMap<String, Object>();
	//
	// // --------------->> Creating a User
	// try {
	// MessagePacket packet = (MessagePacket) packetDAO
	// .getPacket(packetID);
	// User packetFromUser = packet.getFromUser();
	// User packetToUser = packet.getToUser();
	//
	//
	//
	//
	// MessagePacketResult messagePacketResult = new MessagePacketResult();
	// messagePacketResult.setId(packet.getId());
	//
	// // ---->> setting up fromUser json response
	// UserResult fromUserResult = new UserResult();
	// fromUserResult.setId(packetFromUser.getId());
	// fromUserResult.setDisplayName(packetFromUser.getDisplayName());
	// fromUserResult.setDisplayPicFileURI(packetFromUser
	// .getDisplayPicFileURI());
	// fromUserResult.setEmailID(packetFromUser.getEmailID());
	// fromUserResult.setGcmID(packetFromUser.getGcmID());
	// fromUserResult.setPhoneNumber(packetFromUser.getPhoneNumber());
	// fromUserResult.setUserActivationStatus(packetFromUser
	// .isUserActivationStatus());
	// messagePacketResult.setFromUserResult(fromUserResult);
	//
	// // ---->> setting up toUser json response
	// UserResult toUserResult = new UserResult();
	// toUserResult.setId(packetToUser.getId());
	// toUserResult.setDisplayName(packetToUser.getDisplayName());
	// toUserResult.setDisplayPicFileURI(packetToUser
	// .getDisplayPicFileURI());
	// toUserResult.setEmailID(packetToUser.getEmailID());
	// toUserResult.setGcmID(packetToUser.getGcmID());
	// toUserResult.setPhoneNumber(packetToUser.getPhoneNumber());
	// toUserResult.setUserActivationStatus(packetToUser
	// .isUserActivationStatus());
	// messagePacketResult.setToUserResult(toUserResult);
	//
	// messagePacketResult.setPacketStatus(packet.getStatus());
	// messagePacketResult.setTimeStamp(packet.getTimeStamp());
	// messagePacketResult.setMessage(packet.getMessage());
	//
	// // /------->> Result
	// map.put("result", ServerResult.SUCCESS);
	// map.put("messsage", "Successfully returned packet");
	// map.put("packet", messagePacketResult);
	// } catch (PacketException e) {
	// map.put("result", ServerResult.EXCEPTION);
	// map.put("messsage", "Packet exception " + e.getMessage());
	// e.printStackTrace();
	// } catch (Exception e) {
	// map.put("result", ServerResult.EXCEPTION);
	// map.put("messsage", "Exception " + e.getMessage());
	// e.printStackTrace();
	// }
	//
	// return map;
	// }
}
