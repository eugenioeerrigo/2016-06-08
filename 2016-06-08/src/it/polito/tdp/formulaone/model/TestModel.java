package it.polito.tdp.formulaone.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		System.out.println(m.getAllDrivers());
		
		m.simula(1, m.getAllDrivers().get(0));
		for(FantaPilota fp : m.getFantaPiloti()) {
			//if(!fp.isEliminato())
				System.out.println(fp.getYear()+ " "+ fp.getPoints()+ " "+ fp.getLastPosition());
		}
	}
}
