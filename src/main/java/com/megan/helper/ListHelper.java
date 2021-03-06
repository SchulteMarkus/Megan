package com.megan.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.conversion.Result;

public final class ListHelper {

	public static <T> List<T> toList(final Iterator<T> iter) {
		final List<T> list = new ArrayList<>();
		while (iter.hasNext()) {
			list.add(iter.next());
		}

		return list;
	}

	public static <T> List<T> toList(final Result<T> result) {
		return ListHelper.toList(result.iterator());
	}

	public static <T> List<T> toList(final Set<T> set) {
		return ListHelper.toList(set.iterator());
	}

	public static <T> Set<T> toSet(final Iterable<T> iter) {
		return ListHelper.toSet(iter.iterator());
	}

	public static <T> Set<T> toSet(final Iterator<T> iter) {
		final Set<T> set = new LinkedHashSet<>();
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
