package sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import irc.messages.Entity;

public class SQLiteConnector {
	
	Connection dbConnection;
	EntityMultiplexer entityMultiplexer;
	String dbName;
	
	public static void initDriver() throws ClassNotFoundException
	{
		Class.forName("org.sqlite.JDBC");
	}
	public SQLiteConnector(File sqLiteDB) 
	{
		initDBConnection(sqLiteDB);
		dbName = getName(sqLiteDB);
	}
	private String getName(File sqLiteDB) {
		String dbName = sqLiteDB.getName();
		dbName = dbName.substring(0, dbName.indexOf("."));
		return dbName;
	}
	private void initDBConnection(File sqLiteDB){
		try {
			dbConnection = DriverManager.getConnection("jdbc:sqlite:" + sqLiteDB.getAbsolutePath());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	private ResultSet executeQuery(PreparedStatement statement)
	{
		try {
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void add(Entity entity)
	{
		//entityMultiplexer.add(entity);
		//TODO:
	}
	
}
