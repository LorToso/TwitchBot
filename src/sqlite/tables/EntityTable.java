package sqlite.tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import irc.messages.Entity;

public abstract class EntityTable<E extends Entity> {
	abstract Statement createTableIfNotExists(Connection dbConnection) throws SQLException;
	abstract Statement add(Connection dbConnection, E entity) throws SQLException;
}
