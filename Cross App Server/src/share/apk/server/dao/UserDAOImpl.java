package share.apk.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import share.apk.server.dto.Packet;
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
public class UserDAOImpl implements UserDAO {

	List<String> errorMessages;
	SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.errorMessages = new ArrayList<>();
		this.sessionFactory = sessionFactory;
	}

	// @Transactional
	@Override
	public User getUser(long id) throws NoSuchIDException, UserException,
			NegativeValueException {
		if (id <= 0) {
			throw new NegativeValueException("Negative ID Supplied " + id);
		}
		// Session s = sessionFactory.getCurrentSession();
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		User asu = (User) s.get(User.class, id);
		// ------------>>>>asu.getCredentialsList().get(0);

		// asu.setInBoxPacketList(asu.getInBoxPacketList());
		s.getTransaction().commit();

		if (asu != null) {
			return asu;
		} else {
			throw new NoSuchIDException("No User with ID " + id);
		}
	}

	@Transactional
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
	@Transactional
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

	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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

	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
	public boolean updateUser(User User) throws UserException {
		if (User != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.update(User);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	@Transactional
	public boolean updateUserEmailID(User User, String emailID)
			throws EmptyStringException, EmailIDException, UserException {
		if (User != null) {
			if (emailID.equals("")) {
				throw new EmptyStringException("Email ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				User.setEmailID(emailID);
				s.update(User);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	@Transactional
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
	@Transactional
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

	/*
	 * @Override public boolean updateUserFacebookCredential(User User, String
	 * facebookID, String facebookOAuthAccessToken) throws EmptyStringException,
	 * UserException { if (User != null) { if (facebookID.equals("")) { throw
	 * new EmptyStringException("Facebook ID cannot be empty "); } else {
	 * Session s = sessionFactory.getCurrentSession(); //s.beginTransaction();
	 * User.getFacebookCredential().setFacebookID(facebookID);
	 * User.getFacebookCredential()
	 * .setFacebookOAuthAccessToken(facebookOAuthAccessToken); s.update(User);
	 * //s.getTransaction().commit(); return true; } } else { throw new
	 * UserException("User cannot be NULL"); } }
	 * 
	 * @Override public boolean updateUserGooglePlusCredential(User User, String
	 * goolgePlusID, String googlePlusOAuthAccessToken) throws
	 * EmptyStringException, UserException { if (User != null) { if
	 * (goolgePlusID.equals("")) { throw new EmptyStringException(
	 * "Google Plus ID cannot be empty "); } else { Session s =
	 * sessionFactory.getCurrentSession(); //s.beginTransaction();
	 * User.getGoolgePlusCredential().setGoolgePlusID( goolgePlusID);
	 * User.getGoolgePlusCredential() .setGoolgePlusOAuthAccessToken(
	 * googlePlusOAuthAccessToken); s.update(User);
	 * //s.getTransaction().commit(); return true; } } else { throw new
	 * UserException("User cannot be NULL"); } }
	 * 
	 * @Override public boolean updateUserTwitterCredential(User User, String
	 * twitterID, String twitterOAuthAccessToken) throws EmptyStringException,
	 * UserException { if (User != null) { if (twitterID.equals("")) { throw new
	 * EmptyStringException( "Google Plus ID cannot be empty "); } else {
	 * Session s = sessionFactory.getCurrentSession(); //s.beginTransaction();
	 * User.getTwitterCredential().setTwitterID(twitterID);
	 * User.getTwitterCredential().setTwitterOAuthAccessToken(
	 * twitterOAuthAccessToken); s.update(User); //s.getTransaction().commit();
	 * return true; } } else { throw new UserException("User cannot be NULL"); }
	 * }
	 */

}
