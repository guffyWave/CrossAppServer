package share.apk.server.dao;

import java.util.List;

import share.apk.server.dto.File;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.FileException;
import share.apk.server.exceptions.NegativeValueException;
import share.apk.server.exceptions.NoSuchIDException;

public interface FileDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// get file by id
	public File getFile(long id) throws NoSuchIDException,
			NegativeValueException, FileException;

	// get file by fileUri
	public File getFile(String fileURI)
			throws EmptyStringException, FileException;

	// add a File
	public boolean addFile(File File)
			throws FileException;

	// update a File fileName
	public boolean updateFileName(File File,
			String newFileName) throws FileException, EmptyStringException;

	// update a File fileUri
	public boolean updateFileURI(File File,
			String newFileURI) throws FileException, EmptyStringException;

	// update a File fileSize
	public boolean updateFileSize(File File,
			long newFileSize) throws FileException, NegativeValueException;

	// delete a File
	public boolean deleteFile(File File)
			throws FileException;

}
