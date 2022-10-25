package a2;

/** Test harness for Assignment 2
 */
public class A2Test {
    public static void main(String[] args) {
        testConstructor();//tests constructor and that it properly initializes values.
        testEmptyList(); //tests that an empty list properly returns the right value.
        testSize(); //tests if the Size method returns the size of the studentlist with students.
        testAppend();//tests if adding student to a studentlist works
        testRemove();//tests if removing a student from a student list functions
        testDuplicateRemoval(); //Tests that when duplicate, removes first instance of student
        testContains();//Tests if contains method words properly
        testGet();// tests if get works ad returns expected value
        testEnroll(); // tests if enroll properly enrolls a student
        testDrop(); //tests if drop removes a student properly
        testDynamicListSize(); // tests if the list size changes dynamic when necessary
        testCourse(); //test if the course function returns the current course that the student is in.

        // TODO Add more tests to thoroughly test StudentList
        // The methods provided do not necessarily test everything in each
        // case.  You will need to add more to the existing testing procedures
        // as well as add new testing procedures.  You can also add tests to
        // test the Course and Student classes.
        // 
        // Try to keep tests small and test features as independently as
        // possible.
    }

    public static void testConstructor() {
        Student s = new Student("Bill", "Nye", 4);
        System.out.println("s.firstName() = " + s.firstName());
        System.out.println("expected = Bill");
        System.out.println("s.lastName() = " + s.lastName());
        System.out.println("expected = Nye");
        System.out.println("s.year() = " + s.year());
        System.out.println("expected = 4");
        //calling elements that are constructed, checking if they return the right/expected values.
    }
    public static void testEmptyList() {
        StudentList list = new StudentList();
        Student s = new Student("Bill", "Nye", 4);
        System.out.println("list.size() = " + list.size());
        System.out.println("expected = 0");
        System.out.println("list.contains(s) = " + list.contains(s));
        System.out.println("expected = false");
        //testing to see is the list size, and list contains method return the right values.
        //when students are not added to the list
    }
    public static void testSize() {
        StudentList list = new StudentList();
        Student s = new Student("Bill", "Nye", 4);
        Student a = new Student("Connor", "Williams", 2);
        list.append(s);
        list.append(a);
        System.out.println("list.size() = " + list.size());
        System.out.println("expected = 2");
        //testing to see is the list size, and list contains method return the right values.
        //when students are added to the list
    }

    public static void testAppend() {
        StudentList list = new StudentList();
        Student s = new Student("Bill", "Nye", 4);
        list.append(s);
        System.out.println("list.size() = " + list.size());
        System.out.println("expected = 1");
        //testing append individually
    }

    public static void testRemove() {
        StudentList list = new StudentList();
        Student s = new Student("John", "Green", 3);
        list.append(s);
        System.out.println("list.size() = " + list.size());
        System.out.println("expected = 1");
        list.remove(s);
        System.out.println("list.size() = " + list.size());
        System.out.println("expected = 0");
        //testing remove after appending student to the list

    }
    public static void testDuplicateRemoval() {
        StudentList list = new StudentList();
        Student s = new Student("Bill", "Nye", 4);
        list.append(s);
        list.append(s);
        System.out.println("list = " + list);
        System.out.println("expected = [Bill Nye, Bill Nye]");
        list.remove(s);
        System.out.println("list = " + list);
        System.out.println("expected = [Bill Nye]");
        //code successfully removes one element from the list, closer look at implementation reveals
        //it is the initial element

    }
    public static void testContains() {
        StudentList list = new StudentList();
        Student s = new Student("John", "Green", 3);
        list.append(s);
        System.out.println("list.contains(s) = " + list.contains(s));
        System.out.println("expected = true");
        //testing contains independently
    }
    public static void testGet() {
        StudentList list = new StudentList();
        Student s = new Student("John", "Green", 3);
        list.append(s);
        System.out.println("list.get(0) = " + list.get(0));
        System.out.println("expected = John Green");
        //testing functionality of get method and return value

    }
    public static void testEnroll() {
        StudentList list = new StudentList();
        Student s = new Student("John", "Green", 3);
        list.append(s);
        Course java = new Course(12, 30, "Dall_Hall","Daniel","cs2210" );
        System.out.println("list.get(0) = " + list.get(0));
        System.out.println("expected = John Green");

        System.out.println("java.enrollStudents(s)= " + java.enrollStudent(s));
        System.out.println("expected = true");

        System.out.println("java.getStudents(s)= " + java.getStudents());
        System.out.println("expected = [John Green]");

        System.out.println("java.dropStudents(s)= " + java.dropStudent(s));
        System.out.println("expected = true");

        System.out.println("java.getStudents(s)= " + java.getStudents());
        System.out.println("expected = []");

        //testing the enrolling and dropping methods and ensuring they return
        // right values when getStudents is run after.


    }
    public static void testDrop() {
        StudentList list = new StudentList();
        Student s = new Student("John", "Green", 3);
        Student a = new Student("Tammy", "Green", 1);
        Student b = new Student("Scott", "Green", 2);
        list.append(s);
        list.append(a);
        list.append(b);
        Course java = new Course(12, 30, "Dall_Hall","Daniel","cs2210" );
        java.enrollStudent(s);
        java.enrollStudent(a);
        java.enrollStudent(b);
        System.out.println("java.getStudents()= " + java.getStudents());
        System.out.println("expected = [John Green, Tammy Green, Scott Green]");

        System.out.println("java.dropStudents(s)= " + java.dropStudent(s));
        System.out.println("expected = true");

        System.out.println("java.getStudents()= " + java.getStudents());
        System.out.println("expected = [Tammy Green, Scott Green]");

        //testing drop function individually


    }

    public static void testDynamicListSize() {
        StudentList list = new StudentList();
        Student s = new Student("John", "Green", 3);
        Student a = new Student("Tammy", "Green", 1);
        Student b = new Student("Scott", "Green", 2);
        Student c = new Student("Conny", "Green", 1);
        Student d = new Student("Samuela", "Green", 2);
        Student e = new Student("Baker", "Green", 2);
        list.append(s);
        list.append(a);
        list.append(b);
        list.append(c);
        list.append(d);
        Course java = new Course(12, 30, "Dall_Hall","Daniel","cs2210" );
        java.enrollStudent(s);
        java.enrollStudent(a);
        java.enrollStudent(b);
        java.enrollStudent(c);
        java.enrollStudent(d);

        System.out.println("java.getStudents()= " + java.getStudents());
        System.out.println("expected = [John Green, Tammy Green, Scott Green, Conny Green, Samuela Green]");

        System.out.println("list = " + list.backingArrayLength());
        System.out.println("expected = 5");

        list.append(e);
        java.enrollStudent(e);

        System.out.println("java.getStudents()= " + java.getStudents());
        System.out.println("expected = [John Green, Tammy Green, Scott Green, Conny Green, Samuela Green, Baker Green]");

        System.out.println("list = " + list.backingArrayLength());
        System.out.println("expected = 10");

        //running through the program, it tests that once the value has the necessity to go beyond the capacity,
        //the enrollment list increases , but the capacity increases according to the specifications.


    }
    public static void testCourse() {
        StudentList list = new StudentList();
        Student s = new Student("John", "Green", 3);
        list.append(s);
        Course java = new Course(12, 30, "Dall_Hall","Daniel","cs2210" );
        java.enrollStudent(s);
        System.out.println("s.course().getName()= " + s.course().getName());
        System.out.println("expected =  " + java.getName());
        s.leaveCourse();
        System.out.println("s.course()= " + s.course());
        System.out.println("expected = null ");

        //testing the functionality of the course method in Course, that it contains the course like expected.


    }
}
