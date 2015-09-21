package share.apk.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import share.apk.server.dto.ApkSharePacket;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.PacketException;

public class ApkSharePacketDAOImpl implements ApkSharePacketDAO {

	List<String> errorMessages;
	SessionFactory sessionFactory;

	public ApkSharePacketDAOImpl(SessionFactory sessionFactory) {
		this.errorMessages = new ArrayList<>();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean storePacket(ApkSharePacket apkSharePacket)
			throws PacketException {
		if (apkSharePacket != null) {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.save(apkSharePacket);
			s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
	public boolean deletePacket(ApkSharePacket apkSharePacket)
			throws PacketException {
		if (apkSharePacket != null) {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.delete(apkSharePacket);
			s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
	public boolean updatePacket(ApkSharePacket apkSharePacket)
			throws PacketException {
		if (apkSharePacket != null) {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.update(apkSharePacket);
			s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
	public ApkSharePacket getPacket(long id) throws NegativeValueException,
			PacketException {
		if (id <= 0) {
			throw new NegativeValueException("Negative ID Supplied " + id);
		}
		Session s = sessionFactory.getCurrentSession();
		s.beginTransaction();
		ApkSharePacket asp = (ApkSharePacket) s.get(ApkSharePacket.class, id);
		s.getTransaction().commit();
		if (asp != null) {
			return asp;
		} else {
			throw new PacketException("No Packet with ID " + id);
		}
	}

	@Override
	public List<String> getErrorMessages() {
		return errorMessages;
	}

}
