package no.nxc.vn;

import java.io.BufferedReader;
import java.io.InputStreamReader;

 

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = reader.readLine();
			if (line.equals("exit")) {
				break;
			} else {
				System.out.println(line.toUpperCase());
			}
		}
	}
}
