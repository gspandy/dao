package com.porpoise.dao.gen.access

import java.sql._;

object AccessConnection {

	val DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	Class.forName(DRIVER)

	private val prefix = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	private val suffix = ";DriverID=22;READONLY=false}";

	// Initialize the JdbcOdbc Bridge Driver

	def newConnection(database : String) : Connection = 
	{
		newConnection(database, "", "");
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	def newConnection(filename : String, user : String, pw : String) : Connection =
	{
		val database = filename.trim();
		val databaseURL = prefix + database + suffix;
		return DriverManager.getConnection(databaseURL, user, pw);
	}
}