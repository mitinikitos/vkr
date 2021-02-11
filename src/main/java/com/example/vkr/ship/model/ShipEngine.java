package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ship_engine")
@Data
@NoArgsConstructor
@JsonView(View.UI.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ShipEngine {

    @Id
    @Column(name = "reg_num")
    private int regNum;
    @JsonView(View.REST.class)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reg_num", referencedColumnName = "reg_num")
    private Ship ship;

//    @JsonView(View.REST.class)
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eng_1", referencedColumnName = "id", updatable = false, columnDefinition = "uuid")
    private Engine engine1;

//    @JsonView(View.REST.class)
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eng_2", referencedColumnName = "id", updatable = false, columnDefinition = "uuid")
    private Engine engine2;

//    @JsonView(View.REST.class)
    @Nullable
    @ManyToOne(targetEntity = Engine.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eng_3", referencedColumnName = "id", updatable   = false, columnDefinition = "uuid")
    private Engine engine3;

    @Column(name = "sum_pwr")
    private int sumPwr;

    public ShipEngine(Ship ship, @Nullable Engine engine1, @Nullable Engine engine2, @Nullable Engine engine3) {
        this.regNum = ship.getId();
        this.ship = ship;
        this.engine1 = engine1;
        this.engine2 = engine2;
        this.engine3 = engine3;
        sumPwr();
    }


    public ShipEngine(Ship ship) {
        this(ship, null, null, null);
    }

    private void sumPwr() {
        this.sumPwr = engine1 == null ? 0 : engine1.getPwr() * engine1.getCount();
        this.sumPwr += engine2 == null ? 0 : engine2.getPwr() * engine2.getCount();
        this.sumPwr += engine3 == null ? 0 : engine3.getPwr() * engine3.getCount();
    }

    private void setEngine1(Engine engine1) {
        this.engine1 = engine1;
        sumPwr();
    }
    private void setEngine2(Engine engine2) {
        this.engine2 = engine2;
        sumPwr();
    }
    private void setEngine3(Engine engine3) {
        this.engine3 = engine3;
        sumPwr();
    }

    @Override
    public String toString() {
        return "ShipEngine [ " + regNum + ", " + sumPwr + " ]";
    }
}
