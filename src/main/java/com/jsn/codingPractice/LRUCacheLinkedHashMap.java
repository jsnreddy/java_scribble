/**
 * 
 */
package com.jsn.codingPractice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Surendranath Reddy
 *
 */
public class LRUCacheLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	int capacity;

	/**
	 * 
	 */
	public LRUCacheLinkedHashMap(int capacity) {
		super(capacity, 0.75f, true);
		this.capacity = capacity;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry eldest) {
		return this.size() > this.capacity;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LRUCacheLinkedHashMap<Integer, Integer> cache = new LRUCacheLinkedHashMap<Integer, Integer>(2);
		cache.put(1, 1);
		cache.put(2, 2);
		cache.put(1, 1);
		cache.put(3, 3);
		cache.put(1, 1);

		System.out.println(cache);
	}

}
