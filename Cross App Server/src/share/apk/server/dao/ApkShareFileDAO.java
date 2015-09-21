package share.apk.server.dao;

import java.util.List;

import share.apk.server.dto.ApkShareFile;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.FileException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;

public interface ApkShareFileDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// get file by id
	public ApkShareFile getApkShareFile(long id) throws NoSuchIDException,
			NegativeValueException, FileException;

	// get file by fileUri
	public ApkShareFile getApkShareFile(String fileURI)
			throws EmptyStringException, FileException;

	// add a ApkShareFile
	public boolean addApkShareFile(ApkShareFile apkShareFile)
			throws FileException;

	// update a ApkShareFile fileName
	public boolean updateApkShareFileName(ApkShareFile apkShareFile,
			String newFileName) throws FileException, EmptyStringException;

	// update a ApkShareFile fileUri
	public boolean updateApkShareFileURI(ApkShareFile apkShareFile,
			String newFileURI) throws FileException, EmptyStringException;

	// update a ApkShareFile fileSize
	public boolean updateApkShareFileSize(ApkShareFile apkShareFile,
			long newFileSize) throws FileException, NegativeValueException;

	// delete a ApkShareFile
	public boolean deleteApkShareFile(ApkShareFile apkShareFile)
			throws FileException;

}
