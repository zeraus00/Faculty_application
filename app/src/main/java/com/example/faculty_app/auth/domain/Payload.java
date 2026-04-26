package com.example.faculty_app.auth.domain;

public class Payload {
    private final Integer id;
    private final String email;
    private final String username;
    private final String surname;
    private final String firstName;
    private final String middleName;
    private final String gender;
    private final String contactNumber;
    private final String role;
    private final String college;
    private final String facultyRank;

    private Payload(Builder b) {
        id = b.id;
        email = b.email;
        username = b.username;
        surname = b.surname;
        firstName = b.firstName;
        middleName = b.middleName;
        gender = b.gender;
        contactNumber = b.contactNumber;
        role = b.role;
        college = b.college;
        facultyRank = b.facultyRank;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getGender() {
        return gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getRole() {
        return role;
    }

    public String getCollege() {
        return college;
    }

    public String getFacultyRank() {
        return facultyRank;
    }

    public static class Builder {
        private Integer id;
        private String email;
        private String username;
        private String surname;
        private String firstName;
        private String middleName;
        private String gender;
        private String contactNumber;
        private String role;
        private String college;
        private String facultyRank;

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder surname(String val) {
            surname = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder middleName(String val) {
            middleName = val;
            return this;
        }

        public Builder gender(String val) {
            gender = val;
            return this;
        }

        public Builder contactNumber(String val) {
            contactNumber = val;
            return this;
        }

        public Builder role(String val) {
            role = val;
            return this;
        }

        public Builder college(String val) {
            college = val;
            return this;
        }

        public Builder facultyRank(String val) {
            facultyRank = val;
            return this;
        }

        public Payload build() {
            return new Payload(this);
        }
    }
}