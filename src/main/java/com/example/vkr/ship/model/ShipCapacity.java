package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ship_capacity")
@Data
@NoArgsConstructor
public class ShipCapacity {

    @Id
    @Column(name = "reg_num")
    private int regNum;
    @JsonView(View.REST.class)
    @OneToOne(targetEntity = Ship.class)
    @PrimaryKeyJoinColumn(name = "reg_num", referencedColumnName = "reg_num")
    private Ship ship;

    @Nullable
    @Column(name = "dedv")
    private int dedv;
    @Nullable
    @Column(name = "pass_k")
    private int passK;
    @Nullable
    @Column(name = "pass_p")
    private int passP;
    @Nullable
    @Column(name = "gt")
    private int gt;
    @Nullable
    @Column(name = "nt")
    private int nt;

    public ShipCapacity(@Nullable int dedv, @Nullable int passK, @Nullable int passP,
                        @Nullable int gt, @Nullable int nt) {
        this.dedv = dedv;
        this.passK = passK;
        this.passP = passP;
        this.gt = gt;
        this.nt = nt;
    }

    /**
     * Creates default {@link ShipCapacity} for the given {@link Ship}
     * @param ship must not be {@literal null}
     */
    public ShipCapacity(Ship ship) {
        this.regNum = ship.getId();
        this.ship = ship;
    }

    public void setRegNum(Ship ship) {
        this.regNum = ship.getId();
        this.ship = ship;
    }
}
