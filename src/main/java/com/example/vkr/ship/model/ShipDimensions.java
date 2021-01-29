package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ship_dimensions")
@Data
@NoArgsConstructor
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
    @Column(name = "class")
    private String shipClass;

    public ShipDimensions(@Nullable int disp, @Nullable double length, @Nullable double breadth,
                          @Nullable double draught, @Nullable String shipClass) {
        this.disp = disp;
        this.length = length;
        this.breadth = breadth;
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

    public void setRegNum(Ship ship) {
        this.regNum = ship.getId();
        this.ship = ship;
    }
}
