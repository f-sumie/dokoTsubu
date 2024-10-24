package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MuttersDAO {
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/dokotsubu?serverTimezone=JST";

	private final String DB_USER = "root";
	private final String DB_PASS = "root";

	public List<Mutter> findAll() {
		List<Mutter> mutterList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "SELECT * FROM MUTTERS ORDER BY ID DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("ID");
				String userName = rs.getString("NAME");
				String text = rs.getString("TEXT");

				Mutter mutter = new Mutter(id, userName, text);
				mutterList.add(mutter);
			}
		}
		catch(SQLException e) {
				e.printStackTrace();
				return null;
		}
		return mutterList;
	}

	public boolean create(Mutter mutter) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}


		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "INSERT INTO MUTTERS(NAME, TEXT) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, mutter.getUserName());
			pStmt.setString(2, mutter.getText());

			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}