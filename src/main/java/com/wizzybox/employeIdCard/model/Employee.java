package com.wizzybox.employeIdCard.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth" , nullable = false)
    private String dateOfBirth;

    @Column(name = "gmail" , nullable = false , unique = true)
    private String email;

    @Column(name = "phone_number" , nullable = false , unique = true)
    private String phoneNumber;

    @Column(name = "qr_code_path")
    private String qrCodePath;


}


//{
//        "firstName": "-------",
//        "lastName": "-----",
//        "email": "-------",
//        "phoneNumber": "-------",
//        "dateOfBirth": "yyyy-mm-dd"
//}
