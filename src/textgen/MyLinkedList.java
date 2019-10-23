package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list

 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		
		this.head = new LLNode<E>();
		this.tail = new LLNode<E>();
		this.size = 0;
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		
		if(element==null){
			throw new NullPointerException();
		}
		else{
			LLNode<E> myNode = new LLNode(element);
			myNode.next = tail;
			myNode.prev = tail.prev;
			tail.prev.next = myNode;
			tail.prev = myNode;
			size++;
			return true;
		}
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		LLNode<E> temp = new LLNode<E>();
		// TODO: Implement this method.
		if(size!=0&&index>=0&&index<size){
			temp = head.next;
			for(int i=0;i<index;i++){
				temp = temp.next;
			}
			return temp.data;
		}
		
		else
		 throw new IndexOutOfBoundsException();
		
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		LLNode<E> temp = new LLNode<E>();
		LLNode<E> temp1 = new LLNode<E>(element);
		if(index == 0&&element!=null){
			head.next = temp1;
			temp1.next = tail;
			temp1.prev = head;
			tail.prev = temp1;
			size++;
		}
		else if(index>=0&&index<=size&&element!=null){
			temp = head.next;
			for(int i = 0;i<index-1;i++)
				temp = temp.next;
			temp1.next = temp.next;
			temp1.prev = temp;
			temp.next.prev = temp1;
			temp.next = temp1;
			size++;
		}
		else if(element == null)
			throw new NullPointerException();
		else
			throw new IndexOutOfBoundsException();
		
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
		// TODO: Implement this method
		LLNode<E> temp = new LLNode<E>();
		temp = head.next;
		if(index>=0 && index<=size){
			for(int i=0; i<index;i++){
				temp=temp.next;
			}
		temp.prev.next = temp.next;
		temp.next.prev = temp.prev;
		size--;
		return temp.data;
		}
		else
			throw new IndexOutOfBoundsException();
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
		// TODO: Implement this method
		E cur;
		LLNode<E> temp = new LLNode<E>();
		temp = head.next;
		if(index>=0 && index<=size&&element!=null){
			for(int i=0; i<index;i++){
				temp=temp.next;
			}
			cur = temp.data;
			temp.data = element;
		
		return cur;
		}
		else if(element == null)
			throw new NullPointerException();
		else
			throw new IndexOutOfBoundsException();
	}   
	public void print(){
		LLNode<E> temp = new LLNode<E>();
		for(temp = head.next;temp!=tail;temp=temp.next)
			System.out.print(temp.data+"\t");
		System.out.println("Size = "+size);
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(){
		this.data = null;
		this.prev = null;
		this.next = null;
	}
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
