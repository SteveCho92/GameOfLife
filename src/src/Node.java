package src;

import java.io.Serializable;

class Node<E> implements Serializable {
	protected E key;
	protected Node<E> prev;
	protected Node<E> next;

	public Node(E e) {
		this.key = e;
		prev = null;
		next = null;
	}

	public E intValue() {

		return key;
	
	}

}