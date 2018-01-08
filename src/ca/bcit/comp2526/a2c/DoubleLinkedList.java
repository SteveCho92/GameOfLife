package ca.bcit.comp2526.a2c;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Double linked list links Node<E> with each other to form a double linked
 * list.
 * 
 * @author Steve Cho
 * @version 2017
 *
 * @param <E>
 *            generic type that can be linked in this list
 */
public class DoubleLinkedList<E> implements Iterable<E>, Serializable {

    private static final long serialVersionUID = 2147980590749975329L;
    private Node<E> head;
    private Node<E> tail;
    private Node<E> cur;
    private int length;

    /**
     * Constructs empty DoubleLinkedList.
     */
    public DoubleLinkedList() {
        head = null;
        tail = null;
        cur = null;
    }

    /**
     * Inner class Node<E> holds elements to be linked.
     * 
     * @author Steve Cho
     * @version 2017
     *
     * @param <E>
     *            generic type that can be held in this a Node
     */
    public static class Node<E> implements Serializable {

        private static final long serialVersionUID = 7199897679963030526L;
        private E key;
        private Node<E> prev;
        private Node<E> next;

        /**
         * Constructs Node with generic type as its key.
         * 
         * @param e
         *            generic type to be used as key in Node
         */
        public Node(E e) {
            this.key = e;
            prev = null;
            next = null;
        }

        /**
         * Returns the key.
         * 
         * @return E the key in this Node
         */
        public E intValue() {

            return key;
        }
    }

    /**
     * Adds the E e to the front of the list.
     * 
     * @param e
     *            adds E to the front of the list
     * @throws CouldNotAddException
     *             exception when null is added
     */
    public void addToFront(E e) throws CouldNotAddException {

        if (e == null) {

            throw new CouldNotAddException("can't add null to the list");

        }

        Node<E> temp = new Node<E>(e);

        // when nothing was in the list
        if (head == null) {
            head = temp;
            tail = temp;
        } else {

            // make temp the new head with proper linkage
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
        length++;

    }

    /**
     * Removes the front element of the list.
     * 
     * @return Node<E> the front element of the list
     * 
     * @throws CouldNotRemoveException
     *             for when no elements to remove
     */
    public E removeFromFront() throws CouldNotRemoveException {

        if (head == null) {
            throw new CouldNotRemoveException("Could not remove from front");
        }
        Node<E> removeHead = head;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        length--;

        return removeHead.key;
    }

    /**
     * Adds element to the end of the list.
     * 
     * @param e
     *            element to add to end of the list
     * @throws CouldNotAddException
     *             for when null is added
     */
    public void addToEnd(E e) throws CouldNotAddException {
        if (e == null) {

            throw new CouldNotAddException("can't add null to the list");

        }
        Node<E> temp = new Node<E>(e);
        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;

        }
        length++;

    }

    /**
     * Removes last element from the list.
     * 
     * @return Node<E> the removed element
     * 
     * @throws CouldNotRemoveException
     *             for when no element to remove
     */
    public E removeFromEnd() throws CouldNotRemoveException {

        Node<E> toRemove = tail;
        if (tail == null) {
            throw new CouldNotRemoveException("Could not remove from end");
        }
        if (tail == head) {
            head = null;
            tail = null;
        }

        if (tail != null) {
            tail = tail.prev;
            tail.next = null;
        }
        length--;

        return toRemove.key;
    }

    @Override
    /**
     * Implements iterator for DoubleLinkedList.
     * 
     * @return Iterator<E>
     */
    public Iterator<E> iterator() {
        // final DoubleLinkList<E> linkedList = this;

        // returning an annonymous inner class
        return new Iterator<E>() {

            @Override
            public boolean hasNext() {
                if (head == null) {
                    return false;
                }
                return cur != tail;
            }

            @Override
            public E next() {
                if (cur == null) {
                    cur = head;
                    return cur.key;
                }
                if (cur.next == null) {
                    cur = null;
                    throw new NoSuchElementException();
                }
                cur = cur.next;
                return cur.key;
            }
        };
    }

    /**
     * Gets the size of the linked list.
     * 
     * @return int the size of the list
     */
    public int size() {

        return length;
    }

    /**
     * Gets the first node in the linked list.
     * 
     * @return Node<E> the head of the list
     */
    public E getFirst() {
        if (length == 0) {
            return null;
        } else {
            return head.key;
        }
    }

    /**
     * Gets the last node in the linked list.
     * 
     * @return Node<E> the tail of the list
     */
    public E getLast() {
        if (length == 0) {
            return null;
        } else {
            return tail.key;
        }
    }
}
