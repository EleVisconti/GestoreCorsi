package it.polito.tdp.corsi.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import it.polito.tdp.corsi.model.Corso;
//ogni entità avrà la sua classe nel model
public class CorsoDao {
  public List <Corso> getCorsobyPeriodo(int periodo) {
	  //togliere tutti i \n e mettere sempre spazi alla fine di ogni riga!!!
	String sql = "SELECT * "
			+"FROM corso "
	        + "where pd = ?";

	List <Corso> result = new ArrayList<Corso>();
	try {
		Connection conn = ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, periodo); //posizione del parametro, l'indicizzazione parte da 1
		ResultSet rs = st.executeQuery();
		 while(rs.next()) {  //nome della colonna del database (non della mia classe java se fossero eventualmente diversi)
		Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
		 result.add(c);
		}
		conn.close();
		st.close();
		rs.close();
		return result;
		
	}catch (SQLException e) {
		System.err.println("Errore nel DAO");
		e.printStackTrace();
		return null;
	}
  }
  
  public Map <Corso, Integer> getIscritti(int periodo){
	 String sql="select c.codins, c.crediti, c.nome, c.pd, count(*) as n "
  +"from corso c, iscrizione i "+"where c.codins = i.codins and c.pd = ? "
			  +"group by c.codins, c.crediti, c.nome, c.pd";
  Map <Corso, Integer> result = new HashMap<Corso, Integer>();
  try {Connection conn = ConnectDB.getConnection();
  PreparedStatement st = conn.prepareStatement(sql);
  st.setInt(1, periodo); 	
  ResultSet rs = st.executeQuery();
  while(rs.next()) {  //nome della colonna del database (non della mia classe java se fossero eventualmente diversi)
		Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
		 result.put(c, rs.getInt("n") );}
		 conn.close();
	     st.close();
		 rs.close();
	     return result;
		 
  }catch  (SQLException e) {
		System.err.println("Errore nel DAO");
		e.printStackTrace();
		return null;	  
  }
 }
  
}
