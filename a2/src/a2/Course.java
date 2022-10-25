package a2;

public class Course{

    /**
     * List of all students enrolled in this Course.
     */
    private StudentList students;
    /**
     * The hour at which lecture for this Course is held (in 24-hour time). 0 <= hour <= 23
     */
    private int hour;
    /**
     * The minutes at which lecture for this Course is held. 0 <= min <= 59 The lectures for this
     * course are at hour:min
     */
    private int min;
    /**
     * The location of lectures at this course (e.g. Statler Hall Room 185) Must be non-empty
     * location.length > 0.
     */
    private String location;
    /**
     * The last name of the professor of this course (e.g. Myers, Muhlberger, Gries) Must be
     * non-empty professor.length > 0;
     */
    private String prof;

    /**
     * The name of this course (e.g. Object-Oriented Programming and Data Structures) Must be
     * non-empty name.length > 0.
     */
    private String name;

    /**Class Invariant: Returns boolean and checks that invariants are maintained throughout the program
     **/

    //Boolean classInv method that checks class invariants throughout the function.
    boolean classInv(){
        return 0 <= hour && hour <= 23 && 0 <= min && min <= 59 && location.length() > 0
                && prof.length() > 0 && name.length() > 0;

    }

    /**
     * Constructor: Create new Course with name n, professor last name prof, location loc,<br> and
     * lectures are held at time hour:min. The course has no students. Precondition: n, prof, and
     * loc have at least one character in them, 0 <= hr <= 23, 0 <= min <= 59.
     */
    public Course(int hr, int m, String loc, String pr, String n) {
        // TODO 18
        name = n;
        location = loc;
        hour = hr;
        students = new StudentList();
        min = m;
        prof = pr;
        // Note that an empty StudentList is not the same as null
    }

    /**Give Course to Student Class*/
    /** Return the name of this course. */
    public String getName() {
        // TODO 19
        //Checks class inv name.length > 0
        assert classInv();
        return name;
    }

    /**
     * Return the time at which lectures are held for this course in the format hour:min AM/PM using
     * 12-hour time. For example, "11:15 AM", "1:35 PM". Add leading zeros to the minutes if necessary.
     */
    public String getLectureTime() {
        // TODO 20
        //checks classInv that hour and minute meet classinv requirements 0 <= hr <= 23, 0 <= min <= 59.
        //checks if hour is less than 12, and mutates time accordingly, and if time is not less than 12, creates
        //2 paths where time is between 12pn-1pm, and another path from 1pm - midnight to ensure time return properly.
        assert classInv();
        String time = "";
        if(hour < 12)
        {
            if(min <= 10)
            {
                time = hour + ":0" + min + " AM";
            }
            else {
                time = hour + ":" + min + " AM";
            }
        }
        else
        {
            if(hour < 13)
            {
                if(min < 10)
                {
                    time = hour + ":0" + min + " PM";
                }
                else {
                    time = hour + ":" + min + " PM";
                }
            }
            else {
                if(min < 10)
                {
                    time = (hour - 12) + ":0" + min + " PM";
                }
                else {
                    time = (hour - 12) + ":" + min + " PM";
                }
            }

        }
        return time;
    }

    /**
     * Return the location of lectures in this course.
     */
    public String getLocation() {
        // TODO 21
        //checks class invariant that the location.length() > 0.
        assert classInv();
        return location;
    }

    /**
     * Return the name of the professor in the format "Professor LastName"
     */
    public String getProfessor() {
        // TODO 22
        //checks class invariant that the prof.length() > 0.
        assert classInv();
        return "Professor " + prof;
    }

    /**
     * Return the String representation of the list of students enrolled in this course
     */
    public String getStudents() {
        //TODO 23
        //returns student value in string form
        return students.toString();
    }

    /**
     * Enroll a new student s to this course. If Student s is already enrolled in a course, they
     * cannot enroll in this course. Return true if the student was successfully enrolled in the
     * course.
     */
    public boolean enrollStudent(Student s) {
        // TODO 24
        assert classInv();
        Course b = new Course(hour, min, location, prof, name);
        //Maintains class invariant manually by only adding if the studentslist does not contain s.
        //only adding if the student is not in the list.
        if (!students.contains(s))
        {
            s.joinCourse(b);
            students.append(s);
            return true;
        }

        return false;
        // Remember that the class invariant of all classes must be kept true.
        // In other words, make sure that every field is correctly modified based on its
        // Javadoc comments.

    }

    /**
     * Drop Student s from this course. If Student s is not enrolled in this course, they cannot be
     * dropped from this course. Return true if the student was successfully dropped from the
     * course.
     */
    public boolean dropStudent(Student s) {
//TODO 25
        //class invariant maintained by only running the remove method if the studentlist does not contain student s.
        if (students.contains(s))
        {
            students.remove(s);
            return true;
        }

        // Remember that the class invariant of all classes must be kept true.
        // In other words, make sure that every field is correctly modified based on its
        // Javadoc comments.

        return false;
    }

    /**
     * Print the Course information in tabular format
     */
    public void print() {
        System.out.printf("%-40s%-12s%-20s%-40s",
                name, getLectureTime(), prof, location);
    }
}