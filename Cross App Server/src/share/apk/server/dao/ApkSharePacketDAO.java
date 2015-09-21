package share.apk.server.dao;

import java.util.List;

import share.apk.server.dto.ApkSharePacket;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.PacketException;

public interface ApkSharePacketDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// store packet
	public boolean storePacket(ApkSharePacket apkSharePacket)
			throws PacketException;

	// delete packet
	public boolean deletePacket(ApkSharePacket apkSharePacket)
			throws PacketException;

	// update packet
	public boolean updatePacket(ApkSharePacket apkSharePacket)
			throws PacketException;

	// get packet by id
	public ApkSharePacket getPacket(long id) throws NegativeValueException,
			PacketException;

	// ---Low priority---- get to_user of packet

	// ---Low priority---- get from_user of packet

	// ---Low priority---- get packetStatus

}
