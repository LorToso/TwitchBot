package sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import irc.messages.Join;

public class SQLQueryGenerator {
	Connection dbConnection;
	
	public SQLQueryGenerator(Connection connection)
	{
		dbConnection = connection;
	}
	public PreparedStatement generateQuery(Join join)
	{
		try {
			PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO joins (channel,sender,timestamp) VALUES (?,?,?);");
			statement.setString(0, join.channel);
			statement.setString(1, join.sender);
			statement.setLong(2, join.timestamp);
			return statement;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			return dbConnection.prepareStatement(";");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
}
