import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseHelper {
    private ArrayList<Person> personArrayList;
    private ArrayList<Person> modifyPersonList;
    private ArrayList<Person> personReports;
    private boolean isModified = false;

    public DatabaseHelper(){
        personArrayList = new ArrayList<Person>();
        modifyPersonList = new ArrayList<Person>();
        personReports = new ArrayList<Person>();
    }

    // Add new person to database(Arraylist)
    public void addPerson(String s){
        Person person = new Person(s);

        String name = person.getName();
        String birthday = person.getBirthday();
        String address = person.getAddress();
        String phone = person.getPhone();
        String email = person.getEmail();

        String nameRegex = "^[A-Za-z\\s]+[\\.]?[A-Za-z\\s]*$";
        String birthdayRegex = "((((0[1-9]|[12]\\d|3[01])-(0[13578]|10|12))|((0[1-9]|[12]\\d|30)-(0[469]|11))|((0[1-9]|1[0-9]|2[0-8]))-02)-(19\\d\\d|(200\\d|201[0-9])))|(29-02-((?!1900)19([02468][048]|[13579][26])|(2000)|(2004)|(2008)|2012))";
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (birthday != null && !birthday.matches(birthdayRegex)){
            String[] dayMonthYear = birthday.split("-");
            String day = dayMonthYear[0];
            String month = dayMonthYear[1];
            String year = dayMonthYear[2];

            if (dayMonthYear[0].length() == 1){
                day = "0" + dayMonthYear[0];
            }
            if (dayMonthYear[1].length() == 1){
                month = "0" + dayMonthYear[1];
            }
            birthday = day + "-" + month + "-" + year;
            person.setBirthday(birthday);
        }

        if (phone != null){
            if (phone.startsWith("0")){
                String newPhone = phone.substring(1);
                person.setPhone(newPhone);
            }
        }

        if (name != null && birthday != null && email != null){
            if (name.matches(nameRegex) && birthday.matches(birthdayRegex) && email.matches(emailRegex)){
                personArrayList.add(person);
            } else {
                person.setEmail(null);
                personArrayList.add(person);
            }
        } else if (name != null && birthday != null){
            if (name.matches(nameRegex) && birthday.matches(birthdayRegex)){
                personArrayList.add(person);
            }
        }

    }

    // Update person info by string, if no this person, create one
    public void updatePerson(String s){
        Person person = new Person(s);

        String name = person.getName();
        String birthday = person.getBirthday();
        String address = person.getAddress();
        String phone = person.getPhone();
        String email = person.getEmail();

        String newEmail, newPhone = null;

        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (email != null && email.matches(emailRegex)) newEmail = email; else newEmail = null;

        if (phone != null){
            if (phone.startsWith("0")){
                newPhone = phone.substring(1);
            }
        }

        // Read the arraylist and delete the person object
        for (Person p: personArrayList){
            String oldname = p.getName();
            String oldBirth = p.getBirthday();
            if(oldname.equals(name) && oldBirth.equals(birthday))
            {

                if (address != null) p.setAddress(address);
                if (phone != null) p.setPhone(phone);
                if (email != null && email.matches(emailRegex)) p.setEmail(email);
            } else {
                Person newPerson = new Person(name, birthday, address, newPhone, newEmail);
                modifyPersonList.add(newPerson);
            }
        }
        personArrayList.addAll(modifyPersonList);
    }

    // Delete person by name or by name;Birthday
    public void deletePerson(String s){
        // Split the instruction by ";"
        String[] deleteStr = s.split(";\\s");
        String deleteName = deleteStr[0];
        String deleteBirthday = deleteStr[1];

        String birthdayRegex = "((((0[1-9]|[12]\\d|3[01])-(0[13578]|10|12))|((0[1-9]|[12]\\d|30)-(0[469]|11))|((0[1-9]|1[0-9]|2[0-8]))-02)-(19\\d\\d|(200\\d|201[0-9])))|(29-02-((?!1900)19([02468][048]|[13579][26])|(2000)|(2004)|(2008)|2012))";
        if (deleteBirthday != null && !deleteBirthday.matches(birthdayRegex)){
            String[] dayMonthYear = deleteBirthday.split("-");
            String day = dayMonthYear[0];
            String month = dayMonthYear[1];
            String year = dayMonthYear[2];

            if (dayMonthYear[0].length() == 1){
                day = "0" + dayMonthYear[0];
            }
            if (dayMonthYear[1].length() == 1){
                month = "0" + dayMonthYear[1];
            }
            deleteBirthday = day + "-" + month + "-" + year;
        }

        // Read the arraylist and delete the person object
        Iterator<Person> iter = personArrayList.iterator();
        while(iter.hasNext()){
            Person p = iter.next();
            if(p.getName().equals(deleteName) && p.getBirthday().equals(deleteBirthday))
            {
                iter.remove();
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

        // If the instruction is to generate report by name
        if (infoType.equals("name")){
            for (Person p: personArrayList){
                if (p.getName().equals(info)){
                    personReports.add(p);
                }
            }
        }

        // If the instruction is to generate report by Birthday
        if (infoType.equals("birthday")){
            for (Person p: personArrayList){
                if (p.getBirthday().equals(info)){
                    personReports.add(p);
                }
            }
        }

        // If the instruction is to generate report by phone number
        if (infoType.equals("phone")){
            for (Person p: personArrayList){
                if (p.getPhone() != null && p.getPhone().equals(info)){
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

    public ArrayList<Person> getModifyPersonList() {
        return modifyPersonList;
    }

    public boolean isModified() {
        return isModified;
    }
}
