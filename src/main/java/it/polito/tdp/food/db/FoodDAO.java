package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDAO {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<String> getTipologia(int C) {
		String sql="SELECT DISTINCT p.portion_display_name " + 
				"FROM portion as p " + 
				"WHERE p.calories<?";
		List<String> risultato= new ArrayList<String>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, C);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				risultato.add(res.getString("portion_display_name"));	
			}
			
			conn.close();
			return risultato;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}	
	}
	
	public List<Adiacenza> getAdiacenze() {
		String sql="SELECT p1.portion_display_name as pd1, p2.portion_display_name pd2, COUNT(DISTINCT p1.food_code) as peso " + 
				"FROM portion as p1, portion as p2 " + 
				"WHERE p1.food_code=p2.food_code AND p1.portion_display_name>p2.portion_display_name AND p1.portion_id<>p2.portion_id " + 
				"GROUP BY p1.portion_display_name, p2.portion_display_name";
		List<Adiacenza> risultato= new ArrayList<Adiacenza> ();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Adiacenza a= new Adiacenza (res.getString("pd1"), res.getString("pd2"), res.getInt("peso"));
				risultato.add(a);
			}
			
			conn.close();
			return risultato;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}	
		
		
	}
	
	

}
