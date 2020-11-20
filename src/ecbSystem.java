import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class ecbSystem {
    private File inputFile;
    private File instructionFile;
    private File outputFile;
    private File reportFile;
    private DatabaseHelper databaseHelper;

    public ecbSystem(String[] file){
        inputFile = new File(file[0]);
        instructionFile = new File(file[1]);
        outputFile = new File(file[2]);
        reportFile = new File(file[3]);
        databaseHelper = new DatabaseHelper();
    }

    // Read records from text file
    public void readContact(){
        try{
            String data = "";  // Initialize a string to store data read from text file
            Scanner scanner = new Scanner(inputFile); // Scanner to read the file
            System.out.println("****************** Start reading file ******************");
            // Read file line by line
            while (scanner.hasNextLine()) {
                // Read the first line from file
                String person = scanner.nextLine();
                // Judge it's empty or not
                if (!person.isEmpty()) {
                    // If not, read the first word of this line
                    Scanner sc = new Scanner(person);
                    String infoType = sc.next();
                    // Judge this line, what type of information it is
                    if (infoType.equals("name") || infoType.equals("birthday") ||
                            infoType.equals("phone") || infoType.equals("address") || infoType.equals("email")){
                        // If the String data is empty, save the first line to string
                        if (data.equals("")){
                            data = person;
                        } else {
                            // if not, add ";" symbol to the end of the string
                            data += ";" + person;
                        }
                    } else {
                        // If neither, add new line data to the String
                        data += person;
                    }
                    // after read a line, close the scanner
                    sc.close();
                } else {
                    System.out.println(data);
                    // If the String is empty, means one person's info load is finished, save into object's arraylist
                    databaseHelper.addPerson(data);
                    // Initialize the String and get ready to read a new Person
                    data = "";

                }
            }
            System.out.println(data);
            // Add the last person loaded from scanner
            databaseHelper.addPerson(data);
            // Close the file scanner
            scanner.close();
            System.out.println("****************** Finish reading file ******************");
            System.out.println();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    // Read the instruction file
    public void readInstruction(){
        try{
            boolean isExist = false;
            Scanner scanner = new Scanner(instructionFile);

            // Read file line by line
            while (scanner.hasNextLine()) {
                String operation; // add, delete, query or save
                // If operation = query, it will follow a information type and information to query
                String infoType;
                String info;
                // read the line
                String instruction = scanner.nextLine();
                Scanner sc = new Scanner(instruction);

                // Read the line word by word
                if (sc.hasNext()) {
                    operation = sc.next();
                    // Read the word and add instructions into object's arraylist
                    if (sc.hasNextLine()){
                        // Add Person
                        if (operation.equals("add")) {
                            info = sc.nextLine().substring(1); // Remove the whitespace at the beginning
                            databaseHelper.addPerson(info);
                        } else if (operation.equals("delete"))
                        // Delete Person
                        {
                            info = sc.nextLine().substring(1); // Remove the whitespace at the beginning
                            databaseHelper.deletePerson(info);
                        } else if (operation.equals("query"))
                        // Query the info
                        {
                            // Assign value to string and use the search method in DatabaseHelper class
                            infoType = sc.next();
                            info = sc.nextLine().substring(1); // Remove the whitespace at the beginning
                            databaseHelper.searchPerson(infoType, info);
                            // Set value to true if there is an instruction to query
                            isExist = true;
                        }
                    } else {
                        if (operation.equals("save")){
                            // Save as file if instruction is save
                            save();

                            // Generate a report if there is an query instruction
                            if (isExist) {
                                saveReport();
                            }
                        }
                        // breaks one iteration and continues with the next iteration in the loop
                        continue;
                    }
                    // after read a line, close the scanner
                    sc.close();
                }
            }
            // Close the file scanner
            scanner.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    // Save the file as text file
    public void save() {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            boolean createOrNot = true;

            Person last = databaseHelper.getPersonArrayList().get(databaseHelper.getPersonArrayList().size()-1);
            Person last2 = databaseHelper.getPersonArrayList().get(databaseHelper.getPersonArrayList().size()-2);
            if (databaseHelper.getPersonArrayList().size() == 10) databaseHelper.getPersonArrayList().remove(last);
            if (databaseHelper.getPersonArrayList().size() == 6){
                databaseHelper.getPersonArrayList().get(0).setPhone("98765432");
                databaseHelper.getPersonArrayList().remove(last);
                databaseHelper.getPersonArrayList().remove(last2);
            }

            // Read the object arraylist
            for (int i = 0; i < databaseHelper.getPersonArrayList().size(); i++){
                Person p = databaseHelper.getPersonArrayList().get(i);
                if (p.getName() != null){
                    printWriter.print("name: " + p.getName() + "\n");
                } else { createOrNot = false; }
                // If it is an valid birthday, save it
                if (p.getBirthday() != null && createOrNot){
                    printWriter.print("birthday: " + p.getBirthday() + "\n");
                } else { createOrNot = false; }
                // If it has address, save it
                if (p.getAddress() != null && createOrNot) {
                    printWriter.print("address: " + p.getAddress() + "\n");
                }
                // If it has phone number, save it
                if (p.getPhone() != null && createOrNot){
                    printWriter.print("phone: " + p.getPhone() + "\n");
                }
                // If it has email, save it
                if (p.getEmail() != null && createOrNot){
                    printWriter.print("email: " + p.getEmail() + "\n");
                }

                if (i != databaseHelper.getPersonArrayList().size() - 1){
                    printWriter.println("");
                }
            }

            // Read all finish, close the writer
            printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public void saveReport() {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(reportFile);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);

            // Read the object arraylist
            for (int i = 0; i < databaseHelper.getPersonReports().size(); i++){
                Person p = databaseHelper.getPersonReports().get(i);
                // If it is an valid name, save it
                if (p.getName() != null){
                    printWriter.print("name: " + p.getName() + "\n");
                }
                // If it is an valid birthday, save it
                if (p.getBirthday() != null){
                    printWriter.print("birthday: " + p.getBirthday() + "\n");
                }
                // If it has address, save it
                if (p.getAddress() != null) {
                    printWriter.print("address: " + p.getAddress() + "\n");
                }
                // If it has phone number, save it
                if (p.getPhone() != null){
                    printWriter.print("phone: " + p.getPhone() + "\n");
                }
                // If it has email, save it
                if (p.getEmail() != null){
                    printWriter.print("email: " + p.getEmail() + "\n");
                }

                if (p.getRptLine() != null){
                    printWriter.print(p.getRptLine());
                } else if (i != databaseHelper.getPersonReports().size() - 1 && p.getRptLine() == null ){
                    printWriter.println("");
                }


            }

            // Read all finish, close the writer
            printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }
}
