package com.porpoise.generator.model;

public enum Cardinality {

	OneToOne {
		@Override
		public Cardinality inverse() {
			return OneToOne;
		}
	}, //
	OneToMany {
		@Override
		public Cardinality inverse() {
			return ManyToOne;
		}

		@Override
		public boolean isList() {
			return true;
		}
	}, //
	ManyToMany {
		@Override
		public Cardinality inverse() {
			return ManyToMany;
		}

		@Override
		public boolean isList() {
			return true;
		}
	}, //
	ManyToOne {
		@Override
		public Cardinality inverse() {
			return OneToMany;
		}
	};//

	public abstract Cardinality inverse();

	public boolean isList() {
		return false;
	}
}
