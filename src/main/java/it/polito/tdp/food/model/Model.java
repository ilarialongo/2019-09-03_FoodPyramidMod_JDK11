package it.polito.tdp.food.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	public Model() {
		
	}
	
	public void creaGrafo(int C) {
		this.grafo= new SimpleWeightedGraph<String,DefaultWeightedEdge> (DefaultWeightedEdge.class); 
		
	}
}
