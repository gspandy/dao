package com.porpoise.generator.model;

public interface IField {

	public String getJavaName();

	public String getJavaTypeName();

	public String getNameAsProperty();

	public boolean isPrimaryKey();

	public String getNameAsAccessor();

	boolean isDate();

	boolean isBigDecimal();

	boolean isByteArray();

	public String getJavaInterfaceName();

}
