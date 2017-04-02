/**
 * 
 */
package com.jsn.codingPractice;

/**
 * @author Surendranath Reddy
 *
 */
public class LinkedList {

	static Node head;

	static class Node {
		int data;
		Node next;

		Node(int d) {
			data = d;
			next = null;
		}

		public String toString() {
			return this.data + "->";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.head = new Node(50);
		list.head.next = new Node(20);
		list.head.next.next = new Node(15);
		list.head.next.next.next = new Node(4);
		list.head.next.next.next.next = new Node(10);

		// Creating a loop for testing 
		head.next.next.next.next.next = head.next.next;

		list.detectAndRemoveLoop(head);

		Node first = head;
		while (first != null) {
			System.out.print(first.toString());
			first = first.next;
		}
		System.out.print("null");
	}

	/**
	 * @param head
	 */
	private void detectAndRemoveLoop(Node node) {

		Node slow = node;
		Node fast = node;

		boolean status = false;

		while (slow != null && fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;

			if (slow == fast) {
				status = true;
				removeLoop(slow, node);
				break;
			}
		}
		if (status) {
			System.out.println("Loop detected");
		} else {
			System.out.println("No loop");
		}
	}

	/**
	 * @param slow
	 * @param node
	 */
	private void removeLoop(Node slow, Node node) {
		Node p1 = null, p2 = null;

		p1 = node;

		while (true) {
			p2 = slow;
			while (p2.next != slow && p2.next != p1) {
				//				System.out.println("while : " + p2.toString());
				p2 = p2.next;
			}

			if (p2.next == p1) {
				//				System.out.println("woooo : " + p1.toString());
				//				System.out.println("Loop broken");
				break;
			}

			p1 = p1.next;
		}
		System.out.println("loop broken : " + p2.toString() + "null");
		p2.next = null;
	}

}
