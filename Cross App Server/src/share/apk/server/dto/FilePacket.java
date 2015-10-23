package share.apk.server.dto;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import share.apk.server.exceptions.NotNullException;

@Entity
public class FilePacket extends Packet {
	@OneToOne
	File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) throws NotNullException {
		if (file != null) {
			this.file = file;
		} else {
			throw new NotNullException("APK File cannot be null");
		}
	}
}
