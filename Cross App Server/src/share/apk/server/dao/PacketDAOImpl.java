package share.apk.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import share.apk.server.dto.Packet;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.PacketException;

@Repository
@Transactional
public class PacketDAOImpl implements PacketDAO {

	List<String> errorMessages;
	SessionFactory sessionFactory;

	public PacketDAOImpl(SessionFactory sessionFactory) {
		this.errorMessages = new ArrayList<>();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean storePacket(Packet packet) throws PacketException {
		if (packet != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.save(packet);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
	public boolean deletePacket(Packet packet) throws PacketException {
		if (packet != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.delete(packet);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
	public boolean updatePacket(Packet packet) throws PacketException {
		if (packet != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.update(packet);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new PacketException("Packet cannot be NULL");
		}
	}

	@Override
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
