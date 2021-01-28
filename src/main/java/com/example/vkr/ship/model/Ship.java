package com.example.vkr.ship.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ship")
@JsonView(View.UI.class)
@Data
@NoArgsConstructor
public class Ship {

    @Id
    @Column(name = "reg_num")
    private int id;
    @Column(name = "name")
    private String name;
    @Nullable
    @Column(name = "type")
    private String type;
    @Nullable
    @Column(name = "sub_type", nullable = true)
    private String subType;
    @Nullable
    @Column(name = "imo")
    private int imo;
    @Nullable
    @Column(name = "call_sign", nullable = true)
    private String callSign;
    @Nullable
    @Column(name = "project", nullable = true)
    private String project;
    @Nullable
    @Column(name = "god_p")
    private int godP;

    @Nullable
    @Column(name = "own_name")
    private String ownName;
    @Nullable
    @ManyToOne(targetEntity = OwnOperator.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "own_name", referencedColumnName = "name", insertable = false, updatable = false)
    @JsonView(View.REST.class)
    private OwnOperator own;

    @Nullable
    @Column(name = "operator_name")
    private String operatorName;
    @Nullable
    @ManyToOne(targetEntity = OwnOperator.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_name", referencedColumnName = "name", insertable = false, updatable = false)
    @JsonView(View.REST.class)
    private OwnOperator operator;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ship", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.REST.class)
    private ShipEngine shipEngine;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ship", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.REST.class)
    private ShipCapacity shipCapacity;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ship", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.REST.class)
    private ShipDimensions shipDimensions;

    public Ship(int id, String name, @Nullable String type, @Nullable String subType,
                @Nullable Integer imo, @Nullable String callSign, @Nullable String project, @Nullable Integer godP) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.subType = subType;
        this.imo = imo;
        this.callSign = callSign;
        this.project = project;
        this.godP = godP;
    }

    public void setOwn(OwnOperator own) {
        this.own = own;
        this.ownName = own == null ? null : own.getName();
    }

    public void setOwnName(OwnOperator own) {
        this.ownName = own == null ? null : own.getName();
    }

    public void setOperatorName(OwnOperator operator) {
        this.operatorName = operator == null ? null : operator.getName();
    }

    public void setOperator(OwnOperator operator) {
        this.operator = operator;
        this.operatorName = operator == null ? null : operator.getName();
    }

    //Тестовый конструктор
    public Ship(int id, String name, int imo, int godP) {
        this(id, name, null, null, imo, null, null, godP);
    }
    @Override
    public String toString() {
        return "Ship [ " + id + ", " + name + ", " + type + ", " + subType + ", " +
                imo + ", " + callSign + ", " + project + ", " + godP + " ]";
    }
}
