package com.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "stock")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "coal")
    private int coal;

    @Column(name = "iron")
    private int iron;

    @Column(name = "gold")
    private int gold;

    @Transient
    @OneToOne(mappedBy = "stockId",cascade = CascadeType.PERSIST)
    private MinerGroupEntity minerGroup;

    public static void toStringList(List<StockEntity> stockEntitiesList) {
        for(StockEntity stockEntity :stockEntitiesList){
            System.out.println("OrderEntity{" +
                    "  id='" + stockEntity.id + '\'' +
                    ", coal='" + stockEntity.coal + '\'' +
                    ", iron=" + stockEntity.iron +
                    ", gold=" + stockEntity.gold +
                    '}');
        }
    }

}