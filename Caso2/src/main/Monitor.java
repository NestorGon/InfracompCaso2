package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Monitor {
	
	private HashMap<Integer, Integer[]> tp;
	private int fallos;
	private int[] ram;
	private int occupied;
	private boolean finished;
	
	public Monitor( int paginas, int marcos ) {
		tp = new HashMap<Integer, Integer[]>(paginas);
		for ( int i = 0; i < paginas; i++ ) {
			tp.put(i, new Integer[]{-1,0,0});
		}
		fallos = 0;
		ram = new int[marcos];
		occupied = 0;
		finished = false;
	}
	
	public void readReference( Integer key, boolean m ) {
		int pageFrame, mBit, rBit;
		synchronized (this) {
			pageFrame = tp.get(key)[0];
			rBit = tp.get(key)[1];
			mBit = tp.get(key)[2];
		}
		if ( pageFrame == -1 ) {
			if ( occupied != ram.length ) {
				ram[occupied] = key;
				updateBit( key, occupied, m );
				occupied++;
			} else {
				int remPos = NRU();
				int posVirtual = ram[remPos];
				ram[remPos] = key;
				updateBit(key, remPos, m);
				updateBit(posVirtual, -1, m);
			}
			fallos++;
		} else if ( m && mBit == 0 ) {
			synchronized (this) {
				Integer[] temp = tp.get(key);
				temp[2] = 1;
				tp.put(key, temp);
			}
		} else if ( !m && rBit == 0 ) {
			synchronized (this) {
				Integer[] temp = tp.get(key);
				temp[1] = 1;
				tp.put(key, temp);
			}
		}
	}
	
	public synchronized Integer NRU() {
		ArrayList<Integer> class1 = new ArrayList<Integer>();
		ArrayList<Integer> class2 = new ArrayList<Integer>();
		ArrayList<Integer> class3 = new ArrayList<Integer>();
		ArrayList<Integer> class4 = new ArrayList<Integer>();
		for ( int i = 0; i < ram.length; i++ ) {
			Integer[] temp = tp.get(ram[i]);
			if ( temp[1] == 0 ) {
				if ( temp[2] == 0 ) {
					class1.add(i);
				} else {
					class2.add(i);
				}
			} else {
				if ( temp[2] == 0 ) {
					class3.add(i);
				} else {
					class4.add(i);
				}
			}
		}
		int random = 0;
		if ( class1.size() > 0 ) {
			random = new Random().nextInt(class1.size());
			return class1.get(random);
		} else if ( class2.size() > 0 ) {
			random = new Random().nextInt(class2.size());
			return class2.get(random);
		} else if ( class3.size() > 0 ) {
			random = new Random().nextInt(class3.size());
			return class3.get(random);
		} else {
			random = new Random().nextInt(class4.size());
			return class4.get(random);
		}
	}
	
	public synchronized void updateBit( Integer key, int ramPos, boolean m ) {
		Integer[] temp = tp.get(key);
		temp[0] = ramPos;
		if ( ramPos == -1 ) {
			temp[2] = 0;
		} else {
			temp[1] = 1;
			temp[2] = m ? 1: temp[2];
		}
		tp.put(key, temp);
	}
	
	public synchronized void updateReferences() {
		for (Integer key: tp.keySet()) {
			Integer[] temp = tp.get(key);
			temp[1] = 0;
			tp.put(key, temp);
		}
	}
	
	public int getFallos() {
		return fallos;
	}
	
	public void isFinished() {
		finished = true;
	}
	public boolean getFinished() {
		return finished;
	}
	
	public static void main(String[] args) {
		Scanner in = null;
		BufferedReader bf = null;
		Monitor m = null;
		try {
			in = new Scanner(System.in);
			String file = in.next();
			bf = new BufferedReader(new FileReader(file));
			int marcos = Integer.parseInt(bf.readLine());
			int paginas = Integer.parseInt(bf.readLine());
			int cantReferencias = Integer.parseInt(bf.readLine());
			//Crear y correr thread
			m = new Monitor( paginas, marcos );
			PageThread thread1 = new PageThread(m, bf, cantReferencias);
			BitThread thread2 = new BitThread(m);
			thread2.start();
			thread1.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			while ( !m.getFinished() ) {
				System.out.print("");
			}
			try {
				bf.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
