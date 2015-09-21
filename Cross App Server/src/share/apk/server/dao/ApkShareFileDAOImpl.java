package share.apk.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import share.apk.server.dto.ApkShareFile;
import share.apk.server.dto.ApkShareUser;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.FileException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;

public class ApkShareFileDAOImpl implements ApkShareFileDAO {

	List<String> errorMessages;
	SessionFactory sessionFactory;

	public ApkShareFileDAOImpl(List<String> errorMessages,
			SessionFactory sessionFactory) {
		this.errorMessages = new ArrayList<>();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ApkShareFile getApkShareFile(long id) throws NoSuchIDException,
			NegativeValueException, FileException {
		if (id <= 0) {
			throw new NegativeValueException("Negative ID Supplied " + id);
		}
		Session s = sessionFactory.getCurrentSession();
		s.beginTransaction();
		ApkShareFile asf = (ApkShareFile) s.get(ApkShareFile.class, id);
		s.getTransaction().commit();
		if (asf != null) {
			return asf;
		} else {
			throw new NoSuchIDException("No File with ID " + id);
		}
	}

	@Override
	public ApkShareFile getApkShareFile(String fileURI)
			throws EmptyStringException, FileException {
		if (fileURI.equals("")) {
			throw new EmptyStringException("FileURI cannot be empty ");
		} else {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			Criteria criteria = s.createCriteria(ApkShareUser.class);
			criteria.add(Restrictions.eq("fileURI", fileURI));
			s.getTransaction().commit();
			if (criteria.list().size() > 0) {
				return (ApkShareFile) criteria.list().get(0);
			} else {
				throw new FileException("No File with file URI" + fileURI);
			}
		}
	}

	@Override
	public boolean addApkShareFile(ApkShareFile apkShareFile)
			throws FileException {
		if (apkShareFile != null) {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.save(apkShareFile);
			s.getTransaction().commit();
		} else {
			throw new FileException("File cannot be NULL");
		}
		return false;
	}

	@Override
	public boolean updateApkShareFileName(ApkShareFile apkShareFile,
			String newFileName) throws EmptyStringException, FileException {
		if (apkShareFile != null) {
			if (newFileName.equals("")) {
				throw new EmptyStringException("New File Name cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareFile.setFileName(newFileName);
				s.update(apkShareFile);
				s.getTransaction().commit();
				return true;
			}
		} else {
			throw new FileException("File cannot be NULL");
		}
	}

	@Override
	public boolean updateApkShareFileURI(ApkShareFile apkShareFile,
			String newFileURI) throws FileException, EmptyStringException {
		if (apkShareFile != null) {
			if (newFileURI.equals("")) {
				throw new EmptyStringException("New File URI cannot be empty ");
			} else {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareFile.setFileURI(newFileURI);
				s.update(apkShareFile);
				s.getTransaction().commit();
				return true;
			}
		} else {
			throw new FileException("File cannot be NULL");
		}

	}

	@Override
	public boolean updateApkShareFileSize(ApkShareFile apkShareFile,
			long newFileSize) throws FileException, NegativeValueException {
		if (apkShareFile != null) {
			if (newFileSize >= 0) {
				Session s = sessionFactory.getCurrentSession();
				s.beginTransaction();
				apkShareFile.setFileSize(newFileSize);
				s.update(apkShareFile);
				s.getTransaction().commit();
				return true;
			} else {
				throw new NegativeValueException("File Size cannot be Negative");
			}
		} else {
			throw new FileException("File cannot be NULL");
		}

	}

	@Override
	public boolean deleteApkShareFile(ApkShareFile apkShareFile)
			throws FileException {
		if (apkShareFile != null) {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.delete(apkShareFile);
			s.getTransaction().commit();
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
