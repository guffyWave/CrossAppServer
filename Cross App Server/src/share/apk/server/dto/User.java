package share.apk.server.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import share.apk.server.exceptions.EmailIDException;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.PhoneNumberException;
import share.apk.server.validators.EmailValidator;
import share.apk.server.validators.PhoneNumberValidator;

@Entity
@Table(name = "Cross_App_User", uniqueConstraints = @UniqueConstraint(columnNames = {
		"emailID", "gcmID" }))
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String emailID;
	private String phoneNumber;
	private String gcmID;
	private String displayName;
	private String displayPicFileURI;
	// ---->>> userActivationStatus is pending for use
	private boolean userActivationStatus;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Cross_App_User_Friends")
	private List<User> friendsList = new ArrayList<User>();
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Cross_App_User_InBox")
	private List<Packet> inBoxPacketList = new ArrayList<Packet>();
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Cross_App_User_OutBox")
	private List<Packet> outBoxPacketList = new ArrayList<Packet>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SocialCredential> credentialsList = new ArrayList<>();

	public long getId() {
		// System.out.println("getId is called");
		return id;
	}

	public void setId(long id) {
		// System.out.println("setId is called");
		this.id = id;
	}

	public String getEmailID() {
		// System.out.println("getEmailID is called");
		return emailID;
	}

	public void setEmailID(String emailID) throws EmptyStringException,
			EmailIDException {
		System.out.println("setEmailID is called");
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

	public List<SocialCredential> getCredentialsList() {
		System.out.println("getCredentialsList is called");
		return credentialsList;
	}

	public void setCredentialsList(List<SocialCredential> credentialsList) {
		System.out.println("setCredentialsList is called");
		this.credentialsList = credentialsList;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayPicFileURI() {
		return displayPicFileURI;
	}

	public void setDisplayPicFileURI(String displayPicFileURI) {
		this.displayPicFileURI = displayPicFileURI;
	}

	public List<User> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<User> friendsList) {
		this.friendsList = friendsList;
	}

	public List<Packet> getInBoxPacketList() {
		return inBoxPacketList;
	}

	public void setInBoxPacketList(List<Packet> inBoxPacketList) {
		this.inBoxPacketList = inBoxPacketList;
	}

	public List<Packet> getOutBoxPacketList() {
		return outBoxPacketList;
	}

	public void setOutBoxPacketList(List<Packet> outBoxPacketList) {
		this.outBoxPacketList = outBoxPacketList;
	}

}
