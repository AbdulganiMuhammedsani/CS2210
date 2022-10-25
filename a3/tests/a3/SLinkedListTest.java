package a3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SLinkedListTest {
    //Tests to see if list is empty when initialized and returns proper values.
    //Presents to string as well to ensure the output still contains brackets.
    @Test
    void emptyList() {
        LList<Integer> lst0 = new SLinkedList<>();
        assertEquals(lst0.size(), 0);
        assertEquals(lst0.toString(), "[]");
    }
    //testing all types of string outputs from linked lists containing
    //integers as well as strings.
    @Test
    void string12() {
        LList<Integer> lst0 = new SLinkedList<>();
        assertEquals(lst0.size(), 0);
        assertEquals(lst0.toString(), "[]");
        lst0.append(100);
        lst0.append(-32);
        lst0.append(Integer.MAX_VALUE);
        assertEquals("[100,-32,2147483647]",lst0.toString());
        LList<String> lst1 = new SLinkedList<String>();
        lst1.append("abdcedfghijklmnop");
        lst1.append("...");
        lst1.append("?");
        assertEquals("[abdcedfghijklmnop,...,?]",lst1.toString());
    }
    //Append testing with all types of values. Testing Integer max/ min
    //Testing head and tail values while also appending throughout.
    @Test
    void append12() {
        LList<Integer> l1 = new SLinkedList<>();
        l1.append(Integer.MAX_VALUE);
        assertEquals(1, l1.size());
        assertEquals(Integer.MAX_VALUE, l1.head());
        assertEquals(Integer.MAX_VALUE, l1.tail());
        assertEquals("[2147483647]",l1.toString());
        l1.append(21);
        assertEquals(2, l1.size());
        assertEquals(Integer.MAX_VALUE, l1.head());
        assertEquals(21, l1.tail());
        l1.append(Integer.MIN_VALUE);
        assertEquals(3, l1.size());
        assertEquals(Integer.MAX_VALUE, l1.head());
        assertEquals(Integer.MIN_VALUE, l1.tail());
        l1.append(56);
        assertEquals(4, l1.size());
        assertEquals(Integer.MAX_VALUE, l1.head());
        assertEquals(56, l1.tail());
        assertEquals(Integer.MAX_VALUE,l1.get(0));
        assertEquals(21,l1.get(1));
        assertEquals(Integer.MIN_VALUE,l1.get(2));
        assertEquals(56,l1.get(3));
        assertEquals(l1.toString(), "[2147483647,21,-2147483648,56]");
    }
    //Testing prepending with large and small values as well as common value to
    // see if it functions as well as printing result to check if value is
    //properly formatted.
    @Test
    void prepend12() {
        LList<Integer> l1 = new SLinkedList<Integer>();
        l1.prepend(42);
        assertEquals(1, l1.size());
        assertEquals(42, l1.tail());
        assertEquals(42, l1.tail());
        l1.prepend(Integer.MIN_VALUE);
        assertEquals(2, l1.size());
        assertEquals(Integer.MIN_VALUE, l1.head());
        assertEquals(42, l1.tail());
        l1.prepend(Integer.MAX_VALUE);
        assertEquals(3, l1.size());
        assertEquals(Integer.MAX_VALUE, l1.head());
        assertEquals(42, l1.tail());
        assertEquals(l1.toString(), "[2147483647,-2147483648,42]");

    }

    //Testing contains in several aspects. Whether values are valid.
    //If nested linked lists can be used with the contains, and a couple general
    //test cases. Testing the true false return values to ensure they work
    //properly.
    @Test
    void contains12() {
        LList<String> l1 = new SLinkedList<String>();
        LList<String> l2 = new SLinkedList<String>();
        LList<LList<String>> test = new SLinkedList<>();
        LList<Integer> l3 = new SLinkedList<Integer>();
        l1.append("hello");
        l1.append("My");
        l1.append("Name");
        l1.append("Is");
        l1.append("abdulgani");
        test.append(l1);
        l3.append(Integer.MAX_VALUE);
        assertEquals(true, l1.contains("hello"));
        assertEquals(true, l1.contains("My"));
        assertEquals(true, test.contains(l1));
        assertEquals(false, test.contains(l2));
        assertEquals(true, l3.contains(Integer.MAX_VALUE));
        assertEquals(true, test.get(0).contains("Name"));
    }

    //Testing remove by checking indexes of elements, seeing if remove was called
    //and corresponding elements were removed, and also combined a list to see if elements
    //are properly ordered after removal, and also finally removed one sublist from the nested list.
    @Test
    void remove12() {
        LList<Integer> l1 = new SLinkedList<Integer>();
        LList<Integer> l2 = new SLinkedList<Integer>();
        LList<LList<Integer>> l3 = new SLinkedList<>();
        l1.append(1);
        l1.append(2);
        l1.append(-3);
        l1.append(4);
        l1.append(5);
        l2.append(545);
        l2.append(-24);
        l2.append(0);
        l3.append(l1);
        l3.append(l2);

        assertEquals(1,l1.get(0));
        assertEquals(2,l1.get(1));
        assertEquals(-3,l1.get(2));
        assertEquals(4,l1.get(3));
        assertEquals(5,l1.get(4));
        assertEquals(true,l1.remove(1));
        assertEquals(true,l1.remove(2));
        assertEquals("[-3,4,5]",l1.toString());
        assertEquals(-3,l1.get(0));
        assertEquals(4,l1.get(1));
        assertEquals(5,l1.get(2));
        assertEquals(false,l1.remove(6));
        assertEquals(l1,l3.get(0));
        assertEquals(l2,l3.get(1));
        assertEquals("[[-3,4,5],[545,-24,0]]",l3.toString());
        l3.remove(l1);
        assertEquals("[[545,-24,0]]",l3.toString());

    }
//Testing size with get and also testing if get can return the
    //Integer min value as well as positive normal numbers,
    //Including zero.
    @Test
    void get12() {
        LList<Integer> lst = new SLinkedList<>();
        for (int i = 0; i < 5; i++) lst.append(i);
        assertEquals(lst.size(), 5);
        for (int i = 0; i < 5; i++) {
            assertEquals(i, lst.get(i));
            lst.append(i);
        }
        lst.append(Integer.MIN_VALUE);
        lst.append(999);
        assertEquals(12,lst.size());
        assertEquals(4,lst.get(4));
        assertEquals(2,lst.get(2));
        assertEquals(Integer.MIN_VALUE,lst.get(10));
        assertEquals(999,lst.get(11));
        assertEquals(0,lst.get(5));
    }
    //Testing insert before on several different values. Creating two lists to test separated.
    //Tested it with integer min value as the value inserted.
    //converted to string to demonstrate if the output came out appropriately.
    @Test
    void insertBefore12() {
        LList<Integer> l1 = new SLinkedList<Integer>();
        LList<Integer> l2 = new SLinkedList<Integer>();
        l1.append(1);
        l1.append(2);
        l1.append(3);
        l1.append(4);
        l1.append(5);
        l2.append(-3242);
        l2.append(54);
        l2.append(0);
        l1.insertBefore(7,5);
        l2.insertBefore(30,0);
        assertEquals("[-3242,54,30,0]",l2.toString());
        assertEquals("[1,2,3,4,7,5]",l1.toString());
        l1.insertBefore(0,7);
        assertEquals("[1,2,3,4,0,7,5]",l1.toString());
        l1.insertBefore(0,1);
        assertEquals("[0,1,2,3,4,0,7,5]",l1.toString());
        l1.insertBefore(Integer.MIN_VALUE,0);
        assertEquals("[-2147483648,0,1,2,3,4,0,7,5]",l1.toString());
        assertEquals(-2147483648,l1.get(0));
        assertEquals(0,l1.get(1));
        assertEquals(1,l1.get(2));
        assertEquals(2,l1.get(3));
        assertEquals(3,l1.get(4));


    }

}