package share.apk.android;

import java.util.Date;

import org.hibernate.Session;

import share.apk.android.entity.APKFile;
import share.apk.android.entity.ApkShareMessage;
import share.apk.android.entity.ApkShareStatus;
import share.apk.android.entity.ApkShareUser;
import share.apk.android.entity.FilePacket;
import share.apk.android.entity.MessagePacket;
import share.apk.android.management.DatabaseConnectionManager;

// android.apk.share@gmail.com
public class MainClass {

	public static void main(String[] args) throws Exception {

		// /---> create user 1
		ApkShareUser asu = new ApkShareUser();
		asu.setEmailID("gufran.khurshid@gmail.com");
		asu.setGcmID("blah blah");
		asu.setPhoneNumber("+917457748354");
		asu.setUserActivationStatus(false);

		// /---> create user 2
		ApkShareUser asu2 = new ApkShareUser();
		asu2.setEmailID("salman.khurshid@gmail.com");
		asu2.setGcmID("ohohohhohohoho");
		asu2.setPhoneNumber("+918948284582");
		asu2.setUserActivationStatus(true);

		// /---> create an APK File
		APKFile apkFile = new APKFile();
		apkFile.setFileName("asshole.apk");
		apkFile.setFileSize(734892);
		apkFile.setFileURI("/User/apks/asshole.apk");
		apkFile.setPackageName("com.android.asshole");
		apkFile.setVersionCode(3);
		apkFile.setAppVersionName("Chatur Version");

		// /---> create a file packet and attach APK to it
		FilePacket fp = new FilePacket();
		fp.setFromUser(asu);
		fp.setToUser(asu2);
		fp.setFile(apkFile);
		fp.setStatus(ApkShareStatus.DELIVERED);
		fp.setTimeStamp(new Date());

		// user 1 --> user 2 // user 1 sent an APK to user 2

		// attach the file packet to user outbox
		asu.getOutBoxPacketList().add(fp);

		// attach the file packet in user inbox
		asu2.getInBoxPacketList().add(fp);

		// create a message
		ApkShareMessage apkShareMessage = new ApkShareMessage();
		apkShareMessage.setMessage("Thanks for the APK ");

		// /--->> create a message packet and attach a message to it
		MessagePacket mp = new MessagePacket();
		mp.setFromUser(asu2);
		mp.setToUser(asu);
		mp.setApkShareMessage(apkShareMessage);
		mp.setStatus(ApkShareStatus.DELIVERED);
		mp.setTimeStamp(new Date());

		// user 2 --> user 1 // user 2 sent a message to user 1

		// attach the message packet to user outbox
		asu2.getOutBoxPacketList().add(mp);

		// attach the message packet in user inbox
		asu.getInBoxPacketList().add(mp);

		DatabaseConnectionManager dcm = DatabaseConnectionManager.getInstance();
		Session s = dcm.getSession();
		s.beginTransaction();

		s.save(asu);
		s.save(asu2);
		s.save(apkFile);
		s.save(apkShareMessage);
		s.save(mp);
		s.save(fp);

		s.getTransaction().commit();
		System.out.println("Data saved !");

	}
}
