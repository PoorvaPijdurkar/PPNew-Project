package com.procure.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerDetailsDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pin;
    private String state;
    private String country;
    private String email;
    private String mobileNo;
    private String gstNo;
    private String leadSource;
}
