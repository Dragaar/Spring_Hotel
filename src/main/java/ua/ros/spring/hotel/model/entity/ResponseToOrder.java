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
@Table(name = "response_to_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class ResponseToOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1327050385146086057L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    //Foreign keys
    @ToString.Exclude
    @OneToMany(mappedBy = "responseToOrder", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "response_to_order_has_apartment",
            joinColumns = @JoinColumn(name = "response_to_order_id"),
            inverseJoinColumns = @JoinColumn(name = "apartment_id"))
    private Set<Apartment> apartments = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseToOrder that = (ResponseToOrder) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
