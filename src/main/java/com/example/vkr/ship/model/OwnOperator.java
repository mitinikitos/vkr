package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "own_operator")
@Data
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
@JsonView(View.UI.class)
@NoArgsConstructor
public class OwnOperator {

    @Id
    @Column(name = "name", columnDefinition="text")
    private String name;

    @Nullable
    @Column(name = "address", columnDefinition = "text")
    private String address;
    @Nullable
    @Column(name = "phones", columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] phones;
    @Nullable
    @Column(name = "email", columnDefinition = "text")
    private String email;
    @Nullable
    @Column(name = "fax", columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] fax;

    @JsonView(View.REST.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "own", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ship> shipsOwn;
    @JsonView(View.REST.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ship> shipsOperator;

    public OwnOperator(String name, @Nullable String address, @Nullable String[] phones,
                       @Nullable String email, @Nullable String[] fax) {
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.email = email;
        this.fax = fax;
    }

    public String getPhones() {
        StringBuilder res = new StringBuilder();
        String prefix = "";
        for (String phone : phones) {
            res.append(prefix);
            prefix = ", ";
            res.append(phone);
        }
        return res.toString();
    }
    public String getFax() {
        StringBuilder res = new StringBuilder();
        String prefix = "";
        for (String fax : fax) {
            res.append(prefix);
            prefix = ", ";
            res.append(fax);
        }
        return res.toString();
    }

    @Override
    public String toString() {
        return "OwnOperator [ " + name + ", " + address + ", " + getPhones() + ", " +
                email + ", " + getFax() + " ]";
    }
}