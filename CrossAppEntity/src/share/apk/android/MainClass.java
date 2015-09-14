package share.apk.android;

import org.hibernate.Session;

import share.apk.android.entity.APKFile;
import share.apk.android.entity.ApkSharePacket;

import share.apk.android.entity.ApkShareUser;
import share.apk.android.entity.FilePacket;
import share.apk.android.management.DatabaseConnectionManager;

// android.apk.share@gmail.com
public class MainClass {

	public static void main(String[] args) {

		ApkShareUser asu = new ApkShareUser();
		asu.setEmailID("gufran.khurshid@gmail.com");
		asu.setGcmID("blah blah");
		asu.setPhoneNumber("+874577483");
		asu.setUserActivationStatus(false);

		ApkShareUser asu2 = new ApkShareUser();
		asu2.setEmailID("salman.khurshid@gmail.com");
		asu2.setGcmID("ohohohhohohoho");
		asu2.setPhoneNumber("+89894828");
		asu2.setUserActivationStatus(true);

		APKFile apkFile = new APKFile();
		apkFile.setFileName("asshole.apk");
		apkFile.setFileSize(734892);
		apkFile.setFileURI("/User/apks/asshole.apk");
		apkFile.setPackageName("com.android.asshole");
		apkFile.setVersionCode(3);
		apkFile.setVesrionName("Chatur Version");

		FilePacket fp = new FilePacket();
		fp.setFromUser(asu);
		fp.setFile(apkFile);
		//fp.setFileStatus(ApkShareStatus.DELIVERED);

		DatabaseConnectionManager dcm = DatabaseConnectionManager.getInstance();
		Session s = dcm.getSession();
		s.beginTransaction();
		s.save(asu);
		s.getTransaction().commit();

		System.out.println("Data saved !");

	}
}
