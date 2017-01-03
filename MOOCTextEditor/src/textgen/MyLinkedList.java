package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>();
		tail = new LLNode<E>();
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{  
	    if (element == null) throw new NullPointerException();
	    LLNode<E> node = new LLNode<>(element);
	    LLNode<E> prevNode = tail.prev;
	    prevNode.next = node;
	    node.prev = prevNode;
	    node.next = tail;
	    tail.prev = node;
	    size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if (index < 0 | index >= size) throw new IndexOutOfBoundsException();
		LLNode<E> node = head;
		for (int i = 0; i <= index; i++) 
		    node = node.next;
		return node.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if (element == null) throw new NullPointerException();
		if (index < 0 | index > size) throw new IndexOutOfBoundsException();
        LLNode<E> node = head;
        for (int i = 0; i <= index; i++) 
            node = node.next;
        LLNode<E> currentNode = new LLNode<>(element);
        LLNode<E> prevNode = node.prev;
        currentNode.next = node;
        node.prev = currentNode;
        prevNode.next = currentNode;
        currentNode.prev = prevNode;
        size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
        if (index < 0 | index >= size) throw new IndexOutOfBoundsException();
        LLNode<E> node = head;
        for (int i = 0; i <= index; i++) 
            node = node.next;
        LLNode<E> prevNode = node.prev;
        prevNode.next = node.next;
        node.next.prev = prevNode;
        size--;
		return node.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
        if (element == null) throw new NullPointerException();
        if (index < 0 | index >= size) throw new IndexOutOfBoundsException();
        LLNode<E> node = head;
        for (int i = 0; i <= index; i++) 
            node = node.next;
		node.data = element;
        return node.data;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
   public LLNode() 
    {
        this.prev = null;
        this.next = null;
    }


}
