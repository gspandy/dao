package com.porpoise.dao.generator.gen;

import java.util.Iterator;

import com.google.common.base.CaseFormat;
import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Table;

public class DaoContext
{

	private final Table table;
	private final String packageName;

	public DaoContext(final String packageName, final Table t)
	{
		this.packageName = packageName;
		this.table = t;

	}

	/**
	 * @return the table
	 */
	public Table getTable()
	{
		return this.table;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName()
	{
		return this.packageName;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageNameAsPath()
	{
		return this.packageName.replace('.', '/');
	}

	public String getJavaName()
	{
		return this.table.getJavaName();
	}

	public String getTableName()
	{
		return this.table.getTableName();
	}

	public String getColumnDeclarations()
	{
		final StringBuilder b = new StringBuilder();

		for (final Iterator<Column> iter = this.table.getColumns().iterator(); iter.hasNext();)
		{
			final Column c = iter.next();
			b.append("final ");
			b.append(c.getJavaTypeName());
			b.append(" ");
			b.append(c.getNameAsProperty());
			if (iter.hasNext())
			{
				b.append(", ");
			}
		}
		return b.toString();
	}

	public String getColumnAccessorMethods(final String varName)
	{
		final StringBuilder b = new StringBuilder();

		for (final Iterator<Column> iter = this.table.getColumns().iterator(); iter.hasNext();)
		{
			final Column c = iter.next();
			b.append(varName).append(".").append(c.getNameAsAccessor()).append("()");
			if (iter.hasNext())
			{
				b.append(", ");
			}
		}
		return b.toString();
	}

	public String getColumnParameterList()
	{
		final StringBuilder b = new StringBuilder();

		for (final Iterator<Column> iter = this.table.getColumns().iterator(); iter.hasNext();)
		{
			final Column c = iter.next();
			b.append(c.getNameAsProperty());
			if (iter.hasNext())
			{
				b.append(", ");
			}
		}
		return b.toString();
	}

	public String getTestValues()
	{
		final StringBuilder b = new StringBuilder();

		for (final Iterator<Column> iter = this.table.getColumns().iterator(); iter.hasNext();)
		{
			final Column c = iter.next();
			b.append(newTestValue(c));
			if (iter.hasNext())
			{
				b.append(", ");
			}
		}
		return b.toString();
	}

	private String newTestValue(final Column c)
	{
		switch (c.getType())
		{
		case BigDecimal:
		{
			return "Integer.valueOf(1)";
		}
		case String:
		case Text:
			return String.format("\"%s\"", c.getName());
		case Boolean:
			return String.format("Boolean.TRUE");
		case Long:
			return "Long.valueOf(1)";
		case Integer:
			return "Integer.valueOf(1)";
		case Short:
			return "Short.valueOf(1)";
		case Bytes:
			return "byte[0]";
		case Timestamp:
		case Date:
			return "new java.sql.Date()";
		}
		throw new IllegalArgumentException(String.format("Unknown column type for %s: %s", c.toString(), c.getType()));
	}

	public String getColumnNames()
	{
		final StringBuilder b = new StringBuilder();

		for (final Iterator<Column> iter = this.table.getColumns().iterator(); iter.hasNext();)
		{
			final Column c = iter.next();
			b.append(c.getName());
			if (iter.hasNext())
			{
				b.append(", ");
			}
		}
		return b.toString();
	}

	public Iterable<Column> getColumns()
	{
		return this.table.getColumns();
	}

	public String asProperty(final String name)
	{
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name.toUpperCase());
	}
}
