package com.example.vkr.excel.model;

import com.example.vkr.ship.model.*;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParseModel {
    private Ship ship;
    private List<Engine> engines;
    private ShipCapacity shipCapacity;
    private ShipDimensions shipDimensions;
    private Map<String, OwnOperator> ownOperators;


//    private String error;

//    public ParseModel(String error) {
//        this.error = error;
//    }

//    public void addError(String error) {
//        this.error = this.error == null ? error : this.error + "\r\n" + error;
//    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (ship != null) res.append(ship.toString()).append("\n");
//        if (shipCapacity != null) res.append(shipCapacity.toString()).append("\n");
//        if (shipDimensions != null) res.append(shipDimensions.toString()).append("\n");

        return res.toString();
    }
}
