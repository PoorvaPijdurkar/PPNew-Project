package com.procure.service;

import java.io.Serializable;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

public class CustomerDetailsCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private LongFilter id;
    private StringFilter firstName;
    private StringFilter lastName;
    private StringFilter companyName;
    private StringFilter address;
    private StringFilter addressLine1;
    private StringFilter addressLine2;
    private StringFilter city;
    private StringFilter pin;
    private StringFilter state;
    private StringFilter country;
    private StringFilter email;
    private StringFilter mobileNo;
    private StringFilter gstNo;
    private StringFilter leadSource;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(StringFilter addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public StringFilter getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(StringFilter addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getPin() {
        return pin;
    }

    public void setPin(StringFilter pin) {
        this.pin = pin;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getGstNo() {
        return gstNo;
    }

    public void setGstNo(StringFilter gstNo) {
        this.gstNo = gstNo;
    }

    public StringFilter getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(StringFilter leadSource) {
        this.leadSource = leadSource;
    }

    public CustomerDetailsCriteria(CustomerDetailsCriteria customerDetailsCriteria) {
        this.id = customerDetailsCriteria.id == null ? null : customerDetailsCriteria.id.copy();
        this.firstName = customerDetailsCriteria.firstName == null ? null : customerDetailsCriteria.firstName.copy();
        this.lastName = customerDetailsCriteria.lastName == null ? null : customerDetailsCriteria.lastName.copy();
        this.companyName = customerDetailsCriteria.companyName == null ? null : customerDetailsCriteria.companyName.copy();
        this.address = customerDetailsCriteria.address == null ? null : customerDetailsCriteria.address.copy();
        this.addressLine1 = customerDetailsCriteria.addressLine1 == null ? null : customerDetailsCriteria.addressLine1.copy();
        this.addressLine2 = customerDetailsCriteria.addressLine2 == null ? null : customerDetailsCriteria.addressLine2.copy();
        this.city = customerDetailsCriteria.city == null ? null : customerDetailsCriteria.city.copy();
        this.pin = customerDetailsCriteria.pin == null ? null : customerDetailsCriteria.pin.copy();
        this.state = customerDetailsCriteria.state == null ? null : customerDetailsCriteria.state.copy();
        this.country = customerDetailsCriteria.country == null ? null : customerDetailsCriteria.country.copy();
        this.email = customerDetailsCriteria.email == null ? null : customerDetailsCriteria.email.copy();
        this.mobileNo = customerDetailsCriteria.mobileNo == null ? null : customerDetailsCriteria.mobileNo.copy();
        this.gstNo = customerDetailsCriteria.gstNo == null ? null : customerDetailsCriteria.gstNo.copy();
        this.leadSource = customerDetailsCriteria.leadSource == null ? null : customerDetailsCriteria.leadSource.copy();
    }

    public CustomerDetailsCriteria() {}

    @Override
    public Criteria copy() {
        return new CustomerDetailsCriteria(this);
    }
}
