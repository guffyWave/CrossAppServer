package share.apk.android.entity;

public abstract class ApkSharePacket {
	long id;
	ApkShareUser fromUser;
	ApkShareUser toUser;
	ApkShareStatus fileStatus;
}
