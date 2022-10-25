package a2;
public class Student{
    // TODO 1 Add instance variables and write the class invariant
    // Declare the following fields. These fields will hold information describing each Student.
    // Make the fields private and include comments describing each of them in the form of a class invariant

    /** firstName. First name of this Student.
    Must be a non-empty String. firstName.length > 0**/
    private String firstName;

    /** lastName. Last name of this Student.
     * Must be a non-empty String. lastName.length > 0.
     **/
    private String lastName;

    /** year. The year this Student is in school.
     * E.g. 1 if Freshman, 2 if Sophomore, etc. Must be year > 0
     **/
    private int year;

    /** course. The Course this Student is enrolled in.
     * This Student may be enrolled in at most 1 course.
     * null if this Student is not enrolled in any course.**/
    private Course course;

    /** Constructor: Create a new Student with first name f, last name l, and year y.
     * This student is not enrolled in any Courses.
     * Requires: f and l have at least one character and y > 0. */

    /**Class Invariant: Returns boolean and checks that invariants are maintained throughout the program**/

    boolean classInv(){
        //class invariant boolean method that is called throughout
        // the class to ensure all requirements are met
        return firstName.length() > 0 && lastName.length() > 0 && year > 0;

    }
    public Student(String f, String l, int y) {
        //constructs all values
            //TODO 1
           firstName = f;
           lastName = l;
           year = y;
           course = null;
    }

    /** Returns the first name of this Student. firstName > 0 */
    public String firstName() {
       // TODO 3
        //Checks class inv and then returns value.
        assert classInv();
        return firstName;
    }

    /** Returns the last name of this Student. lastName > 0 */
    public String lastName() {
        //TODO 4
        //Checks class inv and then returns value.
        assert classInv();
            return lastName;
    }

    /** Returns the first and last name of this Student in the format "First Last". */
    public String fullName() {
        //Combining the two strings that already have met class invariant requirements
        // TODO 5
        return firstName + " " + lastName;
    }

    /** Returns the year in school this Student is in. year > 0 */
    public int year() {
        //Checks that class invariant component (year) is true through if statement
        //if year 0 is inputted, it sets year to 1;
        if (year > 0) {

            return year;
        }
        return 1;
    }
    /** Returns the course this student is enrolled in. */
    public Course course() {
        // TODO 7
        //Returns course that student is enrolled in at that time and is null if student has not joined course
        return course;
    }

    /** Enroll this Student in Course c.
     * Requires: The student is not enrolled in a course already.*/
    public void joinCourse(Course c) {
        // TODO 8
        //Course is joined if student is not currently enrolled in course
        //Ensures that student is not enrolled into another course
            if(course == null)
            {

                course = c;
            }

        }




    /**
     * Drop this Student from their Course. Requires: This student is enrolled in a course already.
     */
    public void leaveCourse() {
        //If student is enrolled, they can then leave a course
        if (course != null)
        {
            course = null;
        }

        // TODO 9
    }

    /** Return the full name of this Student */
    public String toString() {
        return fullName();
    }
}