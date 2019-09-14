
public class LinkedStack<E> implements Stack<E>{
	
	private static class Node<E>{
		public E val;
		public Node<E> next;
		public Node<E> prev;
		
		public Node(E val,Node<E> next,Node<E> prev){
			this.val=val;
			this.next=next;
			this.prev=prev;
		}
	}
	
	private Node<E> head;
	private int size=0;
	
	public LinkedStack() {
		head=new Node<E>(null,null,null);
		head.next=head;
		head.prev=head;
		
	}

	@Override
	public E pop() {
		if (!isEmpty()) {
			E result=head.next.val;
			head.next=head.next.next;
			head.next.prev=head;
			size--;
			return result;
		}
		return null;
	}

	@Override
	public void push(E elem) {
		
		if(elem==null) {
			throw new NullPointerException("the parameter is empty");
		}
		
		Node<E> node=new Node<E>(elem,head.next,head);
		head.next=node;
		node.next.prev=node;
		size++;
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size==0;
	}
	
}
