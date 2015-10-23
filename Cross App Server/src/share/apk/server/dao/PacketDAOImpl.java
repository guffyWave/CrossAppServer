package share.apk.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import share.apk.server.dto.Packet;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.PacketException;

public class PacketDAOImpl implements PacketDAO {

	List<String> errorMessages;
	SessionFactory sessionFactory;

	public PacketDAOImpl(SessionFactory sessionFactory) {
		this.errorMessages = new ArrayList<>();
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean storePacket(Packet apkSharePacket)
			throws PacketException {
		if (apkSharePacket != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.save(apkSharePacket);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
	@Transactional
	public boolean deletePacket(Packet apkSharePacket)
			throws PacketException {
		if (apkSharePacket != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.delete(apkSharePacket);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
	@Transactional
	public boolean updatePacket(Packet apkSharePacket)
			throws PacketException {
		if (apkSharePacket != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.update(apkSharePacket);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
	@Transactional
	public Packet getPacket(long id) throws NegativeValueException,
			PacketException {
		if (id <= 0) {
			throw new NegativeValueException("Negative ID Supplied " + id);
		}
		Session s = sessionFactory.getCurrentSession();
		// s.beginTransaction();
		Packet asp = (Packet) s.get(Packet.class, id);
		// s.getTransaction().commit();
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
