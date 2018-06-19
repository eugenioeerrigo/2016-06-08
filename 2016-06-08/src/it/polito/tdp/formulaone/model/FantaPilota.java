package it.polito.tdp.formulaone.model;

import java.util.List;

public class FantaPilota {
	
	//Dato 1 driver e 1 circuito, l'oggetto contiene le seguenti info:
	private int year;
	private List<Integer> laptimes;
	private int points;
	private int lap;              //giro corrente
	private int lastPosition;     //posizione rispetto agli altri 
	private boolean eliminato;
	
	public FantaPilota(int year, List<Integer> laptimes) {
		this.year = year;
		this.laptimes = laptimes;
		this.points = 0;
		this.lap = -1;
		this.lastPosition = -1;
		this.eliminato = false;
	}

	public boolean isEliminato() {
		return eliminato;
	}

	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Integer> getLaptimes() {
		return laptimes;
	}

	public void setLaptimes(List<Integer> laptimes) {
		this.laptimes = laptimes;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getLap() {
		return lap;
	}

	public void setLap(int lap) {
		this.lap = lap;
	}

	public int getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(int lastPosition) {
		this.lastPosition = lastPosition;
	}

	@Override
	public String toString() {
		return "FantaPilota [year=" + year + ", laptimes=" + laptimes + ", points=" + points + ", lap=" + lap
				+ ", lastPosition=" + lastPosition + "]";
	}
	
	
	
}
