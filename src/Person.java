public class Person {
    private Name name;
    private Birthday birthday;
    private String phone;
    private String email;
    private String address;
    private String rptLine = null;

    public Person(){
        name = null;
        birthday = null;
        phone = null;
        email = null;
        address = null;
    }

    public Person(Name name, Birthday birthday, String phone, String email, String address){
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Person(String info){
        getPersonInfo(info);
    }

    // Read the string and save to arraylist, assign value to variables if match requirement
    public void getPersonInfo(String s){
        // Remove blank and split with ";" and save into arraylist
        String[] infoArraylist = s.split(";");

        for(int i = 0; i < infoArraylist.length; i++){
            String[] str = infoArraylist[i].split("\\s");

            // Read the input and construct the object
            if(str[0].equals("name")){
                if (str.length == 2){
                    name = new Name(str[1]);
                } else if(str.length == 3){
                    name = new Name(str[1], str[2]);
                } else if (str.length == 4){
                    name = new Name(str[1], str[2], str[3]);
                }

            } else if(str[0].equals("birthday")){
                birthday = new Birthday(str[1]);

            } else if (str[0].equals("phone")){
                // Check the phone number is pure number, if yes remove First 0
                if (str[1].matches("[0-9]+")){
                    phone = str[1].replaceFirst("^0+(?!$)", "");
                }

            } else if (str[0].equals("address")){
                // Check address or not, if yes, read it and give correct format
                int i1 = str.length - 1;
                if (str[i1].matches("[0-9]+")) {
                    address = "";
                    for (int j = 1; j < str.length; j++){
                        if (address.equals("")){
                            address = str[j];
                        } else {
                            address += " " + str[j];
                            address = address.replaceAll("\\s\\s","");
                        }
                    }
                }

            } else if (str[0].equals("email")){
                // Check email or not
                boolean isEmail = false;
                for (int j = 0; j < str[1].length(); j++){
                    if (str[1].charAt(j) == '@') {
                        isEmail = true;
                    }
                }
                if (isEmail) {
                    email = str[1];
                }
            }

        }
    }

    // Update the existing information
    // The reason we use if(!=null) is that you can't assign value if there is no such info
    public void update(Person person){
        if (person.getPhone() != null){
            phone = person.getPhone();
        }
        if (person.getAddress() != null){
            address = person.getAddress();
        }
        if (person.getEmail() != null){
            email = person.getEmail();
        }
    }

    public void startLine(String infoType, String info){
        rptLine = "====== query " + infoType + " "+ info +" ======\n";
    }

    public void endLine(String infoType, String info){
        rptLine = "====== end of query " + infoType +" " + info + " ======\n";
    }

    public Name getName() {
        return name;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getRptLine() {
        return rptLine;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
