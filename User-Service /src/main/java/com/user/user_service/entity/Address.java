package com.user.user_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "Address")
public class Address {
    private int id;
    private String street;
    private String city;
    private String state;
    private String country;
    private long zipcode;

}
