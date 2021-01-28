package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


//TODO
@Entity
@Table(name = "ship_engine")
@Data
@NoArgsConstructor
public class ShipEngine {

    @Id
    @Column(name = "reg_num")
    private int regNum;
    @JsonView(View.REST.class)
    @OneToOne(targetEntity = Ship.class, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "reg_num", referencedColumnName = "reg_num")
    private Ship ship;

//    @Nullable
//    @Column(name = "eng_1")
//    private UUID eng1;
    @JsonView(View.REST.class)
    @Nullable
//    @OneToOne(targetEntity = Engine.class, fetch = FetchType.LAZY)
    @ManyToOne(targetEntity = Engine.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eng_1", referencedColumnName = "id", updatable = false)
    private Engine engine1;

//    @Nullable
//    @Column(name = "eng_2")
//    private UUID eng2;
    @JsonView(View.REST.class)
    @Nullable
//    @OneToOne(targetEntity = Engine.class, fetch = FetchType.LAZY)
    @ManyToOne(targetEntity = Engine.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eng_2", referencedColumnName = "id", updatable = false)
    private Engine engine2;

//    @Nullable
//    @Column(name = "eng_3")
//    private UUID eng3;
    @JsonView(View.REST.class)
    @Nullable
//    @OneToOne(targetEntity = Engine.class, fetch = FetchType.LAZY)
    @ManyToOne(targetEntity = Engine.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eng_3", referencedColumnName = "id", updatable   = false)
    private Engine engine3;

    @Column(name = "sum_pwr")
    private int sumPwr;

    public ShipEngine(Ship ship, @Nullable Engine engine1, @Nullable Engine engine2, @Nullable Engine engine3) {
        this.regNum = ship.getId();
        this.ship = ship;
        this.engine1 = engine1;
        this.engine2 = engine2;
        this.engine3 = engine3;
//        this.eng1 = engine1 == null ? null : engine1.getId();
//        this.eng2 = engine2 == null ? null : engine2.getId();
//        this.eng3 = engine3 == null ? null : engine3.getId();
        sumPwr(engine1, engine2, engine3);
    }

    private void sumPwr(Engine engine1, Engine engine2, Engine engine3) {
        sumPwr += engine1 == null ? 0 : engine1.getPwr() * engine1.getCount();
        sumPwr += engine2 == null ? 0 : engine2.getPwr() * engine2.getCount();
        sumPwr += engine3 == null ? 0 : engine3.getPwr() * engine3.getCount();
    }

    public void setRegNum(Ship ship) {
        this.regNum = ship.getId();
        this.ship = ship;
    }

    @Override
    public String toString() {
        return "ShipEngine [ " + regNum + ", " + sumPwr + " ]";
    }
}
