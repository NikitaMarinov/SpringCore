package com.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.core.domain.enums.TypeOfOreEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "miner_group")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MinerGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "productivity")
    private int productivity;

    @Enumerated(EnumType.STRING)
    @Column(name = "mine_ore")
    private TypeOfOreEnum mineOre;

    @OneToMany(mappedBy = "minerGroup",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"minerGroup"})
    private List<MinerEntity> miners;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties({"typeOfOre", "quantity", "price", "orderStatus","miners","minerGroup"})
    private OrderEntity order;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "stock_id")
    @JsonIgnoreProperties({"coal", "iron", "gold","minerGroup"})
    private StockEntity stock;


    public static void toStringList(List<MinerGroupEntity> minerGroupEntities) {
        for(MinerGroupEntity minerGroupEntity :minerGroupEntities){
            System.out.println("Miner Group Entity{" +
                    "  id='" + minerGroupEntity.id + '\'' +
                    ", name='" + minerGroupEntity.name + '\'' +
                    ", productivity=" + minerGroupEntity.productivity +
                    ", mine ore=" + minerGroupEntity.mineOre +
                    ", stock=" + minerGroupEntity.stock +
                    ", order=" + minerGroupEntity.order +
                    '}' + "Miners : " );
//                    minerGroupEntity.miners);
        }
    }
}
