public class Person {
    private String name;
    private String surName;
    private String email;

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person(String name, String surName, String email) {
        this.name = name;
        this.surName = surName;
        this.email = email;
    }
    public void printDetails(){ //method for display person class details
        System.out.println("Name               :"+getName());
        System.out.println("Surname            :"+getSurName());
        System.out.println("Email              :"+getEmail());
    }
}


