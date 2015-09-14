package share.apk.android.entity;

import java.util.List;

public abstract class ApkShareFile {
	long id;
	String fileURI;
	long fileSize;
	String fileName;
	List<String> tags;
}
