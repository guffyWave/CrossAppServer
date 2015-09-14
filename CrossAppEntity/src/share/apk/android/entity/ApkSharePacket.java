package share.apk.android.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public abstract class ApkSharePacket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	@OneToOne
	ApkShareUser fromUser;
	@OneToOne
	ApkShareUser toUser;
	@Enumerated(EnumType.STRING)
	ApkShareStatus fileStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ApkShareUser getFromUser() {
		return fromUser;
	}

	public void setFromUser(ApkShareUser fromUser) {
		this.fromUser = fromUser;
	}

	public ApkShareUser getToUser() {
		return toUser;
	}

	public void setToUser(ApkShareUser toUser) {
		this.toUser = toUser;
	}

	public ApkShareStatus getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(ApkShareStatus fileStatus) {
		this.fileStatus = fileStatus;
	}

}
