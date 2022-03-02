package uz.train.appbookingflights.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.train.appbookingflights.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class WagonEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainEntity trainEntity;

    @Column(nullable = false)
    private Integer wagonNumber;

    @Column(nullable = false)
    private Integer maxSeats;
    private Integer countOfSeats;

    @Column(nullable = false)
    private boolean wagonStatus;

    @OneToMany(mappedBy = "wagonEntity")
    private List<SeatEntity> seatEntities;

}
