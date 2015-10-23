package share.apk.server.dao;

import java.util.List;

import org.hibernate.Session;

import share.apk.server.dto.Packet;
import share.apk.server.dto.User;
import share.apk.server.exceptions.EmailIDException;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;
import share.apk.server.exceptions.PhoneNumberException;
import share.apk.server.exceptions.UserException;

public interface UserDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// get a user by id
	public User getApkShareUser(long id) throws NoSuchIDException,
			UserException, NegativeValueException;

	// get a user by emailId
	public User getApkShareUser(String emailID)
			throws EmailIDException, EmptyStringException, UserException;

	// get a user by phoneNumber
	public User getApkShareUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, EmailIDException, UserException;

	// get a userlist by ids
	public List<User> getApkShareUsers(Long... ids)
			throws NegativeValueException, NoSuchIDException;

	// get a userlist by emailIds
	public List<User> getApkShareUsers(String... emailIDs)
			throws UserException, EmptyStringException, EmailIDException;

	// get a userlist by phoneNumbers
	public List<User> getApkShareUsersByPhoneNumber(
			String... phoneNumber) throws EmptyStringException,
			PhoneNumberException, UserException;

	public boolean addUser(User asu) throws UserException;

	// add a user by emailID
	public boolean addUser(String emailID) throws EmptyStringException,
			EmailIDException;

	// add a user by phoneNumber
	public boolean addUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, PhoneNumberException;

	// delete user
	public boolean deleteUser(User apkShareUser) throws UserException;

	// update user for emailId
	public boolean updateUser(User apkShareUser) throws UserException;

	// update user for emailId
	public boolean updateUserEmailID(User apkShareUser, String emailID)
			throws EmptyStringException, EmailIDException, UserException;

	// update user for phoneNumber
	public boolean updateUserPhoneNumber(User apkShareUser,
			String phoneNumber) throws EmptyStringException,
			PhoneNumberException, UserException;

	// update user for gcmID
	public boolean updateUserGCMID(User apkShareUser, String gcmID)
			throws EmptyStringException, UserException;

	/*
	 * // update user facebook credential public boolean
	 * updateUserFacebookCredential(ApkShareUser apkShareUser, String
	 * facebookID, String facebookOAuthAccessToken) throws EmptyStringException,
	 * UserException;
	 * 
	 * // update user googleplus credential public boolean
	 * updateUserGooglePlusCredential(ApkShareUser apkShareUser, String
	 * goolgePlusID, String googlePlusOAuthAccessToken) throws
	 * EmptyStringException, UserException;
	 * 
	 * // update user twitter credential public boolean
	 * updateUserTwitterCredential(ApkShareUser apkShareUser, String twitterID,
	 * String twitterOAuthAccessToken) throws EmptyStringException,
	 * UserException;
	 */

	// // / add packet to inbox
	// public boolean addInBoxPacket(ApkShareUser apkShareUser,
	// ApkSharePacket apkSharePacket);
	//
	// // / add packet to outbox
	// public boolean addOutBoxPacket(ApkShareUser apkShareUser,
	// ApkSharePacket apkSharePacket);

}
