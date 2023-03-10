package com.openclassrooms.safetynet.DTO;

import java.util.Objects;

public class PhoneAlertDTO {
    private String phone;

    public PhoneAlertDTO(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneAlertDTO that)) return false;
        return phone.equals(that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}
