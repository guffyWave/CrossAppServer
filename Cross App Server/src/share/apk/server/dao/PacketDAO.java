package share.apk.server.dao;

import java.util.List;

import share.apk.server.dto.Packet;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.PacketException;

public interface PacketDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// store packet
	public boolean storePacket(Packet packet) throws PacketException;

	// delete packet
	public boolean deletePacket(Packet packet) throws PacketException;

	// update packet
	public boolean updatePacket(Packet packet) throws PacketException;

	// get packet by id
	public Packet getPacket(long id) throws NegativeValueException,
			PacketException;

	// ---Low priority---- get to_user of packet

	// ---Low priority---- get from_user of packet

	// ---Low priority---- get packetStatus

}
