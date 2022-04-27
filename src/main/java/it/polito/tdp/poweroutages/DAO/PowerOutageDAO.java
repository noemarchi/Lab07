package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Black;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutageDAO {
	
	/**
	 * Per ottenere la lista di tutti i Nerc presenti nel database
	 * @return
	 */
	public List<Nerc> getNercList() 
	{

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try 
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) 
			{
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} 
		catch (SQLException e) 
		{
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	/**
	 * Per ottenere la lista di blackout avvenuti nel Nerc passato come parametro
	 * @param nercId id del Nerc
	 * @return lista con id, numero persone, anno e ore, data inizio e fine del blackout
	 */
	public List<Black> getBlackByNerc(int nercId)
	{
		String sql = "SELECT id, customers_affected AS n, YEAR(date_event_began) AS anno, "
				+ "(UNIX_TIMESTAMP(date_event_finished)-UNIX_TIMESTAMP(date_event_began)) / 3600 AS ore, "
				+ "date_event_began AS start, date_event_finished AS end "
				+ "FROM PowerOutages "
				+ "WHERE nerc_id = ?";
		
		List<Black> black = new ArrayList<Black>();
		
		try 
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, nercId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) 
			{
				Black b = new Black(rs.getInt("id"), rs.getInt("n"), rs.getInt("anno"), rs.getInt("ore"), 
						rs.getDate("start").toLocalDate(), rs.getDate("end").toLocalDate());
				black.add(b);
			}

			conn.close();
			return black;

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
