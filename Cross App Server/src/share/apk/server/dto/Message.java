package share.apk.server.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import share.apk.server.exceptions.EmptyStringException;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) throws EmptyStringException {
		if (!message.equals("")) {
			this.message = message;
		} else {
			throw new EmptyStringException("Message cannot be empty");
		}
	}

}
