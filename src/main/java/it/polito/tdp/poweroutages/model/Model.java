package it.polito.tdp.poweroutages.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	
	private List<Black> all;
	private List<Black> best;
	
	private int personeBest;
	
	private int anniMax;
	private int oreMax;
	
	public Model() 
	{
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() 
	{
		return podao.getNercList();
	}
	
	public List<Black> getWorst(Nerc nerc, int anni, int ore)
	{
		// ripristino la soluzione migliore
		this.best = new LinkedList<Black>();
		this.personeBest = -1;
		
		// creo la soluzione parziale vuota
		List<Black> parziale = new LinkedList<Black>();
		
		// prendo la lista di tutti i possibili blackout
		this.all = podao.getBlackByNerc(nerc.getId());
		
		// salvo anni e ore
		this.anniMax = anni;
		this.oreMax = ore;
		
		// avvio la ricorsione
		cerca(parziale, 0);
		
		
		return best;
	}

	private void cerca(List<Black> parziale, int livello) 
	{
		// CASI TERMINALI
		
		// numero totale di ore > Y
		if(! oreOk(parziale))
		{
			return;
		}
		
		//intervallo anni > X
		if(! anniOk(parziale))
		{
			return;
		}
		
		// è la soluzione migliore?
		int persone = sommaPersone(parziale);
		
		if(persone > this.personeBest)
		{
			this.personeBest = persone;
			this.best = new LinkedList<Black>(parziale);
		}
		
		// ho già aggiunto tutti i blackout
		if(livello == all.size())
		{
			return;
		}
		
		
		// CASO NORMALE
		
		// provo ad aggiungere il blackout 		all[livello]
		parziale.add(all.get(livello));
		this.cerca(parziale, livello + 1);
		
		// provo ad NON aggiungere il blackout 		all[livello]
		parziale.remove(all.get(livello));
		this.cerca(parziale, livello + 1);
		
	}
	
	private int sommaPersone(List<Black> parziale) 
	{
		int x = 0;
		
		for(Black b: parziale)
		{
			x = x + b.getN();
		}
		
		return x;
	}

	private boolean anniOk(List<Black> parziale) 
	{
		int annoLittle = Integer.MAX_VALUE;
		int annoBig = Integer.MIN_VALUE;
		
		for(Black b: parziale)
		{
			if(b.getAnno() < annoLittle)
			{
				annoLittle = b.getAnno();
			}
			
			if(b.getAnno() > annoBig)
			{
				annoBig = b.getAnno();
			}
		}
		
		if((annoBig - annoLittle) > anniMax)
		{
			return false;
		}
		
		return true;
	}

	private boolean oreOk(List<Black> parziale)
	{
		int ore = 0;
		
		for(Black b: parziale)
		{
			ore = ore + b.getOre();
		}
		
		if(ore > oreMax)
		{
			return false;
		}
		
		return true;
	}

}
