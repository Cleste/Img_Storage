package com.kirill.pimenov.domain;


import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Entity
@Table(name = "images")
public class Image {

    @Id
    private String name;

    public Image(){
    }
    public Image(String name){
        this.name = name;
    }
}
