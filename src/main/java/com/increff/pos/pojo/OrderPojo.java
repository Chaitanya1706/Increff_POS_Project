package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "order_pojo")
public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date;

    private Boolean status;

    //todo -> naming strategy

    // todo -> make barcode unique
    // todo -> not null/ empty
    public OrderPojo(){
        this.status = false;
    }


}
