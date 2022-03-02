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

    @ManyToOne(fetch = FetchType.LAZY)
    private StationEntity fromStation;

    @ManyToOne(fetch = FetchType.LAZY)
    private StationEntity toStation;

    @Column(nullable = false,unique = true)
    private String trainNumber;

    @Column(nullable = false)
    private Integer maxWagons;

    @Column(nullable = false)
    private Integer countOfWagons;

    private boolean trainStatus;

    @ManyToMany
    private List<StationEntity> includeStations;

    @OneToMany(mappedBy = "trainEntity")
    private List<WagonEntity> wagonEntities;

}
