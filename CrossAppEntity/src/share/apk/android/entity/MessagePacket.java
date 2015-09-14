package share.apk.android.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class MessagePacket extends ApkSharePacket {
	@OneToOne
	ApkShareMessage apkShareMessage;

	public ApkShareMessage getApkShareMessage() {
		return apkShareMessage;
	}

	public void setApkShareMessage(ApkShareMessage apkShareMessage) {
		this.apkShareMessage = apkShareMessage;
	}

}
