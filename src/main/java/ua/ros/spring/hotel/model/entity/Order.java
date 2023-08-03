package ua.ros.spring.hotel.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1444499296234806694L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guests_number")
    private Integer guestsNumber;
    @Column(name = "rooms_number")
    private String roomsNumber;
    @Column(name = "apartment_class")
    private String apartmentClass;
    @Column(name = "price")
    private Long price;
    @Column(name = "description")
    private String description;

    @Column(name = "check_in_date")
    private Date checkInDate;
    @Column(name = "check_out_date")
    private Date checkOutDate;

    //Foreign keys
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "response_to_order_id")
    private ResponseToOrder responseToOrder = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
