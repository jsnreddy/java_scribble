/**
 * 
 */
package com.jsn.codingPractice;

import java.util.HashMap;

/**
 * @author Surendranath Reddy
 *
 */
public class LRUCache {

	int capacity;
	HashMap<Integer, Node> map = new HashMap<Integer, Node>();
	Node head = null;
	Node end = null;

	public void removeNode(Node node) {
		if (node.next == null) {
			end = node.pre;
		} else {
			node.next.pre = node.pre;
		}

		if (node.pre == null) {
			head = node.next;
		} else {
			node.pre.next = node.next;
		}

	}

	public void setHead(Node node) {
		node.next = head;
		node.pre = null;

		if (head != null) {
			head.pre = node;
		}
		head = node;

		if (end == null) {
			end = head;
		}
	}

	public void setPage(Node node) {
		if (map.containsKey(node.key)) {
			removeNode(node);
			setHead(node);
		} else {
			if (map.size() >= capacity) {
				removeNode(this.end);
				setHead(node);
			} else {
				setHead(node);
			}

			map.put(node.key, node);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

class Node {
	int key;
	int value;
	Node pre;
	Node next;

	public Node(int key, int value) {
		this.key = key;
		this.value = value;
	}
}
