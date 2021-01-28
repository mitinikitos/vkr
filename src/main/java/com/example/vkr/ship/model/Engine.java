package com.example.vkr.ship.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "engine")
@Data
@NoArgsConstructor
public class Engine {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;
    @Column(name = "eng_count")
    private int count;
    @Column(name = "eng_pwr")
    private int pwr;
    @Column(name = "dvig")
    private String dvig;

    public Engine(int count, int pwr, String dvig) {
        this.count = count;
        this.pwr = pwr;
        this.dvig = dvig;
    }

    @Override
    public String toString() {
        return "Engine [ " + id +", " + count + ", " + pwr + ", " + dvig + " ]";
    }
}
