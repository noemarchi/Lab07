package it.polito.tdp.poweroutages.model;

import java.time.LocalDate;

public class Black {
	
	private int id;
	private int n;
	private int anno;
	private int ore;
	private LocalDate start;
	private LocalDate end;
	
	public Black(int id, int n, int anno, int ore, LocalDate start, LocalDate end) 
	{
		super();
		this.id = id;
		this.n = n;
		this.anno = anno;
		this.ore = ore;
		this.start = start;
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public int getN() {
		return n;
	}

	public int getAnno() {
		return anno;
	}

	public int getOre() {
		return ore;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return "persone=" + n + ", anno=" + anno + ", ore=" + ore + ", inizo=" + start + ", fine=" + end;
	}
	
	
	

}
