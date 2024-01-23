package com.core.domain;

import com.core.domain.enums.OrderStatusEnum;
import com.core.domain.enums.TypeOfOreEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_ore")
    private TypeOfOreEnum typeOfOre;

    @Column(name = "quantity")

    private int quantity;

    @Column(name = "price")
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatusEnum orderStatus;

    @OneToOne(mappedBy = "order",cascade = CascadeType.PERSIST)
    @Transient
    private MinerGroupEntity minerGroup;

    public static void toStringList(List<OrderEntity> orderEntitiesList) {
        for(OrderEntity orderEntity : orderEntitiesList){
            System.out.println("OrderEntity{" +
                    "  id='" + orderEntity.id + '\'' +
                    ", type of ore='" + orderEntity.typeOfOre + '\'' +
                    ", price=" + orderEntity.price +
                    ", quantity=" + orderEntity.quantity +
                    ", status=" + orderEntity.orderStatus +
                    ", miner group=" + orderEntity.minerGroup +
                    '}');
        }
    }
}