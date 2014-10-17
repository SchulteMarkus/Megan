package com.megan.helper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.data.neo4j.conversion.Result;

public final class ListHelper {

	public static <T> Set<T> toSet(final Iterator<T> iter) {
		final Set<T> set = new HashSet<>();
		while (iter.hasNext()) {
			set.add(iter.next());
		}

		return set;
	}

	public static <T> Set<T> toSet(final Result<T> result) {
		return ListHelper.toSet(result.iterator());
	}

	private ListHelper() {
	}
}
