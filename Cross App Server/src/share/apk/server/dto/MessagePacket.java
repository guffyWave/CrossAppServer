package share.apk.server.dto;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import share.apk.server.exceptions.NotNullException;

@Entity
public class MessagePacket extends ApkSharePacket {
	@OneToOne
	Message apkShareMessage;

	public Message getApkShareMessage() {
		return apkShareMessage;
	}

	public void setApkShareMessage(Message apkShareMessage)
			throws NotNullException {
		if (apkShareMessage != null) {
			this.apkShareMessage = apkShareMessage;
		} else {
			throw new NotNullException("Message cannot be null");
		}
	}

}
