//I affirm that I have adhered to the honor code on this assignment

import java.util.NoSuchElementException;




public class Queue<AnyType> implements QueueADT<AnyType>{

    class Node<T>{
	    
	    public T element;
	    public Node<T> next = null;
	    
	    public Node(T item, Node<T> nextNode) {
		element = item;
		next = nextNode;
	    }
	    
	    public Node(T item) {
		element = item;
		next = null;
	    }
	    
    }
	    
    private Node end = new Node(null);
    private Node start = new Node(null, end);
    
    private int size = 0;
    
    @Override
    //add items to the tail
    public void enqueue(AnyType item) {
	Node node = new Node(item, end);
	Node cursor = start;
	if(size == 0) {
	    start.next = node;
	}
	while(cursor.next.next != end) {
	    cursor = cursor.next;
	}
	cursor.next.next = node;
	node.next = end;
	
	size++;
    }

    @Override
    //take item after the head
    public AnyType dequeue() throws NoSuchElementException {
	// TODO Auto-generated method stub
	Object temp = start.next.element;
	start.next = start.next.next;
	
	size--;
	
	return (AnyType) temp;
    }

    @Override
    public AnyType front() throws NoSuchElementException {
	// TODO Auto-generated method stub
	return (AnyType) start.next.element;
    }

    @Override
    public int size() {
	// TODO Auto-generated method stub
	return size;
    }

    @Override
    public boolean isEmpty() {
	if(start.next == end && size == 0) {
	    return true;
	}else {
	    return false;
	}
    }

    @Override
    public void clear() {
	start.next = end;
	size = 0;
    }

}
