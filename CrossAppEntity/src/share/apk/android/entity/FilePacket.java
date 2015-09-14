package share.apk.android.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class FilePacket extends ApkSharePacket {
	@OneToOne
	ApkShareFile file;

	public ApkShareFile getFile() {
		return file;
	}

	public void setFile(ApkShareFile file) {
		this.file = file;
	}

}
