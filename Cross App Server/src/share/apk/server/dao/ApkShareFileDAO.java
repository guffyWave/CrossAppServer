package share.apk.server.dao;

import java.util.List;

import share.apk.server.dto.File;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.FileException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;

public interface ApkShareFileDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// get file by id
	public File getApkShareFile(long id) throws NoSuchIDException,
			NegativeValueException, FileException;

	// get file by fileUri
	public File getApkShareFile(String fileURI)
			throws EmptyStringException, FileException;

	// add a ApkShareFile
	public boolean addApkShareFile(File apkShareFile)
			throws FileException;

	// update a ApkShareFile fileName
	public boolean updateApkShareFileName(File apkShareFile,
			String newFileName) throws FileException, EmptyStringException;

	// update a ApkShareFile fileUri
	public boolean updateApkShareFileURI(File apkShareFile,
			String newFileURI) throws FileException, EmptyStringException;

	// update a ApkShareFile fileSize
	public boolean updateApkShareFileSize(File apkShareFile,
			long newFileSize) throws FileException, NegativeValueException;

	// delete a ApkShareFile
	public boolean deleteApkShareFile(File apkShareFile)
			throws FileException;

}
