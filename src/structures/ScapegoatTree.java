package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;

	
	@Override
	public void add(T t) {
		upperBound ++;
//		System.out.println("This is my upperBound " + upperBound);
		BSTNode<T> tempNode = new BSTNode<T>(t,null,null);
		root = super.addToSubtree(root, tempNode);
		double limit = Math.log(upperBound)/Math.log(1.5);
//		System.out.println("This is my limit "+limit);
		if(super.height()>limit){
//			System.out.println("Out of limit");
			BinarySearchTree<T> tempTree = new BinarySearchTree<T>();
			BSTNode<T> scapegoatNode = scapegoat(tempNode);
//			System.out.println("This scapegoat node is " + scapegoatNode.getData());
			tempTree.root = scapegoatNode;
//			System.out.println("The temp tree height before balance is " + tempTree.height());
			tempTree.balance();
//			System.out.println("The temp tree height after balance is " + tempTree.height());
			if(scapegoatNode.getData().compareTo(scapegoatNode.getParent().getData())<0)
				scapegoatNode.getParent().setLeft(tempTree.root);
			else{
				scapegoatNode.getParent().setRight(tempTree.root);
			}
		}
		}
	
	private BSTNode<T> scapegoat(BSTNode<T> node){
		BSTNode<T> w = node.getParent();
			while(3*super.subtreeSize(w) <= 2*super.subtreeSize(w.getParent())){
				w = w.getParent();			
				}
		return w;
	}
	
	@Override
	public boolean remove(T element) {
		super.remove(element);
		if(upperBound> 2*size()){
			balance();
			upperBound = size();
		}
		return true;
	}
}
