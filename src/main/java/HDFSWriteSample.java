
import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;

public class HDFSWriteSample {

	public static final String theFilename = "hello.txt";
	public static final String message = "Hello, world 1 this is append message!\n";

	public static void main(String[] args) throws IOException {

		Configuration conf = new Configuration();
		conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
        conf.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));
		FileSystem fs = FileSystem.get(conf);

		Path filenamePath = new Path(theFilename);
		FSDataOutputStream out = null;
		try {
			if (fs.exists(filenamePath)) {
				// remove the file first
				//fs.delete(filenamePath);
				 out = fs.append(filenamePath);
				
			}

			//FSDataOutputStream out = fs.create(filenamePath);
			out.writeUTF(message);
			out.close();

			FSDataInputStream in = fs.open(filenamePath);
			String messageIn = in.readUTF();
			System.out.print(messageIn);
			in.close();
		} catch (IOException ioe) {
			System.err.println("IOException during operation: "
					+ ioe.toString());
			System.exit(1);
		}
	}
}
