package share.apk.android.entity;

import javax.persistence.Entity;

@Entity
public class MessagePacket extends ApkSharePacket {
	String message;
}
