package main;

import java.io.BufferedReader;
import java.util.HashMap;

public class PageThread extends Thread {
	
	private Monitor m;
	private BufferedReader bf;
	private int cantReferencias;
	private HashMap<Integer, Integer> tp;
	private int[] ram;
	private int occupied;
	
	public PageThread(Monitor m, BufferedReader bf, int cantReferencias, int marcos, int paginas) {
		this.m = m;
		this.bf = bf;
		this.cantReferencias = cantReferencias;
		tp = new HashMap<Integer, Integer>(paginas);
		for ( int i = 0; i < paginas; i++ ) {
			tp.put(i, -1);
		}
		ram = new int[marcos];
		occupied = 0;
	}
	
	public void run() {
		
		for ( int i = 0; i < cantReferencias; i++ ) {
			try {
				String temp = bf.readLine();
				//TODO Monitor añadir al history
				int key = Integer.parseInt(temp.split(",")[0]);
				if ( tp.get(key) == -1 ) {
					if ( occupied != ram.length ) {
						ram[occupied] = key;
						occupied++;
					} else {
						
					}
				}
				sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
