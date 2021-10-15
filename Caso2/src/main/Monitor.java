package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Monitor {
	
	private ArrayList<String> history;
	
	public Monitor(ArrayList<String> history) {
		this.history = history;
	}
	
	public void addReference() {
		
	}
	
	public String readReference() {
		return null;
	}
	
	//TODO Read all from history and delete read items, then PageThread can continue
	
	public static void main(String[] args) {
		Scanner in = null;
		BufferedReader bf = null;
		try {
			in = new Scanner(System.in);
			String file = in.next();
			bf = new BufferedReader(new FileReader(file));
			int marcos = Integer.parseInt(bf.readLine());
			int paginas = Integer.parseInt(bf.readLine());
			int cantReferencias = Integer.parseInt(bf.readLine());
			//Crear y correr thread
			ArrayList<String> history = new ArrayList<String>();
			Monitor m = new Monitor(history);
			PageThread thread1 = new PageThread(m, bf, cantReferencias, marcos, paginas);
			BitThread thread2 = new BitThread(m);
			thread1.run();
			thread2.run();
			
		} catch (Exception e) {
			
		} finally {
			try {
				bf.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
