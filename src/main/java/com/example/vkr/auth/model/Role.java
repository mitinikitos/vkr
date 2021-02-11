package com.example.vkr.auth.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
@JsonView(View.UI.class)
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", columnDefinition = "text")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
