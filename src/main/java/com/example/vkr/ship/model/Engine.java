package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "engine")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonView(View.UI.class)
public class Engine {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView(View.REST.class)
    @Column(name = "id")
    private UUID id;
    @Column(name = "eng_count")
    private int count;
    @Column(name = "eng_pwr")
    private int pwr;
    @Column(name = "dvig")
    private String dvig;

    /**
     * Creates {@link Engine}
     * @param count must not be {@literal null}. Quantity engine
     * @param pwr must not be {@literal null}. Engine power
     * @param dvig must not be {@literal null}. Engine name
     */
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
