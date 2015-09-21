package share.apk.server.dao;

import java.util.List;

import share.apk.server.dto.ApkShareUser;
import share.apk.server.exceptions.EmailIDException;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;
import share.apk.server.exceptions.PhoneNumberException;
import share.apk.server.exceptions.UserException;

public interface ApkShareUserDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// get a user by id
	public ApkShareUser getApkShareUser(long id) throws NoSuchIDException,
			UserException, NegativeValueException;

	// get a user by emailId
	public ApkShareUser getApkShareUser(String emailID)
			throws EmailIDException, EmptyStringException, UserException;

	// get a user by phoneNumber
	public ApkShareUser getApkShareUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, EmailIDException, UserException;

	// get a userlist by ids
	public List<ApkShareUser> getApkShareUsers(Long... ids)
			throws NegativeValueException, NoSuchIDException;

	// get a userlist by emailIds
	public List<ApkShareUser> getApkShareUsers(String... emailIDs)
			throws UserException;

	// get a userlist by phoneNumbers
	public List<ApkShareUser> getApkShareUsersByPhoneNumber(
			String... phoneNumber) throws EmptyStringException,
			PhoneNumberException;

	// add a user by emailID
	public boolean addUser(String emailID) throws EmptyStringException,
			EmailIDException;

	// add a user by phoneNumber
	public boolean addUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, PhoneNumberException;

	// delete user
	public boolean deleteUser(ApkShareUser apkShareUser) throws UserException;

	// update user for emailId
	public boolean updateUserEmailID(ApkShareUser apkShareUser, String emailID)
			throws EmptyStringException, EmailIDException, UserException;

	// update user for phoneNumber
	public boolean updateUserPhoneNumber(ApkShareUser apkShareUser,
			String phoneNumber) throws EmptyStringException,
			PhoneNumberException, UserException;

	// update user for gcmID
	public boolean updateUserGCMID(ApkShareUser apkShareUser, String gcmID)
			throws EmptyStringException, UserException;

	// // / add packet to inbox
	// public boolean addInBoxPacket(ApkShareUser apkShareUser,
	// ApkSharePacket apkSharePacket);
	//
	// // / add packet to outbox
	// public boolean addOutBoxPacket(ApkShareUser apkShareUser,
	// ApkSharePacket apkSharePacket);

}
