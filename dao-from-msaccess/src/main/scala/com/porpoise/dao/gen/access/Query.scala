package com.porpoise.dao.gen.access

import java.sql._
import scala.collection.mutable._

/**
 * This trait conforms to the signature required by the Query
 */
trait ResultSetVisitor {
	def onResult(row : Int, result : ResultSet, columnNames : List[String]) =
		println("row#" +row+ "  " + columnNames)
}

/**
 * @author Aaron
 */
class Query(val sql: String) {

	def queryWith(connection: Connection, visitor : ResultSetVisitor) : Unit = {
		queryWith(connection, visitor.onResult _)
	}

	/**
	 * @param connection
	 * @param visitor
	 */
	def queryWith(connection: Connection, visitor : (Int, ResultSet, List[String]) => Unit) : Unit = {

		import _root_.com.porpoise.dao.Query._
		
		val statement = connection.prepareStatement(sql);
		using (statement) {statement => {
			statement.execute()
			val queryResultSet : ResultSet = statement.getResultSet()
			using (queryResultSet) { queryResultSet => {
				if(queryResultSet.next())
				{
					var row = 1
					val metaData = queryResultSet.getMetaData()
					val colCount = metaData.getColumnCount()

					do {
						val columnLabels = new ArrayBuffer[String](); 
							for(c <- 1 to colCount) {
								columnLabels + metaData.getColumnLabel(c)
							}
						visitor(row, queryResultSet, List(columnLabels))
						row += 1;
					} while(queryResultSet.next())
				}
			} }
		} }
	}
}

/**
 * @author Aaron
 */
object Query {

	/**
	 * @param queryString
	 * @return
	 */
	implicit def string2Query(queryString : String) =  new Query(queryString)
	
	/**
	 * @param closable
	 * @param logic
	 * @return
	 */
	def using[Closable <: {def close() : Unit}, Logic] (closable : Closable)(logic: Closable => Logic ) : Logic = {
			try {
				return logic(closable)
			} finally {
				closable close
			}
	}
}