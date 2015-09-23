package share.apk.server.dto;

import javax.persistence.Entity;

import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.NegativeValueException;


@Entity
public class APKFile extends ApkShareFile {
	String packageName;
	long versionCode;
	String appVersionName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) throws EmptyStringException {
		if (!packageName.equals("")) {
			this.packageName = packageName;
		} else {
			throw new EmptyStringException("Package Name cannot be empty");
		}
	}

	public long getVersionCode() {
		return versionCode;
	}

	public String getAppVersionName() {
		return appVersionName;
	}

	public void setAppVersionName(String appVersionName)
			throws EmptyStringException {
		if (!appVersionName.equals("")) {
			this.appVersionName = appVersionName;
		} else {
			throw new EmptyStringException("Version Name cannot be empty");
		}
	}

	public void setVersionCode(long versionCode) throws NegativeValueException {
		if (versionCode >= 0) {
			this.versionCode = versionCode;
		} else {
			throw new NegativeValueException("Version Code cannot be negative.");
		}
	}
}
