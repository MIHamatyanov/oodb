package lab1;

abstract class Person {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    Person(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
