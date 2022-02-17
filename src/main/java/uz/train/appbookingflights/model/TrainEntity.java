package uz.train.appbookingflights.model;

import lombok.*;
import uz.train.appbookingflights.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class TrainEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private CityEntity fromCity;

    @ManyToOne
    private CityEntity toCity;

    @Column(nullable = false,unique = true)
    private String trainNumber;

    @Column(nullable = false)
    private boolean status;

    @ManyToMany
    private List<StationEntity> includeStation;

}
