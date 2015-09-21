package share.apk.server.dao;

import java.util.List;

import share.apk.server.dto.ApkShareFile;

public interface ApkShareFileDAO {

	// --->> for error message
	public List<String> getErrorMessages();

	// get file by id
	public ApkShareFile getApkShareFile(long id);

	// get file by fileUri
	public ApkShareFile getApkShareFile(String fileURI);

	// add a ApkShareFile
	public boolean addApkShareFile(ApkShareFile apkShareFile);

	// update a ApkShareFile fileName
	public boolean updateApkShareFileName(ApkShareFile apkShareFile,
			String newFileName);

	// update a ApkShareFile fileUri
	public boolean updateApkShareFileURI(ApkShareFile apkShareFile,
			String newFileURI);

	// update a ApkShareFile fileSize
	public boolean updateApkShareFileSize(ApkShareFile apkShareFile,
			String newFileSize);

	// delete a ApkShareFile
	public boolean deleteApkShareFile(ApkShareFile apkShareFile);
	

}
