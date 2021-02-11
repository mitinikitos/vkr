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
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reg_num", referencedColumnName = "reg_num")
    private Ship ship;

    @Nullable
    @Column(name = "disp")
    private int disp;
    @Nullable
    @Column(name = "length", columnDefinition = "double precision")
    private double length;
    @Nullable
    @Column(name = "breadth", columnDefinition = "double precision")
    private double breadth;
    @Nullable
    @Column(name = "draught", columnDefinition = "double precision")
    private double draught;
    @Nullable
    @Column(name = "depth", columnDefinition = "double precision")
    private double depth;
    @Nullable
    @Column(name = "class", columnDefinition = "text")
    private String shipClass;

    public ShipDimensions(@Nullable int disp, @Nullable double length, @Nullable double breadth,
                          @Nullable double depth, @Nullable double draught, @Nullable String shipClass) {
        this.disp = disp;
        this.length = length;
        this.breadth = breadth;
        this.depth = depth;
        this.draught = draught;
        this.shipClass = shipClass;
    }

    public ShipDimensions(Ship ship) {
        this.regNum = ship.getId();
        this.ship = ship;
    }
}
