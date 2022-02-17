package uz.train.appbookingflights.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.train.appbookingflights.model.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class WagonEntity extends BaseEntity {

    @ManyToOne
    private TrainEntity trainEntity;

    @Column(nullable = false)
    private Integer wagonNumber;

}
