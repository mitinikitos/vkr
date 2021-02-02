package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ship_dimensions")
@Data
@NoArgsConstructor
@JsonView(View.UI.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ShipDimensions {

    @Id
    @Column(name = "reg_num")
    private int regNum;
    @JsonView(View.REST.class)
    @OneToOne(targetEntity = Ship.class, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "reg_num", referencedColumnName = "reg_num")
    private Ship ship;

    @Nullable
    @Column(name = "disp")
    private int disp;
    @Nullable
    @Column(name = "length")
    private double length;
    @Nullable
    @Column(name = "breadth")
    private double breadth;
    @Nullable
    @Column(name = "draught")
    private double draught;
    @Nullable
    @Column(name = "depth")
    private double depth;
    @Nullable
    @Column(name = "class")
    private String shipClass;

    /**
     * Creates {@link ShipDimensions}.
     * @param disp can be {@literal null}.
     * @param length can be {@literal null}.
     * @param breadth can be {@literal null}.
     * @param depth can be {@literal null}.
     * @param draught can be {@literal null}.
     * @param shipClass can be {@literal null}.
     */
    public ShipDimensions(@Nullable int disp, @Nullable double length, @Nullable double breadth,
                          @Nullable double depth, @Nullable double draught, @Nullable String shipClass) {
        this.disp = disp;
        this.length = length;
        this.breadth = breadth;
        this.depth = depth;
        this.draught = draught;
        this.shipClass = shipClass;
    }

    /**
     * Creates default {@link ShipDimensions} for the given {@link Ship}
     * @param ship must not be {@literal null}
     */
    public ShipDimensions(Ship ship) {
        this.regNum = ship.getId();
        this.ship = ship;
    }
}
