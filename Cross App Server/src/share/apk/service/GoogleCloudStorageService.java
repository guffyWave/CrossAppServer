package share.apk.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;

public class GoogleCloudStorageService {
	// Steps to Add Google Storage Client
	// 1. First add Google Storage API(or Google Storage JSON API)
	// 2. Add 4 Extra Jars as provided in
	// https://cloud.google.com/appengine/docs/java/googlecloudstorageclient/download
	// (better to make a library and them)
	// 3. Add some class files from https://github.com/kernel164/gmultipart
	// 4. Add gson, commons-io and commons fileupload

	public static final String BUCKETNAME = "cloudstorageclientstudybucket";
	private static final int BUFFER_SIZE = 1024 * 1024;

	private OutputStream os = null;
	GcsOutputChannel writeChannel;

	public void init(String fileName, String mime) throws Exception {
		GcsService gcsService = GcsServiceFactory.createGcsService();
		GcsFilename filename = new GcsFilename(BUCKETNAME, fileName);
		GcsFileOptions options = new GcsFileOptions.Builder().mimeType(mime)
				.acl("public-read")
				.addUserMetadata("myfield1", "my field value").build();

		writeChannel = gcsService.createOrReplace(filename, options);
		writeChannel.waitForOutstandingWrites();
		os = Channels.newOutputStream(writeChannel);

	}

	public void storeFile(byte[] b, int readSize) throws Exception {
		os.write(b, 0, readSize);
		os.flush();
	}

	public void storeFile(byte[] b) throws Exception {
		os.write(b);
		os.flush();
	}

	public void destroy() throws Exception {
		os.close();
		writeChannel.close();
	}

	public boolean deleteFile(String fileName, String mime) throws IOException {
		GcsService gcsService = GcsServiceFactory.createGcsService();
		GcsFilename filename = new GcsFilename(BUCKETNAME, fileName);

		return gcsService.delete(filename);
	}

}
