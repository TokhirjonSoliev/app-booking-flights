package uz.train.appbookingflights.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.train.appbookingflights.model.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SeatEntity extends BaseEntity {

    @ManyToOne
    private WagonEntity wagonEntity;

    @Column(nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private Double seatPrice;

    @Column(nullable = false)
    private boolean status;

}
