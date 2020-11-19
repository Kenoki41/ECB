public class Name {
    private String fullName;
    private String firstName;
    private String middleName;
    private String lastName;

    public Name(String fullName){
        this.fullName = fullName;
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = firstName + " " + lastName;
    }

    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        fullName = firstName + " " + middleName + " " + lastName;
    }

    // Validate the name
    public boolean isValidName(){
        if (!fullName.matches("^[A-Za-z\\s]+[\\.]?[A-Za-z\\s]*$")){
            return false;
        }
        return true;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }
}
