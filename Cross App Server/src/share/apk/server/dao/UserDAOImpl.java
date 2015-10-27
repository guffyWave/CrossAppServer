package share.apk.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import share.apk.server.dto.FacebookCredential;
import share.apk.server.dto.GooglePlusCredential;
import share.apk.server.dto.Packet;
import share.apk.server.dto.SocialCredential;
import share.apk.server.dto.TwitterCredential;
import share.apk.server.dto.User;
import share.apk.server.exceptions.EmailIDException;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;
import share.apk.server.exceptions.PhoneNumberException;
import share.apk.server.exceptions.UserException;
import share.apk.server.management.DefaultValues;
import share.apk.server.validators.EmailValidator;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

	List<String> errorMessages;
	SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.errorMessages = new ArrayList<>();
		this.sessionFactory = sessionFactory;

	}

	@Override
	public User getUser(long id) throws NoSuchIDException, UserException,
			NegativeValueException {
		if (id <= 0) {
			throw new NegativeValueException("Negative ID Supplied " + id);
		}
		Session s = sessionFactory.getCurrentSession();
		// Session s = sessionFactory.openSession();
		// s.beginTransaction();
		User u = (User) s.get(User.class, id);

		// s.getTransaction().commit();

		if (u != null) {
			return u;
		} else {
			throw new NoSuchIDException("No User with ID " + id);
		}
	}

	@Override
	public List<Packet> getUserInbox(long userID) throws NoSuchIDException,
			UserException, NegativeValueException {
		// if (userID <= 0) {
		// throw new NegativeValueException("Negative ID Supplied " + userID);
		// }
		// Session s = sessionFactory.getCurrentSession();
		// // //s.beginTransaction();
		// User asu = (User) s.get(User.class, userID);
		// // //s.getTransaction().commit();
		// if (asu != null) {
		// return asu.getInBoxPacketList();
		// } else {
		// throw new NoSuchIDException("No User with ID " + userID);
		// }
		//
		return null;
	}

	@Override
	public User getUser(String emailID) throws EmailIDException,
			EmptyStringException, UserException {
		if (emailID.equals("")) {
			throw new EmptyStringException("Email ID cannot be empty ");
		} else {
			if (!new EmailValidator().validate(emailID)) {
				throw new EmptyStringException("Invalid Email ID " + emailID);
			} else {
				Session s = sessionFactory.getCurrentSession();
				// if (!s.isOpen()) {
				// s = sessionFactory.getCurrentSession()();
				// }
				// s.beginTransaction();
				Criteria criteria = s.createCriteria(User.class);
				criteria.add(Restrictions.eq("emailID", emailID));
				// s.getTransaction().commit();
				if (criteria.list().size() > 0) {
					return (User) criteria.list().get(0);
				} else {
					throw new UserException("No User with email ID " + emailID);
				}
			}
		}
	}

	@Override
	public User getUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, EmailIDException, UserException {
		if (phoneNumber.equals("")) {
			throw new EmptyStringException("Phone Number cannot be empty ");
		} else {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			Criteria criteria = s.createCriteria(User.class);
			criteria.add(Restrictions.eq("phoneNumber", phoneNumber));
			// s.getTransaction().commit();
			if (criteria.list().size() > 0) {
				return (User) criteria.list().get(0);
			} else {
				throw new UserException("No User with Phone Number "
						+ phoneNumber);
			}
		}
	}

	// /---------------------->> pending
	@Override
	public List<User> getUsers(Long... ids) throws NegativeValueException,
			NoSuchIDException {
		// check for IDs
		for (long id : ids) {
			if (id <= 0) {
				throw new NegativeValueException("Negative ID Supplied " + id);
			}
		}

		Session s = sessionFactory.getCurrentSession();
		// s.beginTransaction();
		Criteria criteria = s.createCriteria(User.class);
		criteria.add(Restrictions.in("id", ids));

		// s.getTransaction().commit();

		return criteria.list();
	}

	@Override
	public List<User> getUsers(String... emailIDs) throws UserException,
			EmptyStringException, EmailIDException {
		// check for email IDs
		for (String emailID : emailIDs) {
			if (emailID.equals("")) {
				throw new EmptyStringException("Empty Email ID Supplied "
						+ emailID);
			} else {
				if (!new EmailValidator().validate(emailID)) {
					throw new EmptyStringException("Invalid Email ID "
							+ emailID);
				}
			}
		}

		Session s = sessionFactory.getCurrentSession();
		// s.beginTransaction();
		Criteria criteria = s.createCriteria(User.class);
		criteria.add(Restrictions.in("emailID", emailIDs));

		// s.getTransaction().commit();
		return criteria.list();
	}

	@Override
	public List<User> getUsersByPhoneNumber(String... phoneNumbers)
			throws EmptyStringException, PhoneNumberException {
		// check for email IDs
		for (String phoneNumber : phoneNumbers) {
			if (phoneNumber.equals("")) {
				throw new EmptyStringException("Empty Email ID Supplied "
						+ phoneNumber);
			}
		}

		Session s = sessionFactory.getCurrentSession();
		// s.beginTransaction();
		Criteria criteria = s.createCriteria(User.class);
		criteria.add(Restrictions.in("phoneNumber", phoneNumbers));

		// s.getTransaction().commit();
		return criteria.list();
	}

	@Override
	public boolean addUser(User asu) throws UserException {
		if (asu == null) {
			throw new UserException("User cannot be NULL");
		} else {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.save(asu);
			// s.getTransaction().commit();
			return true;
		}
	}

	@Override
	public boolean addUser(String emailID) throws EmptyStringException,
			EmailIDException {
		if (emailID.equals("")) {
			throw new EmptyStringException("Email ID cannot be empty ");
		} else {
			if (!new EmailValidator().validate(emailID)) {
				throw new EmptyStringException("Invalid Email ID " + emailID);
			} else {
				Session s = sessionFactory.getCurrentSession();
				// if (!s.isOpen()) {
				// s = sessionFactory.getCurrentSession()();
				// }
				// s.beginTransaction();
				User asu = new User();
				asu.setEmailID(emailID);
				asu.setGcmID(DefaultValues.NOT_DEFINED.toString());
				asu.setUserActivationStatus(false);
				s.save(asu);
				// s.getTransaction().commit();
				return true;
			}
		}
	}

	@Override
	public boolean addUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, PhoneNumberException {
		if (phoneNumber.equals("")) {
			throw new EmptyStringException("Phone Number cannot be empty ");
		} else {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			User asu = new User();
			asu.setPhoneNumber(phoneNumber);
			asu.setGcmID("");
			asu.setUserActivationStatus(false);
			s.save(asu);
			// s.getTransaction().commit();
			return true;
		}
	}

	@Override
	public boolean deleteUser(User User) throws UserException {
		if (User != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.delete(User);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUser(User User) throws UserException {
		if (User != null) {
			Session s = sessionFactory.getCurrentSession();
			// Session s = sessionFactory.openSession();
			// s.beginTransaction();
			s.update(User);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserEmailID(User u, String emailID)
			throws EmptyStringException, EmailIDException, UserException {
		if (u != null) {
			if (emailID.equals("")) {
				throw new EmptyStringException("Email ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				u.setEmailID(emailID);
				s.update(u);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserPhoneNumber(User User, String phoneNumber)
			throws EmptyStringException, PhoneNumberException, UserException {
		if (User != null) {
			if (phoneNumber.equals("")) {
				throw new EmptyStringException("Phone Number cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				User.setPhoneNumber(phoneNumber);
				s.update(User);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserGCMID(User User, String gcmID)
			throws EmptyStringException, UserException {
		if (User != null) {
			if (gcmID.equals("")) {
				throw new EmptyStringException("GCM ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				User.setGcmID(gcmID);
				s.update(User);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public List<String> getErrorMessages() {
		return errorMessages;
	}

	@Override
	public boolean updateUserFacebookCredential(User u, String facebookID,
			String facebookOAuthAccessToken) throws EmptyStringException,
			UserException {
		if (u != null) {
			if (facebookID.equals("")) {
				throw new EmptyStringException("Facebook ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession(); // s.beginTransaction();
				boolean isFacebookCreadentialAvailable = false;
				for (SocialCredential sc : u.getCredentialsList()) {
					if (sc instanceof FacebookCredential) {
						u.getCredentialsList().remove(sc);
						((FacebookCredential) sc).setFacebookID(facebookID);
						((FacebookCredential) sc)
								.setFacebookOAuthAccessToken(facebookOAuthAccessToken);
						isFacebookCreadentialAvailable = true;
						u.getCredentialsList().add(sc);
						break;
					}
				}

				if (isFacebookCreadentialAvailable == false) {
					FacebookCredential fc = new FacebookCredential();
					fc.setFacebookID(facebookID);
					fc.setFacebookOAuthAccessToken(facebookOAuthAccessToken);
					u.getCredentialsList().add(fc);
				}

				s.update(u);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserGooglePlusCredential(User u, String goolgePlusID,
			String googlePlusOAuthAccessToken) throws EmptyStringException,
			UserException {
		if (u != null) {
			if (goolgePlusID.equals("")) {
				throw new EmptyStringException(
						"Google Plus ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession(); // s.beginTransaction();
				boolean isGooglePlusCredentialAvailable = false;
				for (SocialCredential sc : u.getCredentialsList()) {
					if (sc instanceof GooglePlusCredential) {
						u.getCredentialsList().remove(sc);
						((GooglePlusCredential) sc)
								.setGoolgePlusID(goolgePlusID);
						((GooglePlusCredential) sc)
								.setGoolgePlusOAuthAccessToken(googlePlusOAuthAccessToken);
						isGooglePlusCredentialAvailable = true;
						u.getCredentialsList().add(sc);
						break;
					}
				}

				if (isGooglePlusCredentialAvailable == false) {
					GooglePlusCredential gc = new GooglePlusCredential();
					gc.setGoolgePlusID(goolgePlusID);
					gc.setGoolgePlusOAuthAccessToken(googlePlusOAuthAccessToken);
					u.getCredentialsList().add(gc);
				}
				s.update(u);
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserTwitterCredential(User u, String twitterID,
			String twitterOAuthAccessToken) throws EmptyStringException,
			UserException {
		if (u != null) {
			if (twitterID.equals("")) {
				throw new EmptyStringException(
						"Google Plus ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession(); // s.beginTransaction();
				boolean isTwitterCredentialAvailable = false;
				for (SocialCredential sc : u.getCredentialsList()) {
					if (sc instanceof TwitterCredential) {
						u.getCredentialsList().remove(sc);
						((TwitterCredential) sc).setTwitterID(twitterID);
						((TwitterCredential) sc)
								.setTwitterOAuthAccessToken(twitterOAuthAccessToken);
						isTwitterCredentialAvailable = true;
						u.getCredentialsList().add(sc);
						break;
					}
				}
				if (isTwitterCredentialAvailable == false) {
					TwitterCredential tc = new TwitterCredential();
					tc.setTwitterID(twitterID);
					tc.setTwitterOAuthAccessToken(twitterOAuthAccessToken);
					u.getCredentialsList().add(tc);
				}
				s.update(u);
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserDisplayName(User u, String displayName)
			throws EmptyStringException, UserException {
		if (u != null) {
			if (displayName.equals("")) {
				throw new EmptyStringException("Display name cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				u.setDisplayName(displayName);
				s.update(u);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserDisplayPic(User u, String displayPicFileURI)
			throws EmptyStringException, UserException {
		if (u != null) {
			if (displayPicFileURI.equals("")) {
				throw new EmptyStringException(
						"Display Pic file URI cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				u.setDisplayPicFileURI(displayPicFileURI);
				s.update(u);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean addFriend(User u, User friend) throws UserException {
		if (u == null || friend == null) {
			throw new UserException("User or Friend cannot be NULL");
		} else {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			u.getFriendsList().add(friend);
			friend.getFriendsList().add(u);
			s.update(u);
			s.update(friend);
			// s.getTransaction().commit();
			return true;
		}
	}

	// @Override
	// public boolean addInBoxPacket(User u, Packet packet) throws UserException
	// {
	// if (u == null || packet == null) {
	// throw new UserException("User or Packet cannot be NULL");
	// } else {
	// Session s = sessionFactory.getCurrentSession();
	// // s.beginTransaction();
	// u.getInBoxPacketList().add(packet);
	// s.update(u);
	// // s.getTransaction().commit();
	// return true;
	// }
	// }
	//
	// @Override
	// public boolean addOutBoxPacket(User u, Packet packet) throws
	// UserException {
	// if (u == null || packet == null) {
	// throw new UserException("User or Packet cannot be NULL");
	// } else {
	// Session s = sessionFactory.getCurrentSession();
	// // s.beginTransaction();
	// u.getOutBoxPacketList().add(packet);
	// s.update(u);
	// // s.getTransaction().commit();
	// return true;
	// }
	// }

}
