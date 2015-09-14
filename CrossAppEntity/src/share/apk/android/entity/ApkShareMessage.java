package share.apk.android.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import share.apk.android.exceptions.EmptyStringException;

@Entity
public class ApkShareMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) throws EmptyStringException {
		if (message.equals("")) {
			this.message = message;
		} else {
			throw new EmptyStringException("Message cannot be empty");
		}
	}

}
