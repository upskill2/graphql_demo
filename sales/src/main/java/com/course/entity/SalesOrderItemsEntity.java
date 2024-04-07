package com.course.entity;

import com.course.sales.generated.types.SimpleModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table (name = "sales_order_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItemsEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private int quantity;
    /*    @ManyToOne
        @JoinColumn (name = "sales_order_uuid", nullable = false)
        private SalesOrderEntity salesOrderEntity;*/
    private UUID modelUuid;
    @Transient
    private SimpleModel simpleModel;

}
