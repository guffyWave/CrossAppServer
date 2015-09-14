import share.apk.android.exceptions.ApkShareException;

// android.apk.share@gmail.com
public class MainClass {

	public static void main(String[] args) throws Exception {

		System.out.println("This is first line..");

		throw new ApkShareException("Custom exception is fucking Coool !");

	}
}
