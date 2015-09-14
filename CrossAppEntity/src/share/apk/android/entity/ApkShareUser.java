package share.apk.android.entity;

import java.util.ArrayList;
import java.util.List;

public class ApkShareUser {
	long id;
	String emailID;
	String phoneNumber;
	String gcmID;
	boolean userActivationStatus;
	List<ApkSharePacket> sharePackets = new ArrayList<ApkSharePacket>();
}
