// Author 1: Saheed Akinbile Abimbola
// Student number: 8872464
// Author 2: Wilfried Tanezick Dondji Fogang
// Student number: 7968381
// Course: ITI 1121-B
// Assignment 4
// Question 1

import java.io.*;
public class GenericLinkedStack<E> implements Stack<E>,Cloneable,Serializable{

    private static class Elem<E> implements Cloneable,Serializable{


		private E value;
		private Elem<E> next;

		public Elem(E value, Elem<E> next) {
			this.value = value;
			this.next = next;
		}
	}

	private Elem<E> top;

	/**
     * Tests if this Stack is empty.
     *
     * @return true if this Stack is empty; and false otherwise.
     */
    public boolean isEmpty() {
    	return top == null;
    }

    /**
     * Returns a reference to the top element; does not change
     * the state of this Stack.
     *
     * @return The top element of this stack without removing it.
     */

    public E peek() {
    	if (isEmpty()) {
    		throw new EmptyStackException();
    	} else {
    		return top.value;
    	}
    }

    /**
     * Removes and returns the element at the top of this stack.
     *
     * @return The top element of this stack.
     */

    public E pop() {
    	if (isEmpty()) {
    		throw new EmptyStackException();
    	} else {
    		E toReturn = top.value;
    		if (top.next == null) {
    			top = null;
    			return toReturn;
    		} else {
    			top = top.next;
    			return toReturn;
    		}
    	}
    }

    /**
     * Puts an element onto the top of this stack.
     *
     * @param element the element be put onto the top of this stack.
     */
    public void push( E element ) {
    	if (element == null) {
    		throw new NullPointerException("Can't push a null elemnet");
    	} else {
    		top = new Elem<E>(element, top);
    	}
    }

    public GenericLinkedStack<E> clone() throws CloneNotSupportedException{
        GenericLinkedStack<E> first = new GenericLinkedStack<E>();
        GenericLinkedStack<E> second = new GenericLinkedStack<E>();
        while(!this.isEmpty()){
            first.push(this.pop());
        }

        while(! first.isEmpty()) {
            E temp = first.pop();
            second.push(temp);
            this.push(temp);
        }
        return second;
    }

    public String toString() {
        Elem<E> cusor = top;

        while( cusor.next != null){
            cusor = cusor.next;
        }
        return cusor.value.toString();

    }
}