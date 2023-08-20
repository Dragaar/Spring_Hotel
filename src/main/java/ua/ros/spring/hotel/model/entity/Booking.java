package ua.ros.spring.hotel.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenerationTime;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "booking")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString(exclude = {"account", "apartment"})
public class Booking implements Serializable {

    @Serial
    private static final long serialVersionUID = 385954903468257504L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guests_number")
    private String guestsNumber;

    @Column(name = "check_in_date")
    private Date checkInDate;
    @Column(name = "check_out_date")
    private Date checkOutDate;

    //Auto-Generated
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "reservation_data")
    private Timestamp reservationData;

    @Column(name = "is_paid_for_reservation")
    private Boolean isPaidForReservation = false;

    //Foreign keys
    @ManyToOne
    @JoinColumn(name = "account_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_booking_account1"))
    private Account account;

    @ManyToOne
    @JoinColumn(name = "apartment_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_booking_apartment"))
    private Apartment apartment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        return id.equals(booking.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
