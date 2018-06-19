package it.polito.tdp.formulaone.model;

public class Evento implements Comparable<Evento>{
	
	private FantaPilota fp;
	private int lap;
	private int time;
	
	public Evento(FantaPilota fp, int lap, int time) {
		this.fp = fp;
		this.lap = lap;
		this.time = time;
	}


	public FantaPilota getFp() {
		return fp;
	}

	public int getLap() {
		return lap;
	}

	public int getTime() {
		return time;
	}

	@Override
	public int compareTo(Evento arg0) {
		// TODO Auto-generated method stub
		return this.time - arg0.time;
	}
	
	

}
