package share.apk.android.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ApkShareUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String emailID;
	String phoneNumber;
	String gcmID;
	boolean userActivationStatus;
	@OneToMany
	List<ApkSharePacket> sharePackets = new ArrayList<ApkSharePacket>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGcmID() {
		return gcmID;
	}

	public void setGcmID(String gcmID) {
		this.gcmID = gcmID;
	}

	public boolean isUserActivationStatus() {
		return userActivationStatus;
	}

	public void setUserActivationStatus(boolean userActivationStatus) {
		this.userActivationStatus = userActivationStatus;
	}

	public List<ApkSharePacket> getSharePackets() {
		return sharePackets;
	}

	public void setSharePackets(List<ApkSharePacket> sharePackets) {
		this.sharePackets = sharePackets;
	}

}
