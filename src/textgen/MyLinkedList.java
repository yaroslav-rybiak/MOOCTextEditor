package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /**
     * Create a new empty LinkedList
     */
    MyLinkedList() {
        size = 0;
        head = new LLNode<>(null);
        tail = new LLNode<>(null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        if (element == null) throw new NullPointerException();
        LLNode newNode = new LLNode<>(element);
        newNode.prev = tail.prev;
        tail.prev = newNode;
        newNode.prev.next = newNode;
        newNode.next = tail;
        size++;
        return true;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        LLNode node = head;
        int i = 0;
        while (i <= index) {
            node = node.next;
            i++;
        }
        return (E) node.data;
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index   where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {
        if (element == null) throw new NullPointerException();
        if (index > size || index < 0) throw new IndexOutOfBoundsException();
        int i = 0;
        LLNode node = head;
        LLNode newNode = new LLNode<>(element);
        while (i <= index) {
            node = node.next;
            i++;
        }
        newNode.prev = node.prev;
        newNode.next = node;
        node.prev.next = newNode;
        node.prev = newNode;

        size++;
    }


    /**
     * Return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();
        int i = 0;
        LLNode node = head;
        while (i <= index) {
            node = node.next;
            i++;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return (E) node.data;
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        if (element == null) throw new NullPointerException();
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();
        int i = 0;
        LLNode node = head;
        LLNode newNode = new LLNode<>(element);
        while (i <= index) {
            node = node.next;
            i++;
        }
        node.prev.next = newNode;
        node.next.prev = newNode;
        newNode.next = node.next;
        newNode.prev = node.prev;
        node = null;
        return (E) newNode.data;
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    // TODO: Add any other methods you think are useful here
    // E.g. you might want to add another constructor

    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

}
