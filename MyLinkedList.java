/** 
 * This file contains three classes two of which
 *are (Node and MyListIterator) are embedded within
 * the MyLinkedList class. The purpose of this file is 
 * imitate the built in LinkedList in java and to contain
 * a working iterator to traverse it and a working node 
 * to create the links.
 * Author:Nathalie Franklin
 * Email: nfrankli@ucsd.edu
 * References: No refrences were used everything was
 * done and conceptulized by me.
 */

import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/** 
 * MyLinkedList is a class that implements select methods
 * for LinkedList, which enforced by it's extenstion of the
 * AbstractList. Just like a linked list it will store, add,
 * remove and traverse and keep track of the linked Nodes with 
 * aid of it's emebeded Node and MyListItertorClass
 */
public class MyLinkedList<E> extends AbstractList<E> {

    int size;
    Node head;
    Node tail;

    /**
     * A Node class that holds data and references to previous and next Nodes
     * This class is used for both MyLinkedList and MyListIterator.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /** 
        * Constructor to create singleton Node 
        * @param element Element to add, can be null	
        */
        public Node(E element) {
            //Initialise the elements
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /** 
        * Set the previous node in the list
        * @param p new previous node
        */
        public void setPrev(Node p) {
            //Set the node p on the previous position
            prev = p;
        }

        /** 
        * Set the next node in the list
        * @param n new next node
        */
        public void setNext(Node n) {
            //Set the node n on the next position
            next = n;
        }

        /** 
        * Set the element 
        * @param e new element 
        */
        public void setElement(E e) {
            this.data = e;
        }

        /** 
        * Accessor to get the next Node in the list 
        * @return the next node
        */
        public Node getNext() {
            return this.next;
        }
        /** 
        * Accessor to get the prev Node in the list
        * @return the previous node  
        */
        public Node getPrev() {
            return this.prev;
        } 
        /** 
        * Accessor to get the Nodes Element 
        * @return this node's data
        */
        public E getElement() {
            return this.data;
        } 
    }

    /** ListIterator implementation  which traverse the linked
    * list one Node per method call.
    */ 
    protected class MyListIterator implements ListIterator<E> {

        boolean forward;
        boolean canRemoveOrSet;
        Node left,right; // Cursor sits between these two nodes
        int idx; // Tracks current position. what next() would return

        /** The constructr to MyListIterator which intializes
        * the feild variables forward, left, right,idx and 
        * can remove to their default values.
        * @params (None)
        */
        public MyListIterator() {
            this.forward = true;
            this.left=head;
            this.right = head.next;
            this.idx =0;
            this.canRemoveOrSet = false;
            
        }
        /** 
        *The add method overrides the inherited
        *add method. Makes sure data not null
        *creates a new node to store 
        *the passes in data and
        *adds it to the left of the iterator 
        *increasing the size by one
        *@params(data to be stored)
        *@returns void
        */
        @Override
        public void add(E e) {
            //null leads to an exception
            if(e==null){
                throw new NullPointerException();
            }
            canRemoveOrSet = false;
            Node adds = new Node(e);
            //inserts Node into the left feild
            adds.prev = left;
            adds.next =right;
            left.next = adds;
            right.prev=adds;
            left = adds;
            size +=1;
            idx +=1;
            

        }
        /*The method overrides the inherited
         hasNext method.This method makes sure that the
        *iteratorcan move to the right
        *@params(none)
        *@return(none)
        */
        @Override
        public boolean hasNext() {
            return (right.data != null);
        }

        /*The method overrides the inherited
         hasPrevious method.This method makes sure that the
        *iteratorcan move to the right
        *@params(none)
        *@return(none)
        */
        @Override
        public boolean hasPrevious() {
            return (left.data!=null);
        }

        /*This method overides the inherited
        next method. It first checks if you can 
        *go left then moves left and increments
        *the index by 1 updating the right left
        *feild variables to new nodes and forward
        *to true and canRemoveOrSet to true
        *@params(none)
        *@returns the element at the right field variable
        */
        @Override
        public E next()	{
            //invalid next leads to an exception
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            E return_val = right.data;
            left= right;
            right = right.next;
            idx+=1;
            canRemoveOrSet = true;
            forward = true;
            return left.data; 
             
        }
        /*Overides the inharited nextidx
        *returns the index of the right
        *field which is stored in idx
        *@params(none)
        *@returns the right feild index
        */
        @Override
        public int nextIndex() {
            return idx;		
        }

        /* Override the inherited previous 
        *method. Checks if the iterator can go
        *left if so it moves left and right field
        *left and udates forward false and canRemoveOrSet to
        *true 
        *@params (none)
        *@returns the data in the node to the right
        */
        @Override
        public E previous() {
            //invalid previous leads to exception
            if(!hasPrevious()){
                throw new NoSuchElementException();
            }
            E return_val = left.data;
            right = left;
            left = left.prev; 
            canRemoveOrSet = true;
            idx = idx-1;
            forward = false;
            return return_val; 
        }
        /*Overides the inherited previousIndex/
        *Get the the index at which the left 
        *field is stored by subtracting one from
        *idx
        *@params(none)
        *@returns the left fields index
        */
        @Override
        public int previousIndex() {
            return idx-1;  
        }
        /*checks if it can remove. Removes the last node
        *whose value had been returned which it
        *knows from the forward field and 
        *updates either right or left feild to
        *a new node and
        *canRemoveOrSet to false
        *@params (none)
        *@returns void
        */
        @Override
        public void remove() {
            if(!canRemoveOrSet){
                throw new IllegalStateException();
            }
            //checks if forward is true to confirm 
            //next as the last action and previous exists
            if(forward && hasPrevious()){
                
                left.prev.next = left.next;
                left.next.prev = left.prev; 
                left =left.prev;
            }
            //if next makes sure next exists 
            else if (!forward && hasNext()){

                right.prev.next = right.next;
                right.next.prev = right.prev; 
                right =right.next;	
            }
            canRemoveOrSet = false;
            
            
        }
        /* Overides the inherited set method checks if it
        * can set the that the data is not null then it 
        sets the data in the Node whose data was returned
        last which it knows the direction by the forward feild
        *@params the replacemnet data 
        *@returns the replaced data
        */
        @Override
        public void set(E e) {
            if(!canRemoveOrSet){
                throw new IllegalStateException();
            }
            else if( e == null){
                throw new NullPointerException();
            }
            if (forward){
                left.data = e;

            }
            else{
                right.data = e;
            }
                
        }
    }

    //  Implementation of the MyLinkedList Class
    /** Only 0-argument constructor is defined intializes
    *all the the field variables to default only creating a
    *double linked list with a dummy head and tail node 
    *linked to each other.
    *@params(none)
    */
    public MyLinkedList() {
        this.size = 0;
        this.head = new Node(null);
        this.tail = new Node(null);
        head.next = tail;
        head.prev = tail;
        tail.prev = head;
        tail.next = head;
       
    }
    /*This method overides the inherited
    *size method and returns the current
    *size of the list.
    *@params(none)
    *@returns the size of the list
    */
    @Override
    public int size() {
        return size;
    }
    /*
    *This method overides the inherited get
    *method. This method traverses through
    * the getNth method it find the node
    * with the data corelated to the
    *index and returns the data
    *@params where the desired data is
    *@returns the desired data
    */
    @Override
    public E get(int index) {

        Node temp = getNth(index);
     
        return temp.data;  
    }
    /*
    *This method overrides the inherited add method
    *it traverses the list untill it gets to the
    *desired index(which it first checks is valid) 
    *and then stores a node containing the data 
    *which it checks isn't Null.Increases size by one
    *@params the desired location of storage and the
    *data to be stored.
    *@return void 
    */
    @Override
    public void add(int index, E data) {
        //index out of range leads to exception
        if(index< 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        //null data leads to execption
        else if(data == null){
            throw new NullPointerException();
        }
        Node temp = head;
        for(int i =0; i<=index; i++){
            temp = temp.next;	
        }
        //inserts node
        Node to_add = new Node(data);
        to_add.prev = temp.prev; 
        to_add.next = temp;
        temp.prev.next = to_add;
        temp.prev = to_add;
        size +=1;
    }
    /*This method overloads add for when the
    *the indexs is not specified and called
    *to be stored in the last index.
    *The data is checked to make sure it's not
    *null and increments size by 1
    *@params data to be stored
    *@returns true if the data was stored
    */
    public boolean add(E data) {
        //null data leads to exception
        if(data == null){
            throw new NullPointerException();
        }
        add(size, data);
        System.out.println(size);
        return true; 
    }

    /*
    *This method repaces the data in the
    *desired index with ones that is past in
    *but first makes sure it's not null and
    *in valid index when calling getNth
    *@params index that the data should be replaced
    *@returns the remplaced data
    */
    public E set(int index, E data) {
        //null data leads to exception
        if(data == null){
            throw new NullPointerException();
        }
        //replaces data
        Node temp= getNth(index);
        E return_data = temp.data; 
        temp.data = data;
        return return_data;
    }

    /*This method removes element at the 
    *desired index, decrements size by one
    *and then returns the
    *data in the element. The travering is
    *done through the call to getNth
    *@params the desired indes to be removed
    *@return the data from the removed Node
    */
    public E remove(int index) {
    
        Node temp = getNth(index);
        
        
        E return_data = temp.data;
        temp.next.prev = temp.prev;
        temp.prev.next = temp.next;
        size-=1;
        return return_data; 
    }

    /*Clears the linked list
    *of all it's values by detaching
    *all its elements.
    *@params(none)
    *@return(none)
    */
    public void clear() {
        head.next = tail;
        tail.prev = head;
        size =0 ; 
    }

    /*Checks if the LinkedList has any
    *elements based on size
    *@params(none)
    *@return whether empty is true
    */
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }
        return false;
          
    }

    /*Check if the index passed in valid if so
    *traverse the MyLinkedList from the head to
    *the desired index
    *@params desired values index
    *@returns the desires values Node
    */
    protected Node getNth(int index) {
        //index out of range leads to execption
        if( index >= size || index< 0){
            throw new IndexOutOfBoundsException();
        }
        Node temp = head; 
        for (int i = 0; i<=index; i++){
            temp = temp.next;
        }
        return temp;
    }
    /*This method creates an instance of MyListIterator
    *stores it in a parent type variable and then returns
    *it.
    *@params(none)
    *@returns and instance of MyListIterator
    */
    public Iterator<E> iterator() {
        Iterator<E> test = new MyListIterator();
        return test; 
    }
    
    /*This method creates an instance of MyListIterator
    * then returns it.
    *@params(none)
    *@returns and instance of MyListIterator
    */
    public ListIterator<E> listIterator() {
        ListIterator<E> test = new MyListIterator();
        return test; 
    }

}
