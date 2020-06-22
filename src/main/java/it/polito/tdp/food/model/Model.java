package it.polito.tdp.food.model;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDAO dao;
	public Model() {
		this.dao=new FoodDAO();	
	}
	
	public void creaGrafo(int C) {
		this.grafo= new SimpleWeightedGraph<String,DefaultWeightedEdge> (DefaultWeightedEdge.class); 
		Graphs.addAllVertices(this.grafo, this.dao.getTipologia(C));
	}
}
