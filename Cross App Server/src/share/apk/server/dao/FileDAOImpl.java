package share.apk.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import share.apk.server.dto.File;
import share.apk.server.dto.User;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.FileException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;
@Repository
public class FileDAOImpl implements FileDAO {

	List<String> errorMessages;
	SessionFactory sessionFactory;

	public FileDAOImpl(List<String> errorMessages, SessionFactory sessionFactory) {
		this.errorMessages = new ArrayList<>();
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public File getFile(long id) throws NoSuchIDException,
			NegativeValueException, FileException {
		if (id <= 0) {
			throw new NegativeValueException("Negative ID Supplied " + id);
		}
		Session s = sessionFactory.getCurrentSession();
		// s.beginTransaction();
		File asf = (File) s.get(File.class, id);
		// s.getTransaction().commit();
		if (asf != null) {
			return asf;
		} else {
			throw new NoSuchIDException("No File with ID " + id);
		}
	}

	@Override
	@Transactional
	public File getFile(String fileURI) throws EmptyStringException,
			FileException {
		if (fileURI.equals("")) {
			throw new EmptyStringException("FileURI cannot be empty ");
		} else {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			Criteria criteria = s.createCriteria(User.class);
			criteria.add(Restrictions.eq("fileURI", fileURI));
			// s.getTransaction().commit();
			if (criteria.list().size() > 0) {
				return (File) criteria.list().get(0);
			} else {
				throw new FileException("No File with file URI" + fileURI);
			}
		}
	}

	@Override
	@Transactional
	public boolean addFile(File File) throws FileException {
		if (File != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.save(File);
			// s.getTransaction().commit();
		} else {
			throw new FileException("File cannot be NULL");
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateFileName(File File, String newFileName)
			throws EmptyStringException, FileException {
		if (File != null) {
			if (newFileName.equals("")) {
				throw new EmptyStringException("New File Name cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				File.setFileName(newFileName);
				s.update(File);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new FileException("File cannot be NULL");
		}
	}

	@Override
	@Transactional
	public boolean updateFileURI(File File, String newFileURI)
			throws FileException, EmptyStringException {
		if (File != null) {
			if (newFileURI.equals("")) {
				throw new EmptyStringException("New File URI cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				File.setFileURI(newFileURI);
				s.update(File);
				// s.getTransaction().commit();
				return true;
			}
		} else {
			throw new FileException("File cannot be NULL");
		}

	}

	@Override
	@Transactional
	public boolean updateFileSize(File File, long newFileSize)
			throws FileException, NegativeValueException {
		if (File != null) {
			if (newFileSize >= 0) {
				Session s = sessionFactory.getCurrentSession();
				// s.beginTransaction();
				File.setFileSize(newFileSize);
				s.update(File);
				// s.getTransaction().commit();
				return true;
			} else {
				throw new NegativeValueException("File Size cannot be Negative");
			}
		} else {
			throw new FileException("File cannot be NULL");
		}

	}

	@Override
	@Transactional
	public boolean deleteFile(File File) throws FileException {
		if (File != null) {
			Session s = sessionFactory.getCurrentSession();
			// s.beginTransaction();
			s.delete(File);
			// s.getTransaction().commit();
			return true;
		} else {
			throw new FileException("File cannot be NULL");
		}
	}

	@Override
	public List<String> getErrorMessages() {
		return errorMessages;
	}

}
