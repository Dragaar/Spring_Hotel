package ua.ros.spring.hotel.model.entity;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "apartment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class Apartment implements Serializable {

    @Serial
    private static final long serialVersionUID = -2943941589166694458L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "imageURL")
    private String imageURL;
    @Column(name = "address")
    private String address;
    @Column(name = "max_guests_number")
    private String maxGuestsNumber;
    @Column(name = "rooms_number")
    private String roomsNumber;
    @Column(name = "apartment_class")
    private String apartmentClass;
    @Column(name = "price")
    private Long price;
    //indicate state of apartment (false - not available/ true - available)
    @Column(name = "state")
    private Boolean state = true;

    //Foreign keys
    @ToString.Exclude
    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "apartments")
    private Set<ResponseToOrder> responseToOrders = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Apartment apartment = (Apartment) o;

        return id.equals(apartment.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
