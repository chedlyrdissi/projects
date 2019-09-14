
public interface Stack<E> {
	/*
	 * @return the topmost element 
	 */
	public abstract E pop();
	/*
	 * @param an instance of the class E
	 * adds the element on top
	 */
	public abstract void push(E elem);
	/*
	 * @return true if and only if the stack is empty
	 */
	public abstract boolean isEmpty();
}
