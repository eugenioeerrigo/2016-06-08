package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriverIdMap;
import it.polito.tdp.formulaone.model.FantaPilota;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Integer> getAllYearsOfRace() {

		String sql = "SELECT year FROM races ORDER BY year";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Integer> list = new ArrayList<>();
			while (rs.next()) {
				list.add(rs.getInt("year"));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	public List<Season> getAllSeasons() {

		String sql = "SELECT year, url FROM seasons ORDER BY year";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Driver> getAllDrivers(DriverIdMap dim) {

		String sql = "Select DISTINCT drivers.driverId, forename, surname from drivers";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			List<Driver> drivers = new ArrayList<>();
			while (rs.next()) {
				drivers.add(dim.get(new Driver(rs.getInt("driverId"), rs.getString("forename"), rs.getString("surname"))));
			}

			conn.close();
			return drivers;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Driver> getAllDriversBySeason(Season s, DriverIdMap dim) {

		String sql = "Select DISTINCT drivers.driverId, forename, surname\n" + "from drivers, races, results\n"
				+ "where races.year = ?\n" + "and results.raceId = races.raceId\n"
				+ "and results.driverId is not null\n" + "and results.driverId = drivers.driverId";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getYear());
			ResultSet rs = st.executeQuery();

			List<Driver> drivers = new ArrayList<>();
			while (rs.next()) {
				drivers.add(dim.get(new Driver(rs.getInt("driverId"), rs.getString("forename"), rs.getString("surname"))));
			}

			conn.close();
			return drivers;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	public List<FantaPilota> getFantaPiloti(int circuitId, Driver driver) {         //lista dei fantapiloti, ossia degli ANNI in cui quel pilota ha gareggiato in quel circuito
		
		//Query per ottenere la lista dei fantapiloti (YEAR)
		String sql = "select distinct year from races, laptimes, circuits, drivers where circuits.circuitId = ? and drivers.driverId = ? " + 
				"and laptimes.driverId = drivers.driverId and laptimes.raceId = races.raceId and races.circuitId = circuits.circuitId ";
		
		//Query per ottenere la lista dei laptimes
		String sql2 = "select milliseconds from races, laptimes, circuits, drivers where circuits.circuitId = ? and drivers.driverId = ? " + 
				"and laptimes.driverId = drivers.driverId and laptimes.raceId = races.raceId and races.circuitId = circuits.circuitId "
				+ "and year = ? order by lap ";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			st.setInt(1, circuitId);
			st.setInt(2, driver.getDriverId());
			ResultSet rs = st.executeQuery();

			List<FantaPilota> results = new ArrayList<>();
			while (rs.next()) {
				int year = rs.getInt("year");
				FantaPilota fp = new FantaPilota(year, new ArrayList<Integer>());
				results.add(fp);
			}

			conn.close();
			
			for(FantaPilota fp : results) {
				conn = ConnectDB.getConnection();
				st = conn.prepareStatement(sql2);
			
				st.setInt(1, circuitId);
				st.setInt(2, driver.getDriverId());
				st.setInt(3, fp.getYear());
				rs = st.executeQuery();
	
				List<Integer> laptimes = new ArrayList<>();
				while (rs.next()) {
					laptimes.add(rs.getInt("milliseconds"));
				}
	
				conn.close();
				fp.setLaptimes(laptimes);
			}
			
			return results;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
}
