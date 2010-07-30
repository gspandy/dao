package com.porpoise.dao.gen.access

abstract sealed case class ColumnType {
//	def parse(str : String) : ColumnType = {
//			if (matches(str))
//				return this;
//	}
	
	def matches(dbString : String) : Boolean
}
case class TypeString extends ColumnType {
	override def matches(str : String) : Boolean = {
		str.toLowerCase().trim() match {
			case s : String if s.startsWith("varchar") => true
			case _ => false
		}
	}
}
case class TypeLong extends ColumnType {
	override def matches(str : String) : Boolean = {
		str.toLowerCase().trim() match {
			case s : String if s.contains("long") => true
			case s : String if s.contains("int") => true
			case _ => false
		}
	}
}
case class TypeDate extends ColumnType{
	override def matches(str : String) : Boolean = {
		str.toLowerCase().trim() match {
			case s : String if s.contains("date") => true
			case _ => false
		}
	}
}

@scala.reflect.BeanProperty
case class Column(val name : String, val columnType: ColumnType)

@scala.reflect.BeanProperty
case class Table(val name : String, val columns : List[Column])