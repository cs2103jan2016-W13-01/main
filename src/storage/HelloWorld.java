package storage;

import java.io.File;
import java.io.IOException;

public class HelloWorld {
	public static void main(String[] args) throws IOException{
		File file = new File("data");
		if (!file.exists()) {
			System.out.println(file.mkdirs());
		}
	}
}
