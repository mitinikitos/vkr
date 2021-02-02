package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonView(View.UI.class)
@Data
@NoArgsConstructor
public class ShipDto {

    private int id;
    private String name;
    private String type;
    private String subType;
    private int imo;
    private String callSign;
    private String project;
    private int godP;
    @Nullable
    private String ownName;
    @Nullable
    private String operatorName;
    private ShipCapacity shipCapacity;
    private ShipDimensions shipDimensions;
    private ShipEngine shipEngine;

    /**
     *
     */
    public ShipDto(Ship ship) {
        this.id = ship.getId();
        this.name = ship.getName();
        this.type = ship.getType();
        this.subType = ship.getSubType();
        this.imo = ship.getImo();
        this.callSign = ship.getCallSign();
        this.project = ship.getProject();
        this.godP = ship.getGodP();
        this.ownName = ship.getOwnName();
        this.operatorName = ship.getOperatorName();
    }
}
