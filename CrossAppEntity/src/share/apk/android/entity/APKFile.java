package share.apk.android.entity;

import javax.persistence.Entity;

@Entity
public class APKFile extends ApkShareFile {
	String packageName;
	long versionCode;
	String vesrionName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVesrionName() {
		return vesrionName;
	}

	public void setVesrionName(String vesrionName) {
		this.vesrionName = vesrionName;
	}

	public long getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(long versionCode) {
		this.versionCode = versionCode;
	}

}
