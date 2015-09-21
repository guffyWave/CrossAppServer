package share.apk.server.dao;

import java.util.List;

import share.apk.server.dto.ApkSharePacket;

public interface ApkSharePacketDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// store packet
	public boolean storePacket(ApkSharePacket apkSharePacket);

	// delete packet
	public boolean deletePacket(ApkSharePacket apkSharePacket);

	// update packet
	public boolean updatePacket(ApkSharePacket apkSharePacket);

	// get packet by id
	public ApkSharePacket storePacket(long id);

	// ---Low priority---- get to_user of packet

	// ---Low priority---- get from_user of packet

	// ---Low priority---- get packetStatus

}
