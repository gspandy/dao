package com.porpoise.dao.generator.model;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.porpoise.generator.model.Cardinality;

public class Reference implements Comparable<Reference> {

	private final Column from;
	private final Column to;
	private final Cardinality cardinality;

	@Override
	public String toString() {
		return String.format("%s ---[%s]---> %s", from, cardinality, to);
	}

	public Reference(final Column f, final Column t, final Cardinality c) {
		from = Preconditions.checkNotNull(f);
		to = Preconditions.checkNotNull(t);
		cardinality = Preconditions.checkNotNull(c);
	}

	public Column getFromTablePrimaryKey() {
		if (from.getTable().hasIdColumn()) {
			return from.getTable().getIdColumn();
		}
		return from;
	}

	public Column getFrom() {
		return from;
	}

	public Column getTo() {
		return to;
	}

	public Cardinality getCardinality() {
		return cardinality;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (cardinality == null ? 0 : cardinality.hashCode());
		result = prime * result + (from == null ? 0 : from.hashCode());
		result = prime * result + (to == null ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Reference other = (Reference) obj;
		if (cardinality != other.cardinality) {
			return false;
		}
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(final Reference o) {
		int result = from.compareTo(o.from);
		if (result == 0) {
			result = to.compareTo(o.to);
			if (result == 0) {
				result = cardinality.compareTo(o.cardinality);
			}
		}
		return result;
	}

	/**
	 * @return the inverse relationship
	 */
	public Reference inverse() {
		return new Reference(to, from, cardinality.inverse());
	}

	/**
	 * @param references
	 * @return a collection which represents the inverse of all references given
	 */
	public static Collection<Reference> inverse(
			final Collection<Reference> references) {
		return Collections2.transform(references,
				new Function<Reference, Reference>() {
					@Override
					public Reference apply(final Reference r) {
						return r.inverse();
					}
				});
	}

	/**
	 * Similar to inverse, but the cardinality remains unchanged
	 * 
	 * e.g. given the column
	 * 
	 * A -OneToMany-> B
	 * 
	 * The inverse is
	 * 
	 * B -ManyToOne-> A
	 * 
	 * and the flip is
	 * 
	 * B -OneToMany-> A
	 * 
	 * @return the flip relationship
	 */
	public Reference flip() {
		return new Reference(to, from, cardinality);
	}

	/**
	 * @param references
	 * @return a collection which represents the flip of all references given
	 */
	public static Collection<Reference> flip(
			final Collection<Reference> references) {
		return Collections2.transform(references,
				new Function<Reference, Reference>() {
					@Override
					public Reference apply(final Reference r) {
						return r.flip();
					}
				});
	}
}
