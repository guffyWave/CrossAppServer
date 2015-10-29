package share.apk.server.jsonResponse;

import java.util.Date;

import share.apk.server.dto.Message;
import share.apk.server.dto.PacketStatus;

public class MessagePacketResult {
	private long id;
	private UserResult fromUserResult;
	private UserResult toUserResult;
	private PacketStatus packetStatus;
	private Date timeStamp;
	private Message message;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserResult getFromUserResult() {
		return fromUserResult;
	}

	public void setFromUserResult(UserResult fromUserResult) {
		this.fromUserResult = fromUserResult;
	}

	public UserResult getToUserResult() {
		return toUserResult;
	}

	public void setToUserResult(UserResult toUserResult) {
		this.toUserResult = toUserResult;
	}

	public PacketStatus getPacketStatus() {
		return packetStatus;
	}

	public void setPacketStatus(PacketStatus packetStatus) {
		this.packetStatus = packetStatus;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
