package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;

@SuppressWarnings("JavaDoc")
@Entity
@Table(name = "ship")
@JsonView(View.UI.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ship {

    @Transient
    private transient PersistentAttributeInterceptor $$_hibernate_attributeInterceptor;

    @Id
    @Column(name = "reg_num")
    private Integer id;
    @Column(name = "name", columnDefinition = "text")
    private String name;
    @Nullable
    @Column(name = "type", columnDefinition = "text")
    private String type;
    @Nullable
    @Column(name = "sub_type", columnDefinition = "text")
    private String subType;
    @Nullable
    @Column(name = "imo")
    private Integer imo;
    @Nullable
    @Column(name = "call_sign", columnDefinition = "text")
    private String callSign;
    @Nullable
    @Column(name = "project", columnDefinition = "text")
    private String project;
    @Nullable
    @Column(name = "port", columnDefinition = "text")
    private String port;
    @Nullable
    @Column(name = "speed")
    private Integer speed;
    @Nullable
    @Column(name = "god_p")
    private Integer godP;

    @Nullable
    @Column(name = "own_name", columnDefinition = "text")
    private String ownName;
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "own_name", referencedColumnName = "name", insertable = false, updatable = false)
    @JsonView(View.REST.class)
    private OwnOperator own;

    @Nullable
    @Column(name = "operator_name", columnDefinition = "text")
    private String operatorName;
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operator_name", referencedColumnName = "name", insertable = false, updatable = false)
    @JsonView(View.REST.class)
    private OwnOperator operator;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ship", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JsonView(View.REST.class)
    private ShipEngine shipEngine;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ship", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JsonView(View.REST.class)
    private ShipCapacity shipCapacity;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ship", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JsonView(View.REST.class)
    private ShipDimensions shipDimensions;

    public Ship(int id, String name, @Nullable String type,
                @Nullable String subType, @Nullable Integer imo, @Nullable String callSign,
                @Nullable String project, @Nullable String port, @Nullable Integer godP,
                @Nullable Integer speed) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.subType = subType;
        this.imo = imo;
        this.callSign = callSign;
        this.project = project;
        this.port = port;
        this.godP = godP;
        this.speed = speed;
        this.shipCapacity = new ShipCapacity(this);
        this.shipEngine = new ShipEngine(this);
        this.shipDimensions = new ShipDimensions(this);
    }

    public void setOwn(@Nullable OwnOperator own) {
        this.own = own;
        this.ownName = own == null ? null : own.getName();
    }

    public void setOperator(@Nullable OwnOperator operator) {
        this.operator = operator;
        this.operatorName = operator == null ? null : operator.getName();
    }

    public Ship(int id, String name,
                @Nullable int imo, @Nullable int godP) {
        this(id, name, null, null, imo, null, null, null, godP, null);
    }

    @Override
    public String toString() {
        return "Ship [ " + id + ", " + name + ", " + type + ", " + subType + ", " +
                imo + ", " + callSign + ", " + project + ", " + godP + " ]";
    }

    public ShipEngine getShipEngine() {
        if ($$_hibernate_attributeInterceptor != null) {
            return (ShipEngine) $$_hibernate_attributeInterceptor.readObject(this, "shipEngine", this.shipEngine);
        }
        return this.shipEngine;
    }
    public void setShipEngine(ShipEngine shipEngine) {
        if ($$_hibernate_attributeInterceptor != null) {
            this.shipEngine = (ShipEngine) $$_hibernate_attributeInterceptor.writeObject(this, "shipEngine", this.shipEngine, shipEngine);
            return;
        }
        this.shipEngine = shipEngine;
    }
    public ShipCapacity getShipCapacity() {
        if ($$_hibernate_attributeInterceptor != null) {
            return (ShipCapacity) $$_hibernate_attributeInterceptor.readObject(this, "shipCapacity", this.shipCapacity);
        }
        return this.shipCapacity;
    }
    public void setShipCapacity(ShipCapacity shipCapacity) {
        if ($$_hibernate_attributeInterceptor != null) {
            this.shipCapacity = (ShipCapacity) $$_hibernate_attributeInterceptor
                    .writeObject(this,
                            "shipCapacity",
                            this.shipCapacity,
                            shipCapacity);
            return;
        }
        this.shipCapacity = shipCapacity;
    }
    public ShipDimensions getShipDimensions() {
        if ($$_hibernate_attributeInterceptor != null) {
            return (ShipDimensions) $$_hibernate_attributeInterceptor.readObject(this, "shipDimensions", this.shipDimensions);
        }
        return this.shipDimensions;
    }
    public void setShipDimensions(ShipDimensions shipDimensions) {
        if ($$_hibernate_attributeInterceptor != null) {
            this.shipDimensions = (ShipDimensions) $$_hibernate_attributeInterceptor
                    .writeObject(this,
                            "shipDimensions",
                            this.shipDimensions,
                            shipDimensions);
            return;
        }
        this.shipDimensions = shipDimensions;
    }
}
