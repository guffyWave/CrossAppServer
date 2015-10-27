package share.apk.server.dto;

import java.util.HashSet;
import java.util.Set;

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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "Cross_App_User_Friends")
	private Set<User> friendsList = new HashSet<User>();
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "Cross_App_User_InBox")
	private Set<Packet> inBoxPacketList = new HashSet<Packet>();
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "Cross_App_User_OutBox")
	private Set<Packet> outBoxPacketList = new HashSet<Packet>();
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<SocialCredential> credentialsList = new HashSet<SocialCredential>();

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

	public Set<User> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(Set<User> friendsList) {
		this.friendsList = friendsList;
	}

	public Set<Packet> getInBoxPacketList() {
		return inBoxPacketList;
	}

	public void setInBoxPacketList(Set<Packet> inBoxPacketList) {
		this.inBoxPacketList = inBoxPacketList;
	}

	public Set<Packet> getOutBoxPacketList() {
		return outBoxPacketList;
	}

	public void setOutBoxPacketList(Set<Packet> outBoxPacketList) {
		this.outBoxPacketList = outBoxPacketList;
	}

	public Set<SocialCredential> getCredentialsList() {
		return credentialsList;
	}

	public void setCredentialsList(Set<SocialCredential> credentialsList) {
		this.credentialsList = credentialsList;
	}

}
