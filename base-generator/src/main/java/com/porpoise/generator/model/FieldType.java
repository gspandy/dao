package com.porpoise.generator.model;

public enum FieldType {
	String, //
	Text {
		@Override
		public String getJavaName() {
			return "String";
		}
	}, //
	Boolean, //
	Integer, //
	Date {
		@Override
		public String getResultSetAccessorName() {
			return "getTime";
		}
	}, //
	Timestamp {
		@Override
		public String getResultSetAccessorName() {
			return "getTime";
		}

		@Override
		public String getJavaName() {
			return "Date";
		}
	}, //
	BigDecimal, //
	Long, //
	Short, //
	Bytes {
		@Override
		public String getJavaName() {
			return "byte[]";
		}

		@Override
		public String getResultSetAccessorName() {
			return "getBytes";
		}
	};

	public String getJavaName() {
		return name();
	}

	public String getResultSetAccessorName() {
		return "get" + getJavaName();
	}

	public static FieldType forClass(final Class<?> c1ass) {
		for (final FieldType t : values()) {
			if (c1ass.getSimpleName().equals(t.name())) {
				return t;
			}
		}

		throw new IllegalArgumentException("Unknown class:" + c1ass);
	}

}
