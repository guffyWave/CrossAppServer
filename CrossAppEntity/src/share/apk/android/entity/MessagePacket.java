package share.apk.android.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import share.apk.android.exceptions.NotNullException;

@Entity
public class MessagePacket extends ApkSharePacket {
	@OneToOne
	ApkShareMessage apkShareMessage;

	public ApkShareMessage getApkShareMessage() {
		return apkShareMessage;
	}

	public void setApkShareMessage(ApkShareMessage apkShareMessage)
			throws NotNullException {
		if (apkShareMessage != null) {
			this.apkShareMessage = apkShareMessage;
		} else {
			throw new NotNullException("Message cannot be null");
		}
	}

}
