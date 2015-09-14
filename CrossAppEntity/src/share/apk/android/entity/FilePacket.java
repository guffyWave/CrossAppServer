package share.apk.android.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import share.apk.android.exceptions.NotNullException;

@Entity
public class FilePacket extends ApkSharePacket {
	@OneToOne
	ApkShareFile file;

	public ApkShareFile getFile() {
		return file;
	}

	public void setFile(ApkShareFile file) throws NotNullException {
		if (file != null) {
			this.file = file;
		} else {
			throw new NotNullException("APK File cannot be null");
		}
	}
}
