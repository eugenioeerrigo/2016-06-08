package it.polito.tdp.formulaone.model;

import java.util.PriorityQueue;

public class Simulazione {

	private Model model;          //condivido Model per poter utilizzare i suoi metodi
	
	private PriorityQueue<Evento> queue;
	
	public Simulazione(Model model) {
		this.model = model;
		queue = new PriorityQueue<>();
	}
	
	//SIMULAZIONE: schedulare gli eventi per ogni passaggio dal via per il pilota considerato
	
	public void simula() {
		//inizializzazione: eventi relativi al primo giro
		for(FantaPilota fp : model.getFantaPiloti()) {
			queue.add(new Evento(fp, 0, fp.getLaptimes().get(0)));
		}
		
		//processo gli eventi
		while(!queue.isEmpty()) {
			Evento e = queue.poll();
			
			//assegnare dei punti al pilota
			int count = 0;                                   //posizione del pilota alla fine del giro considerato
			for(FantaPilota fp : model.getFantaPiloti()) {
				if(!fp.isEliminato() && fp.getLap() >= e.getLap())
					count++;
			} 
			
			if(count < e.getFp().getLastPosition()) {     //se posizione pilota migliore di quella precendente, aggiorno punteggio
				int points = e.getFp().getPoints() +1;
				e.getFp().setPoints(points);
			}
			
			e.getFp().setLap(e.getLap());
			e.getFp().setLastPosition(count);
			
			
			//capire se il pilota è stato doppiato
			boolean doppiato = false;
			for(FantaPilota fp : model.getFantaPiloti()) {
				if(!fp.isEliminato() && fp.getLap() > e.getLap()+1)
					doppiato = true;
			}
			
			if(doppiato) {
				e.getFp().setEliminato(true);
				System.out.println("Pilota eliminato "+e.getFp().getYear()+"\n");
			} else {
				//Inserire nuovo evento relativo a giro successivo
				if(e.getFp().getLaptimes().size()>e.getLap()+1) {     //se la lista contiene ancora eventi da aggiungere
					int t = e.getFp().getLaptimes().get(e.getLap()+1);
					queue.add(new Evento(e.getFp(), e.getLap()+1, e.getTime()+t));
				}
			}
			
			
		}
	}
}
