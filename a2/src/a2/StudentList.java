package a2;
/**
 * Please provide the following information
 *  Name:Abdulgani Muhammedsani
 *  NetID:amm546
 *  Testing Partner Name:Partner Dropped Course
 *  Testing Partner NetID:Partner Dropped the course
 *  Tell us what you thought about the assignment:
 *  I thought the assignment was interesting and i think I am becoming more familiar with java and the IDE.
 */

/** A mutable list of {@code Student} objects.
 */
public class StudentList {
    // Implementation: the StudentList is implemented
    // as a resizable array data structure. It should contain
    // an array that has a large enough capacity to hold all the elements,
    // plus possibly extra elements. It should be able to hold
    // a large number of students but not use up a large amount
    // of memory if it holds a small number of students.


    // TODO 10: Add instance variables for the StudentList and write a class invariant

    /**Backing array that stores students that should hold the students enrolled.
     * Must Be >= the size of the studentlist(Different than the capacity)**/
    private Student[] Backing_array;

    /**Student to be added to the list of students **/
    private Student student;

    /** index of student inside the list. 0=< i <= size of StudentList  **/
    private int index;

    // You may not use any classes from the java.util package.

    /** How long you spent on this assignment */
    public static double timeSpent = 8;


    /**Class Invariant: Returns boolean and checks that invariants are maintained throughout the program**/

    boolean classInv(){
        return index >= 0 && index <= size() && size() <= Backing_array.length;

    }

    /** Constructor: A new empty {@code StudentList}. */

    public StudentList() {
        // TODO 11
        Backing_array = new Student[5];
        student = null;
        index = 0;
        // The capacity of the StudentList is not the same as the size.
        // The capacity is the length of the backing array.
        // We suggest starting with a capacity of 5.
        // If the backing array runs out of space, double the size of the backing array
        // and copy all elements to the new backing array
    }

    /** Returns the number of elements in this StudentList. */
    public int size() {
        // TODO 12
        //Using a counter that runs through the backing array, determines the amount of elements that contain value
        //that are not null and returns that value.
        int count = 0;
        for(int i = 0; i < Backing_array.length;i++)
        {
            if(Backing_array[i] != null)
            {
                count++;
            }
        }

        // Note: Do not confuse size and capacity.
        return count;
    }

    /** Returns the element in the list at position i. Positions start from 0.
     * Requires: 0 <= i < size of StudentList
     */
    public Student get(int i) {
        // TODO 13
        //the index that is being returned needs to be first taken into index
        //and class invariant is checked. Index <= size() & if that is true. It will return the element
        index = i;
        assert classInv();
        return Backing_array[index];

    }

    /** Effect: Add Student s to the end of the list. */
    public void append(Student s) {
        // TODO 14
        student = s;
        // because size changes after every element is added, i can use the value at size to find the next index
        //to add the student. This class should have assert at the start and the end because it at affects backing array
        // so we need to ensure that values follow the class invariant. Backing_array.length >= size
        assert classInv();
        if(Backing_array.length > size())
        {
            Backing_array[size()] = s;
        }
        else
        {
            //if the backing array is full-backing array is equal in size to the size method value-, I update it to be an array 2 times the size and transfer the student
            //elements over.
           Student[] Backing_array1 = new Student[2*size()];
           for(int i = 0; i < size();i++)
           {
               Backing_array1[i] = Backing_array[i];
           }
           Backing_array = Backing_array1;
            Backing_array[size()] = s;
        }
        assert classInv();



        // Make sure that BEFORE adding a Student to the array, you
        // ensure that the capacity of the array is enough to add a
        // Student to it.
        // Note: Make sure you are keeping the class invariant for ALL classes true.

    }

    /** Returns: whether this list contains Student s. True/False*/
    public boolean contains(Student s) {
        // TODO 15
        //loop through the backing array, and if student s is found in the array, returns true, else returns false.
        student = s;
        for(int i = 0; i < Backing_array.length; i++)
        {
            if (Backing_array[i] == s)
            {
                return true;
            }
        }
        return false;
    }

    /** Effect: If Student s is in this StudentList, remove Student s from the array and return true.
     * Otherwise, return false. Elements other than s remain in the same relative order.
     */
    public boolean remove(Student s) {
        // TODO 16
        //I look through the backing array, after calling the contains method which confirms that student
        //s is in the studentlist. To remove the student i create a new element and add all elements besides the
        //student s. Class invariant is checked multiple times because this method decreases backing arrays size.
        //therefore we need to check and ensure that the size <= backingarray.length (that size of studentlist <= capacity)

        student = s;
        assert classInv();

        if(contains(s))
        {
            for(int i = 0; i < Backing_array.length; i++)
            {
                if (Backing_array[i].equals(s))
                {
                    //Creating new Array that is one less than the array
                    // to account for the size change once the student is removed
                    Student[] Backing_array2 = new Student[Backing_array.length-1];

                    for(int j = 0; j < Backing_array.length-1;j++)
                    {
                        if(j < i)
                        {
                            Backing_array2[j] = Backing_array[j];
                        }
                        if(j > i)
                        {
                            Backing_array2[j-1] = Backing_array[j];
                        }
                    }
                    Backing_array = Backing_array2;
                    index_adjust();
                    assert classInv();
                    return true;
                }



            }
        }
        // Note: Make sure you are keeping the class invariant for ALL classes true.
        assert classInv();
        return false;
    }
    /** Returns Backing Array Length. Used for testing.
     */
    public int backingArrayLength()
    {
        //A method that I can call outside this class to get the backing array length useful for testing
        return Backing_array.length;
    }
    /** Effect: Adjusts index value if it surpasses size of studentlist
     */
    private void index_adjust()
    {
        //only context the index might be greater is when the remove makes the array shrink by one index
        //running remove. This method adjusts it before the end of the remove method.
        if(index > size())
        {
            index = index - 1;
        }
    }
    // TODO 17 you may want to write some private helper methods

    /** The String representation of this StudentList */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(get(i));
        }
        sb.append("]");
        return sb.toString();
    }
}
