import java.util.ArrayList;

public class DatabaseHelper {
    private ArrayList<Person> personArrayList;
    private ArrayList<Person> personReports;

    public DatabaseHelper(){
        personArrayList = new ArrayList<Person>();
        personReports = new ArrayList<Person>();
    }

    // Add new person to database(Arraylist)
    public void addPerson(String s){
        Person person = new Person(s);
        personArrayList.add(person);
    }

    // Update person info by string, if no this person, create one
    public void updatePerson(String s){
        Person person = new Person(s);
        // Read the list, check existing or not
        if (person.getName().isValidName() && person.getBirthday().isValid()){
            boolean isExist = false;
            String fName = person.getName().getFullName();
            String bDay = person.getBirthday().dateString();

            for (Person p: personArrayList){
                if (p.getName().getFullName().equals(fName) && p.getBirthday().dateString().equals(bDay)){
                    isExist = true;
                    p.update(person);
                }
            }

            // If not exist, create one
            if (!isExist && person.getAddress() != null){
                personArrayList.add(person);
            }
        }
    }

    // Delete person by name or by name;Birthday
    public void deletePerson(String s){
        // Split the instruction by ";"
        String[] strings = s.trim().split("\\s*;\\s*");
        // if only name
        if (strings.length == 1){
            for (Person p: personArrayList){
                if (p.getName().getFullName().equals(strings[0])){
                    personArrayList.remove(p);
                }
            }
        } else if (strings.length == 2){
            // if name and birthday
            Birthday bDelete = new Birthday(strings[1]);
            for (int i = 0; i < personArrayList.size(); i++){
                String fName = personArrayList.get(i).getName().getFullName();
                String bDay = personArrayList.get(i).getBirthday().dateString();
                if (fName.equals(strings[0]) && bDay.equals(bDelete.dateString())){
                    personArrayList.remove(i);
                }
            }
        }
    }

    // Generate report format by giving information
    public void searchPerson(String infoType, String info){
        // First, we need to generate the ===== query xxx format
        Person startLine = new Person();
        startLine.startLine(infoType, info);
        personReports.add(startLine);
        // Remove provided information's blank space
        info = info.trim();

        // If the instruction is to generate report by name
        if (infoType.equals("name")){
            for (Person p: personArrayList){
                if (p.getName().getFullName().equals(info)){
                    personReports.add(p);
                }
            }
        }

        // If the instruction is to generate report by Birthday
        if (infoType.equals("birthday")){
            for (Person p: personArrayList){
                if (p.getBirthday().dateString().equals(info)){
                    personReports.add(p);
                }
            }
        }

        // If the instruction is to generate report by phone number
        if (infoType.equals("phone")){
            for (Person p: personArrayList){
                if (p.getPhone().equals(info)){
                    personReports.add(p);
                }
            }
        }

        // Create a end-line for report ===== End of query
        Person endLine = new Person();
        endLine.endLine(infoType, info);
        personReports.add(endLine);
    }

    public ArrayList<Person> getPersonArrayList(){
        return personArrayList;
    }

    public ArrayList<Person> getPersonReports(){
        return personReports;
    }
}
