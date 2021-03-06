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
	public User getUser(long id) throws NoSuchIDException, UserException,
			NegativeValueException;

	// get a user by emailId
	public User getUser(String emailID) throws EmailIDException,
			EmptyStringException, UserException;

	// gets a user by inbox
	public List<Packet> getUserInbox(long userID) throws NoSuchIDException,
			UserException, NegativeValueException;

	// get a user by phoneNumber
	public User getUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, EmailIDException, UserException;

	// get a userlist by ids
	public List<User> getUsers(Long... ids) throws NegativeValueException,
			NoSuchIDException;

	// get a userlist by emailIds
	public List<User> getUsers(String... emailIDs) throws UserException,
			EmptyStringException, EmailIDException;

	// get a userlist by phoneNumbers
	public List<User> getUsersByPhoneNumber(String... phoneNumber)
			throws EmptyStringException, PhoneNumberException, UserException;

	public boolean addUser(User asu) throws UserException;

	// add a user by emailID
	public boolean addUser(String emailID) throws EmptyStringException,
			EmailIDException;

	// add a user by phoneNumber
	public boolean addUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, PhoneNumberException;

	// delete user
	public boolean deleteUser(User u) throws UserException;

	// update user for emailId
	public boolean updateUser(User u) throws UserException;

	// update user for emailId
	public boolean updateUserEmailID(User u, String emailID)
			throws EmptyStringException, EmailIDException, UserException;

	// update user for phoneNumber
	public boolean updateUserPhoneNumber(User u, String phoneNumber)
			throws EmptyStringException, PhoneNumberException, UserException;

	// update user for gcmID
	public boolean updateUserGCMID(User u, String gcmID)
			throws EmptyStringException, UserException;

	// update user for Display Name
	public boolean updateUserDisplayName(User u, String displayName)
			throws EmptyStringException, UserException;

	// update user for Display Pic
	public boolean updateUserDisplayPic(User u, String displayPicFileURI)
			throws EmptyStringException, UserException;

	// update user for Display Pic
	public boolean addFriend(User u, User friend) throws UserException;

	// update user facebook credential public boolean
	public boolean updateUserFacebookCredential(User u, String facebookID,
			String facebookOAuthAccessToken) throws EmptyStringException,
			UserException;

	// update user googleplus credential public boolean
	public boolean updateUserGooglePlusCredential(User u, String goolgePlusID,
			String googlePlusOAuthAccessToken) throws EmptyStringException,
			UserException;

	// update user twitter credential public boolean
	public boolean updateUserTwitterCredential(User u, String twitterID,
			String twitterOAuthAccessToken) throws EmptyStringException,
			UserException;

//	// / add packet to inbox
//	public boolean addInBoxPacket(User user, Packet packet)
//			throws UserException;
//
//	// / add packet to outbox
//	public boolean addOutBoxPacket(User user, Packet packet)
//			throws UserException;

}
