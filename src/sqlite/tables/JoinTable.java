package sqlite.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import irc.messages.Join;

public class JoinTable extends EntityTable<Join>{

	@Override
	Statement createTableIfNotExists(Connection dbConnection) throws SQLException {
		Statement statement = dbConnection.prepareStatement(
				"CREATE TABLE joins IF NOT EXISTS ("
				+ "id int NOT NULL AUTO_INCREMENT, "
				+ "channel varchar(255), "
				+ "user varchar(255), "
				+ "timestamp bigint);");
		return statement;
	}

	@Override
	Statement add(Connection dbConnection, Join entity) throws SQLException {
		PreparedStatement statement = dbConnection.prepareStatement(
				"INSERT INTO joins (channel,user,timestamp) VALUES (?,?,?);");
		statement.setString(0, entity.channel);
		statement.setString(1, entity.sender);
		statement.setLong(2, entity.timestamp);
		return statement;
	}
	
	
}
