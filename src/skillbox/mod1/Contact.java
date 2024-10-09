package skillbox.mod1;

import java.util.Objects;
import java.util.regex.Pattern;

public class Contact {
    private String fullName;
    private String phoneNumber;
    private String email;

    public Contact(String fullName, String phoneNumber, String email){
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Геттеры
    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    // Сеттеры
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        String regexPattern = "^((\\+7|7|8)+([0-9]){10})$";
        if (patternMatches(phoneNumber, regexPattern)){
            this.phoneNumber = phoneNumber;
        }
        else System.out.println("Incorrect phone number!!");
    }

    public void setEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (patternMatches(email, regexPattern)){
            this.email = email;
        }
        else System.out.println("Incorrect email!!");
    }

    @Override
    public String toString() {
        return "Contact{" +
                "fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Contact otherContact = (Contact) obj;

        return Objects.equals(this.fullName, otherContact.fullName) &&
                Objects.equals(this.phoneNumber, otherContact.phoneNumber) &&
                Objects.equals(this.email, otherContact.email);
    }



    public static boolean patternMatches(String string, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(string)
                .matches();
    }
}
