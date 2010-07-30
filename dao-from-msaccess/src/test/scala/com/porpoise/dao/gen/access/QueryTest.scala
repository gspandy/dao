package com.porpoise.dao.gen.access

import java.sql._;
import com.porpoise.dao.access._;
import com.porpoise.dao._;
import com.porpoise.dao.Query._;
import org.scalatest.matchers.ShouldMatchers
import org.scalatest._
import org.scalatest.events._;

import org.scalatest.junit._;
import org.junit.Assert._;
import org.junit._;

class QueryTest extends AssertionsForJUnit  {
	
	private var conn : Connection = null;
	
	private def pathToTestMdbFile() : String = {
		val fileResource = getClass().getClassLoader().getResource("access.mdb");
		val fileName = fileResource.getFile();
		val file = new java.io.File(fileResource.toURI())
		return file.getAbsolutePath();
	}
	
	@Before
	def setup()
	{
		println("setting up");
		conn = AccessConnection.newConnection(pathToTestMdbFile)
	}

	@After
	def teardown()
	{
		println("tearing down");
		conn.close()
	}
	
	/**
	 *  
	 */
	@Test
	def test_connectToAccess() {
		val v = new ResultSetVisitor {}
		"select * from AaronTable".queryWith(conn, v.onResult _)
	}
}