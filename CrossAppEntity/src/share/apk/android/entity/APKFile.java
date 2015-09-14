package share.apk.android.entity;

import javax.persistence.Entity;

import share.apk.android.exceptions.EmptyStringException;
import share.apk.android.exceptions.NegativeValueException;

@Entity
public class APKFile extends ApkShareFile {
	String packageName;
	long versionCode;
	String versionName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) throws EmptyStringException {
		if (packageName.equals("")) {
			this.packageName = packageName;
		} else {
			throw new EmptyStringException("Package Name cannot be empty");
		}
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) throws EmptyStringException {
		if (versionName.equals("")) {
			this.versionName = versionName;
		} else {
			throw new EmptyStringException("Version Name cannot be empty");
		}
	}

	public long getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(long versionCode) throws NegativeValueException {
		if (versionCode >= 0) {
			this.versionCode = versionCode;
		} else {
			throw new NegativeValueException("Version Code cannot be negative.");
		}
	}
}
