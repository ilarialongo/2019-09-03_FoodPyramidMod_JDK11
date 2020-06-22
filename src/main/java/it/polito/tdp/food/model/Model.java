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
}
