package a3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of {@code LList<T>} as a singly linked list.
 * Name Abdulgani Muhammedsani
 * Net id amm546
 * Date: 09/28/22
 * I thought the assignment was very interesting and helped me
 * understand how to use generics, read files, and how to implement linked lists.
 */
public class SLinkedList<T> implements LList<T> {

    /**
     * Number of values in the linked list.
     */
    private int size;
    /**
     * First and last nodes of the linked list (null if size is 0)
     */
    private Node<T> head, tail;

    /**
     * Creates: an empty linked list.
     */
    public SLinkedList() {
        size = 0;
        head = tail = null;
    }

    //Class invariants If size is zero, head and tail are null
    //Otherwise assert that head and tail are not equal to null.
    //Using this allows us to check the invariant before and after
    // methods that need to insert before, contains, and prepend
    //that should be checking that the list is not empty.
    boolean classInv() {
        assert size >= 0;
        if (size == 0) {
            return (head == null && tail == null);
        }
        assert head != null;
        assert tail != null;

        // TODO
        return true;
    }

    public int size() {
        return size;
    }

    public T head() {
        return head.data();
    }

    public T tail() {
        return tail.data();
    }

    public void prepend(T v) {
        assert classInv();
        Node<T> n = new Node<>(v, head);
        head = n;
        if (tail == null) tail = head;
        size++;
        assert classInv();
    }


    /**
     * Return a representation of this list: its values, with "[" at the beginning, "]" at the
     * end, and adjacent ones separated by ", " . Takes time proportional to the length of this
     * list. E.g. for the list containing 4 7 8 in that order, the result it "[4, 7, 8]".
     * E.g. for the list containing two empty strings, the result is "[, ]"
     */
    @Override
    public String toString() {
        // Do not modify the following 2 lines or the return statement
        assert classInv();
        StringBuilder res = new StringBuilder("[");
        Node<T> container = head;
        // TODO 1
        int i = 0;
        //Checking if head is null and if it is not, we append the head elements data to our string.
        //If i does not equal 1, one element has been added, and comma can now be placed.
        while(head != null)
        {
            if(i == 0)
            {
                res.append(head());
            }
            else
            {
                res.append("," + head());
            }
                head = head.next();
            //traversing the list with the head node.
            i++;
        }
        head = container;//resetting head node at the end to the initial position.
        classInv();
        return res + "]";
    }
    /**
     * Effect: Add v to the end of this list. This operation takes constant time. E.g. if the list
     * is [8, 7, 4], append(2) changes this list to [8, 7, 4, 2].
     */
    public void append(T v) {
        // TODO 2
        //
        Node <T> added = new Node<>(v,null);     // Created a new node to hold the new value
       if(head == null && tail == null)
       {
            head = added;                       //Checking if the initial values are null
            tail = added;                       //sets both to the value
       }
       else
       {
           tail.setNext(added);                 //if they are not null, set the next pointer
           tail = tail.next();                  //to the added element and set tail value to be
       }                                        //the recently added element.
       size++;
       classInv();
    }
    /**
     * Effect: Insert value x into the list before the first occurrence of y.
     * Requires: y must be in the list.
     * Example: If the list is [3, 8, 2], then insertBefore(1, 8) results in the
     * the list changing to [3, 1, 8, 2].
     */
    public void insertBefore(T x, T y) {
        // TODO 3
        //Checking the class invariant being that the head and tails are
        //not null. Initialize counter and node and check if the head data is equal
        // to the value, if it is,it sets new node data to the insert value,
        //points to the previous head, and sets new node to head. This logic
        //is implemented when the the value is found when traversing the
        //linked list. If the node with insert location found at the next
        //index, set the current node while traversing to point to new node, and new
        //node points to the insert before node. ENSURE to reset head to beginning of list
        //so the class invariant will always be met.
        classInv();
        Node <T> added = new Node<>(x,null);
        Node <T> container = head;
        int counter = 0;
        while(counter < size())
        {
            if (head.data().equals(y))
            {
                added.setNext(head);
                head = added;
                break;
            }
            else if (head.next().data().equals(y)) {
                added.setNext(head.next());
                head.setNext(added);
                head = container;
                break;
            }
            head = head.next();
            counter++;
        }


        size++;
        classInv();
        // since there is a precondition that y is in the list, we don't have to handle the case of the empty list
    }
    /**
     * Returns: the element at position k
     * Requires: {@code 0 <= k < size()}
     * <p>
     * Elements are indexed starting from position 0, so the
     * first element is returned if k=0.
     */
    public T get(int k) {
        // TODO 4
        //We check if the head is null, but class inv
        //also ensures that, Then we iterate through the linked
        //list and check to see if the k value is equivalent to
        //the counter, if it is, then we return the data at that
        //location in the linked list. ENSURE to reset head to
        //beginning of list.
        classInv();
        int counter = 0;
        Node <T> container = head;
        if(head == null) {
            return null;
        }
        if(head != null)
        {
            for (int i = 0; i < size(); i++)
            {
                if (i == k)
                {
                    T v = head.data();
                    head = container;
                    return v;
                }
                head = head.next();
            }
        }
        head = container;
        return null;
    }

    /**
     * Returns: true if element is in the linked list, false if not.
     * Requires: {@code head & tail != null}
     */
    public boolean contains(T value) {
        // TODO 5
        //Contains also ensures that values
        // are present via the classinv. We check if
        //heads data is equivalent and return if true.
        //otherwise, we traverse the array using a while
        //loop, if the data equals the searched value, then we return true.
        //otherwise, it returns false. ENSURE to reset head to the initial value
        //to abide by class invariant.
        classInv();
        Node <T> container = head;
        if(head.data().equals(value))
        {
            return true;
        }
        while(head != null)
        {
            if(head.data().equals(value))
            {
                head = container;
                return true;
            }
            head = head.next();
        }
        head = container;
        classInv();
        return false;
    }

    /**
     * Returns: true if element was removed, false if not.
     * Requires: {@code head & tail != null}
     * Effect: Removes x from the list. This operation takes linear time. E.g if the list is
     * [7 ,5, 8], remove(5) changes the list to [7 , 8]
     */
    public boolean remove(T x) {
        // TODO 6
        //Checks class invariant that nodes arent null because
        //necessitates actual values. Checking if they are not null
        //and that the data is at first node. return list missing initial
        //element, else, it traverses the linked list with a while loop.
        //and is the heads next data is equal to what needs to be removed
        // adjust pointers of current head to skip over head.next
        // When done, ENSURE head reset to the beginning of the list.
        classInv();
        Node <T> container = head;
        if(head != null && head.data() == x)
        {
            head = head.next();
            return true;
        }
        while(head != null)
        {
                if (head.next() != null && head.next().data().equals(x)) {
                    head.setNext(head.next().next());
                    head = container;
                    return true;
                }
                head = head.next();

        }
        head = container;
        size--;
        classInv();
        return false;
    }

    /**
     * Iterator support. This method makes it possible to write
     * a for-loop over a list, e.g.:
     * <pre>
     * {@code LList<String> lst = ... ;}
     * {@code for (String s : lst) {}
     *   ... use s here ...
     * }
     * }
     */
    @Override
    public Iterator<T> iterator() {
        //Iterator method allows us to take advantage of a enhanced
        //for loop.
        assert classInv();
        return new Iterator<T>() {
            private Node<T> current = head;

            public T next() throws NoSuchElementException {
                if (current != null) {
                    T result = current.data();
                    current = current.next();
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }

            public boolean hasNext() {
                return (current != null);
            }
        };
    }
}
