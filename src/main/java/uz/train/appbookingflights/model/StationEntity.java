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
public class StationEntity extends BaseEntity {

    @ManyToOne
    private CityEntity city;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String details;

    @ManyToMany
    private List<TrainEntity> trains;

}
