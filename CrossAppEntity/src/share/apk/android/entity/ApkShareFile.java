package share.apk.android.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import share.apk.android.exceptions.EmptyStringException;
import share.apk.android.exceptions.NegativeValueException;

@Entity
public abstract class ApkShareFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String fileURI;
	long fileSize;
	String fileName;
	@ElementCollection
	List<String> tags = new ArrayList<String>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileURI() {
		return fileURI;
	}

	public void setFileURI(String fileURI) throws EmptyStringException {
		if (fileURI.equals("")) {
			this.fileURI = fileURI;
		} else {
			throw new EmptyStringException("File URI cannot be empty");
		}
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) throws NegativeValueException {
		if (fileSize >= 0) {
			this.fileSize = fileSize;
		} else {
			throw new NegativeValueException("File size  cannot be negative.");
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) throws EmptyStringException {
		if (fileName.equals("")) {
			this.fileName = fileName;
		} else {
			throw new EmptyStringException("File name cannot be empty");
		}
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
