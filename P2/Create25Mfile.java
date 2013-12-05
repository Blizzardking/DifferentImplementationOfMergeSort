import java.io.*;
import java.util.*;
public class Create25Mfile {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		final int FILE_SIZE = 25000000;
		File f = new File("bigrandomfile25M.dat");
		int i = 0;
		DataOutputStream os = null;
		try {
			os = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			while(i<FILE_SIZE) {
				os.writeInt((int)(Math.random()*1000000000));
				i++;
			//	System.out.println(i++);
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
		}
		DataInputStream input = new DataInputStream(new FileInputStream(f));
		for(int j = 0; j < 100; j++) {
			System.out.print(input.readInt() + " ");
		}
		input.close();
	}
	

}
