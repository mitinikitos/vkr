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
    private Map<String, OwnOperator> ownOperators;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (ship != null) res.append(ship.toString()).append("\n");

        return res.toString();
    }
}
