package uz.train.appbookingflights.model;

import lombok.*;
import uz.train.appbookingflights.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class BuyTicketEntity extends BaseEntity {

    @ManyToOne
    private CityEntity fromCity;

    @ManyToOne
    private CityEntity toCity;

    @Column(nullable = false)
    private LocalDate flightDate;

    @OneToOne
    private PassengerEntity passenger;

    @ManyToMany
    private List<PaymentEntity> paymentList;

    @ManyToOne
    private DepartureEntity departureEntity;

}
