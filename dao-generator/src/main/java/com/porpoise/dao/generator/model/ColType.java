package com.porpoise.dao.generator.model;

public enum ColType
{
	String, //
	Text
	{
		@Override
		public String getJavaName()
		{
			return "String";
		}
	}, //
	Boolean, //
	Integer, //
	Date, //
	Timestamp
	{
		@Override
		public String getJavaName()
		{
			return "Date";
		}
	}, //
	BigDecimal, //
	Long, //
	Short, // 
	Bytes;

	public String getJavaName()
	{
		return name();
	}//

	public static ColType forClass(final Class<?> c1ass)
	{
		for (final ColType t : values())
		{
			if (c1ass.getSimpleName().equals(t.name()))
				return t;
		}

		throw new IllegalArgumentException("Unknown class:" + c1ass);
	}

}
