package share.apk.server.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import share.apk.server.exceptions.NotNullException;

@Entity
public class MessagePacket extends Packet {
	@OneToOne(cascade = CascadeType.ALL)
	Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) throws NotNullException {
		if (message != null) {
			this.message = message;
		} else {
			throw new NotNullException("Message cannot be null");
		}
	}

}
