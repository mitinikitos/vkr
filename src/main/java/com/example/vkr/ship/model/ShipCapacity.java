package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ship_capacity")
@Data
@JsonView(View.UI.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@NoArgsConstructor
@AllArgsConstructor
public class ShipCapacity {

    @Transient
    private transient PersistentAttributeInterceptor $$_hibernate_attributeInterceptor;

    @Id
    @Column(name = "reg_num")
    private int regNum;

    @JsonView(View.REST.class)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reg_num", referencedColumnName = "reg_num")
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

    /**
     *
     */
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

    public Ship getShip() {
        if ($$_hibernate_attributeInterceptor != null) {
            return (Ship) $$_hibernate_attributeInterceptor.readObject(this, "ship", this.ship);
        }
        return this.ship;
    }

    public void setShip(Ship ship) {
        if ($$_hibernate_attributeInterceptor != null) {
            this.ship = (Ship) $$_hibernate_attributeInterceptor.writeObject(this, "ship", this.ship, ship);
            return;
        }
        this.ship = ship;
    }

    @Override
    public String toString() {
        return "[" + regNum + ", " + dedv + ", " + passK + ", " + passP + ", " + gt + ", " + nt + " ]";
    }
}
