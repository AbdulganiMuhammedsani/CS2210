package a3;


import java.io.*;

public class Main {
    /**
     * Replace "-1" by the time you spent on A3 in hours.
     * Example: for 3 hours 15 minutes, use 3.25
     * Example: for 4 hours 30 minutes, use 4.50
     * Example: for 5 hours, use 5 or 5.0
     */
    public static double timeSpent = 8;

    /* TODO 6
     * Write a function that takes in a csv file name as a string and returns a Linked List
     * representation of the CSV table.
     */

    public static LList<LList<String>> csvToList(String file) {
        LList<String> fileInfo = new SLinkedList<String>(); //Initializing file lists
        LList<String> container = new SLinkedList<String>();
        LList<LList<String>> test = new SLinkedList<>();
        try {
            Reader z = new FileReader(file);
            BufferedReader br = new BufferedReader(z);
            String line = "";
            int i = 0;
            line = br.readLine();//read the first line and create reading buffer
            while (line != null) {

                String[] arr = line.split(",");
                if (line != null)
                {
                    LList<String> temp = new SLinkedList<String>();
                    //create a temporary string that I use and iterate through array
                    //that has text elements and append them to the linked list.

                    for (int j = 0; j < arr.length; j++)
                    {
                        temp.append(arr[j]);
                    }
                    test.append(temp);
                    //Then I append these linked lists
                    //Into linked lists of linked lists
                }
                i++;
                line = br.readLine();
            }

        } catch (IOException e) {
            System.err.println("Error reading from file " + file);
            System.exit(1); // Files can rcause IO Exceptions so
            //ensuring catching and provide a user response.
        }
        return test;
    }


    /* TODO 7
     * Write a function that takes in two lists representing tables and
     * computes their left join.
     */
    public static LList<LList<String>> join(LList<LList<String>> table1,
                                            LList<LList<String>> table2) {
        //TODO
        int counter = 0;
        final int container = table1.size();
        boolean test = false;
        for (int i = 0; i < container; i++) {
            for (int j = 0; j < table2.size(); j++) {
                //Checking general condition that the initial table contains the
                //same element as another file. IF so I merge therm
                if (table1.get(i).get(0).toString().contains(table2.get(j).get(0).toString()))
                {
                    //Creating a counter to see if the original list has more that one column
                    //with the same value.
                    //If this is the case, as well as the second file containing these same 2.
                    // must produce 4 copies of file
                    if(counter >= 1)
                    {
                        for(int c = 0; c < container;c++)
                        {
                            for(int b = c+1; b < container;b++)
                            {
                                if(table1.get(c).get(0).equals(table1.get(b).get(0)))
                                {
                                    test = true;
                                }
                            }
                        }
                        if(test == true) {
                            LList<String> temp = new SLinkedList<>();
                            temp.append(table1.get(i).get(0));
                            temp.append(table1.get(i).get(1));
                            temp.append(table2.get(j).get(1));
                            temp.append(table2.get(j).get(2));
                            table1.insertBefore(temp, table1.get(i));
                            i += 1;
                        }

                    }
                    else {
                        for (int k = 1; k < table2.get(j).size(); k++) {
                            table1.get(i).append(table2.get(j).get(k)); // General
                            // case of merging occurs here.

                        }
                    }
                    counter++;
                }

            }
            counter = 0;

        }
            //Returns a joined list as a linked list in a linked list.
        return table1;
    }



    public static void Format(LList<LList<String>> finalcsv) {
        //TODO
        //Iterating through the linked lit and printing line by line without brackets.
            for(int i = 0; i < finalcsv.size();i++)
            {
                System.out.println((finalcsv.get(i).toString()).substring(1,finalcsv.get(i).toString().length()-1));
            }
    }

    /**
     * Effect: Print a usage message to standard error.
     */
    public static void usage() {
        System.err.println("Usage: a3.Main <file1.csv> <file2.csv>");
    }

    /* TODO 8
     * Write the main method, which parses the command line arguments, reads CSV files
     * into tables, and prints out the resulting table resulting from a left join of the
     * input tables. Hint: use helper methods.
     */
    public static void main(String[] args) {
        //checks for out of bounds error if files entered in build configuration
        //are not present
        try {
            Format(join(csvToList(args[0]), csvToList(args[1])));
            //Printing out the joined list given two csv files as parameters.
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Must Name 2 Files");
            System.exit(1);
        }


    }
}
