package main;

import java.io.BufferedReader;

public class PageThread extends Thread {
	
	private Monitor m;
	private BufferedReader bf;
	private int cantReferencias;
	
	public PageThread(Monitor m, BufferedReader bf, int cantReferencias) {
		this.m = m;
		this.bf = bf;
		this.cantReferencias = cantReferencias;
	}
	
	public void run() {
		
		for ( int i = 0; i < cantReferencias; i++ ) {
			if ( i == 300) {
				System.out.print("");
			}
			try {
				String[] temp = bf.readLine().split(",");
				int key = Integer.parseInt(temp[0]);
				m.readReference( key, temp[1].equals("m") );
				sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		m.isFinished();
		System.out.println(m.getFallos());
	}
}
