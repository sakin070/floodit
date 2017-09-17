// Author 1: Saheed Akinbile Abimbola
// Student number: 8872464
// Author 2: Wilfried Tanezick Dondji Fogang
// Student number: 7968381
// Course: ITI 1121-B
// Assignment 3
// Question 1

public class GenericStack<E> implements Stack<E> {

	private E[] elems;
	private int count;

	@SuppressWarnings("unchecked")
	public GenericStack(int n) {

		this.elems = (E[]) new Object[n*n];
		this.count = 0;
	}

	public boolean isEmpty() {

		return (count==0);
	}

	public E peek() {

		return elems[count-1];
	}

	public E pop() {

		E temp  = elems[count-1];
		elems[count-1] = null;
		this.count--;
		return temp;
	}

	public void push(E element) {

		elems[count] = element;
		count++;
	}	
}