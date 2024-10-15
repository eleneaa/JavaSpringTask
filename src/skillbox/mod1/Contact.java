package skillbox.mod1;

public class Contact {
    private String fullName;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.fullName = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Contact(){}

    // Геттеры и сеттеры
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Метод toString()
    @Override
    public String toString() {
        return fullName + " | " + phoneNumber + " | " + email;
    }
}
