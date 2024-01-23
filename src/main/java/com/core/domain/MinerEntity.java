package com.core.domain;

import com.core.domain.enums.MinerStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "miner")
@Data
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
public class MinerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "salary")
    private int salary;

    @Column(name = "at_work")
    private boolean atWork;

    @Enumerated(EnumType.STRING)
    private MinerStatusEnum status;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "miner_group_id")
    @JsonIgnore
    private MinerGroupEntity minerGroup;

    @Override
    public String toString() {

        return "MinerEntity{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", surname='" + this.surname + '\'' +
                ", salary=" + this.salary +
                ", atWork=" + this.atWork +
                ", status=" + this.status +
                '}';
    }


    public static void toStringList(List<MinerEntity> minerEntityList) {
        for (MinerEntity minerEntity : minerEntityList) {
            System.out.println("MinerEntity{" +
                    "id=" + minerEntity.id +
                    ", name='" + minerEntity.name + '\'' +
                    ", surname='" + minerEntity.surname + '\'' +
                    ", salary=" + minerEntity.salary +
                    ", atWork=" + minerEntity.atWork +
                    ", status=" + minerEntity.status +
                    '}');
        }
    }

}