package share.apk.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import share.apk.server.dto.ApkShareUser;
import share.apk.server.exceptions.EmailIDException;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;
import share.apk.server.exceptions.PhoneNumberException;
import share.apk.server.exceptions.UserException;
import share.apk.server.validators.EmailValidator;

public class ApkShareUserDAOImpl implements ApkShareUserDAO {

	List<String> errorMessages;
	SessionFactory sessionFactory;

	public ApkShareUserDAOImpl(SessionFactory sessionFactory) {
		this.errorMessages = new ArrayList<>();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ApkShareUser getApkShareUser(long id) throws NoSuchIDException,
			UserException, NegativeValueException {
		if (id <= 0) {
			throw new NegativeValueException("Negative ID Supplied " + id);
		}
		Session s = sessionFactory.getCurrentSession();
		s.beginTransaction();
		ApkShareUser asu = (ApkShareUser) s.get(ApkShareUser.class, id);
		s.getTransaction().commit();
		if (asu != null) {
			return asu;
		} else {
			throw new NoSuchIDException("No User with ID " + id);
		}
	}

	@Override
	public ApkShareUser getApkShareUser(String emailID)
			throws EmailIDException, EmptyStringException, UserException {
		if (emailID.equals("")) {
			throw new EmptyStringException("Email ID cannot be empty ");
		} else {
			if (!new EmailValidator().validate(emailID)) {
				throw new EmptyStringException("Invalid Email ID " + emailID);
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				Criteria criteria = s.createCriteria(ApkShareUser.class);
				criteria.add(Restrictions.eq("emailID", emailID));
				s.getTransaction().commit();
				if (criteria.list().size() > 0) {
					return (ApkShareUser) criteria.list().get(0);
				} else {
					throw new UserException("No User with email ID " + emailID);
				}
			}
		}
	}

	@Override
	public ApkShareUser getApkShareUserByPhoneNumber(String phoneNumber)
			throws EmptyStringException, EmailIDException, UserException {
		if (phoneNumber.equals("")) {
			throw new EmptyStringException("Phone Number cannot be empty ");
		} else {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			Criteria criteria = s.createCriteria(ApkShareUser.class);
			criteria.add(Restrictions.eq("phoneNumber", phoneNumber));
			s.getTransaction().commit();
			if (criteria.list().size() > 0) {
				return (ApkShareUser) criteria.list().get(0);
			} else {
				throw new UserException("No User with Phone Number "
						+ phoneNumber);
			}
		}
	}

	// /---------------------->> pending
	@Override
	public List<ApkShareUser> getApkShareUsers(Long... ids)
			throws NegativeValueException, NoSuchIDException {
		// check for IDs
		for (long id : ids) {
			if (id <= 0) {
				throw new NegativeValueException("Negative ID Supplied " + id);
			}
		}

		Session s = sessionFactory.getCurrentSession();
		s.beginTransaction();
		Criteria criteria = s.createCriteria(ApkShareUser.class);
		criteria.add(Restrictions.in("id", ids));

		s.getTransaction().commit();

		return criteria.list();
	}

	@Override
	public List<ApkShareUser> getApkShareUsers(String... emailIDs)
			throws UserException, EmptyStringException, EmailIDException {
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
		s.beginTransaction();
		Criteria criteria = s.createCriteria(ApkShareUser.class);
		criteria.add(Restrictions.in("emailID", emailIDs));

		s.getTransaction().commit();

		return criteria.list();

	}

	@Override
	public List<ApkShareUser> getApkShareUsersByPhoneNumber(
			String... phoneNumbers) throws EmptyStringException,
			PhoneNumberException {
		// check for email IDs
		for (String phoneNumber : phoneNumbers) {
			if (phoneNumber.equals("")) {
				throw new EmptyStringException("Empty Email ID Supplied "
						+ phoneNumber);
			}
		}

		Session s = sessionFactory.getCurrentSession();
		s.beginTransaction();
		Criteria criteria = s.createCriteria(ApkShareUser.class);
		criteria.add(Restrictions.in("phoneNumber", phoneNumbers));

		s.getTransaction().commit();

		return criteria.list();
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
				s.beginTransaction();
				ApkShareUser asu = new ApkShareUser();
				asu.setEmailID(emailID);
				asu.setGcmID("");
				asu.setUserActivationStatus(false);
				s.save(asu);
				s.getTransaction().commit();
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
			s.beginTransaction();
			ApkShareUser asu = new ApkShareUser();
			asu.setPhoneNumber(phoneNumber);
			asu.setGcmID("");
			asu.setUserActivationStatus(false);
			s.save(asu);
			s.getTransaction().commit();
			return true;
		}
	}

	@Override
	public boolean deleteUser(ApkShareUser apkShareUser) throws UserException {
		if (apkShareUser != null) {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.delete(apkShareUser);
			s.getTransaction().commit();
			return true;
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserEmailID(ApkShareUser apkShareUser, String emailID)
			throws EmptyStringException, EmailIDException, UserException {
		if (apkShareUser != null) {
			if (emailID.equals("")) {
				throw new EmptyStringException("Email ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareUser.setEmailID(emailID);
				s.update(apkShareUser);
				s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserPhoneNumber(ApkShareUser apkShareUser,
			String phoneNumber) throws EmptyStringException,
			PhoneNumberException, UserException {
		if (apkShareUser != null) {
			if (phoneNumber.equals("")) {
				throw new EmptyStringException("Phone Number cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareUser.setPhoneNumber(phoneNumber);
				s.update(apkShareUser);
				s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserGCMID(ApkShareUser apkShareUser, String gcmID)
			throws EmptyStringException, UserException {
		if (apkShareUser != null) {
			if (gcmID.equals("")) {
				throw new EmptyStringException("GCM ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareUser.setGcmID(gcmID);
				s.update(apkShareUser);
				s.getTransaction().commit();
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
	public boolean updateUserFacebookCredential(ApkShareUser apkShareUser,
			String facebookID, String facebookOAuthAccessToken)
			throws EmptyStringException, UserException {
		if (apkShareUser != null) {
			if (facebookID.equals("")) {
				throw new EmptyStringException("Facebook ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareUser.getFacebookCredential().setFacebookID(facebookID);
				apkShareUser.getFacebookCredential()
						.setFacebookOAuthAccessToken(facebookOAuthAccessToken);
				s.update(apkShareUser);
				s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserGooglePlusCredential(ApkShareUser apkShareUser,
			String goolgePlusID, String googlePlusOAuthAccessToken)
			throws EmptyStringException, UserException {
		if (apkShareUser != null) {
			if (goolgePlusID.equals("")) {
				throw new EmptyStringException(
						"Google Plus ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareUser.getGoolgePlusCredential().setGoolgePlusID(
						goolgePlusID);
				apkShareUser.getGoolgePlusCredential()
						.setGoolgePlusOAuthAccessToken(
								googlePlusOAuthAccessToken);
				s.update(apkShareUser);
				s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

	@Override
	public boolean updateUserTwitterCredential(ApkShareUser apkShareUser,
			String twitterID, String twitterOAuthAccessToken)
			throws EmptyStringException, UserException {
		if (apkShareUser != null) {
			if (twitterID.equals("")) {
				throw new EmptyStringException(
						"Google Plus ID cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareUser.getTwitterCredential().setTwitterID(twitterID);
				apkShareUser.getTwitterCredential().setTwitterOAuthAccessToken(
						twitterOAuthAccessToken);
				s.update(apkShareUser);
				s.getTransaction().commit();
				return true;
			}
		} else {
			throw new UserException("User cannot be NULL");
		}
	}

}
