package share.apk.server.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import share.apk.server.exceptions.EmailIDException;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.PhoneNumberException;
import share.apk.server.validators.EmailValidator;
import share.apk.server.validators.PhoneNumberValidator;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "emailID", "gcmID" }))
public class ApkShareUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String emailID;
	String phoneNumber;
	String gcmID;
	// ---->>> userActivationStatus is pending for use
	boolean userActivationStatus;
	@OneToMany
	@JoinTable(name = "ApkShareUser_InBox")
	List<ApkSharePacket> inBoxPacketList = new ArrayList<ApkSharePacket>();
	@OneToMany
	@JoinTable(name = "ApkShareUser_OutBox")
	List<ApkSharePacket> outBoxPacketList = new ArrayList<ApkSharePacket>();
	@Embedded
	FacebookCredential facebookCredential;
	@Embedded
	GoolgePlusCredential goolgePlusCredential;
	@Embedded
	TwitterCredential twitterCredential;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) throws EmptyStringException,
			EmailIDException {
		if (!emailID.equals("")) {
			if (new EmailValidator().validate(emailID) == true) {
				this.emailID = emailID;
			} else {
				throw new EmailIDException("Invalid Email ID.");
			}
		} else {
			throw new EmptyStringException("EmailID cannot be empty");
		}
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws EmptyStringException,
			PhoneNumberException {
		if (!phoneNumber.equals("")) {
			if (PhoneNumberValidator.validatePhoneNumber(phoneNumber) == true) {
				this.phoneNumber = phoneNumber;
			} else {
				throw new PhoneNumberException("Invalid Phone Number");
			}
		} else {
			throw new EmptyStringException("Phone Number cannot be empty");
		}
	}

	public String getGcmID() {
		return gcmID;
	}

	public void setGcmID(String gcmID) throws EmptyStringException {
		if (!gcmID.equals("")) {
			this.gcmID = gcmID;
		} else {
			throw new EmptyStringException("GCM ID cannot be empty");
		}
	}

	public boolean isUserActivationStatus() {
		return userActivationStatus;
	}

	public void setUserActivationStatus(boolean userActivationStatus) {
		this.userActivationStatus = userActivationStatus;
	}

	public List<ApkSharePacket> getInBoxPacketList() {
		return inBoxPacketList;
	}

	public void setInBoxPacketList(List<ApkSharePacket> inBoxPacketList) {
		this.inBoxPacketList = inBoxPacketList;
	}

	public List<ApkSharePacket> getOutBoxPacketList() {
		return outBoxPacketList;
	}

	public void setOutBoxPacketList(List<ApkSharePacket> outBoxPacketList) {
		this.outBoxPacketList = outBoxPacketList;
	}

	public FacebookCredential getFacebookCredential() {
		return facebookCredential;
	}

	public void setFacebookCredential(FacebookCredential facebookCredential) {
		this.facebookCredential = facebookCredential;
	}

	public GoolgePlusCredential getGoolgePlusCredential() {
		return goolgePlusCredential;
	}

	public void setGoolgePlusCredential(
			GoolgePlusCredential goolgePlusCredential) {
		this.goolgePlusCredential = goolgePlusCredential;
	}

	public TwitterCredential getTwitterCredential() {
		return twitterCredential;
	}

	public void setTwitterCredential(TwitterCredential twitterCredential) {
		this.twitterCredential = twitterCredential;
	}

}
