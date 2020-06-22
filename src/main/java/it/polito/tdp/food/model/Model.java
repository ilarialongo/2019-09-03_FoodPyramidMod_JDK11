package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> tipologie;
	private FoodDAO dao;
	
	//variabili per la ricorsione
	private double pesoMax;
	private List<String> camminoMax;
	public Model() {
		this.dao=new FoodDAO();	
	}
	
	public List<String> creaGrafo(int C) {
		this.grafo= new SimpleWeightedGraph<String,DefaultWeightedEdge> (DefaultWeightedEdge.class); 
		this.tipologie= this.dao.getTipologia(C);
		Graphs.addAllVertices(this.grafo, this.tipologie);
		List<Adiacenza> adiacenze= new ArrayList<>(this.dao.getAdiacenze());
		for (Adiacenza a: adiacenze) {
			if (this.grafo.containsVertex(a.getTipo1()) && this.grafo.containsVertex(a.getTipo2())) {
				if (this.grafo.getEdge(a.getTipo1(), a.getTipo2())==null) {
					Graphs.addEdgeWithVertices(this.grafo, a.getTipo1(), a.getTipo2(), a.getPeso());	
				}
			}
		}
		System.out.println("Grafo creato!");
		System.out.println("#VERTICI: "+ this.grafo.vertexSet().size());
		System.out.println("#ARCHI: "+ this.grafo.edgeSet().size());
		return tipologie;
	}
	
	
	public List<Adiacenza> getPorzioniConnesse (String tipologia) {
		List <Adiacenza> risultato= new ArrayList<>();
		List<String> vicini= Graphs.neighborListOf(this.grafo, tipologia);
		for (String v: vicini) {
			int peso= (int) this.grafo.getEdgeWeight(this.grafo.getEdge(tipologia, v));
			Adiacenza a= new Adiacenza (tipologia,v,peso);
			risultato.add(a);
		}
		return risultato;	
	}
	public int vertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int archi() {
		return this.grafo.edgeSet().size();
	}
	/*
	 * Soluzione parziale: cammino che parte dal vertice iniziale
	 * Livello: lunghezza del cammino parziale
	 * Terminazione: Cammino con lunghezza N (cioè N+1 vertici)
	 * Funzione da valutare: peso del cammino
	 * Generazione delle soluzioni: aggiungere gli adiacenti non ancora presenti nel cammino
	 * Avvio della ricorsione: un vertice di partenza
	 *[partenza], livello=1
	 * [partenza, v], livello=2
	 * [partenza,v, ..., vN] , livello=N+1    (N+1 solo perchè il livello parte da 1 e non da 0)
	 */
	
	
	
	public void cercaCammino(int N, String partenza) {
		this.camminoMax=null;
		this.pesoMax=0.0;
		List<String> parziale= new ArrayList<String>();
		parziale.add(partenza);
		search(parziale, 1, N);
	}
	
	private void search(List<String> parziale, int livello, int N) {
		if (livello==N+1) {
			double peso=pesoCammino(parziale);
			if (peso>this.pesoMax) {
				this.pesoMax=peso;
				this.camminoMax=new ArrayList<String>(parziale);
			}
			return;
		}
		
		List<String> vicini= Graphs.neighborListOf(this.grafo, parziale.get(livello-1));
		for (String s: vicini) {
			if (!parziale.contains(s)) {
				parziale.add(s);
				search(parziale, livello+1,N);
				parziale.remove(parziale.size()-1);
			}
		}
	}

	private double pesoCammino(List<String> parziale) {
		double peso=0.0;
		for (int i=1; i<parziale.size();i++) {
			peso+=this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i)));	
		}
		return peso;
	}

	public double getPesoMax() {
		return pesoMax;
	}

	public List<String> getCamminoMax() {
		return camminoMax;
	}
	
	
}
