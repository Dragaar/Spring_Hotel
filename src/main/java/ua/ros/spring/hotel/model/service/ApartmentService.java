package ua.ros.spring.hotel.model.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.ros.spring.hotel.model.entity.Apartment;

import java.util.Optional;
import com.querydsl.core.types.Predicate;

/**
 * Apartment Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface ApartmentService {

    /**
     * Create new apartment
     * @param apartment the apartment to be added
     * @return operation result
     */
    Boolean                  createApartment(Apartment apartment);

    /**
     * Find apartment by predicate
     * @param p conditions to receive apartment
     * @return found apartment
     */
    Optional<Apartment>      findOne(Predicate p);

    /**
     * Find apartments by predicate
     * @param p conditions to receive apartments
     * @param pageable conditions to perform paginating
     * @return page with apartments
     */
    Page<Apartment>          findAll(Predicate p, Pageable pageable);
    /**
     * Find few apartments using direct search algorithm by relevance.
     * <br> Include tag functionality to regulate relevancy and result set
     * @param pageable conditions to perform paginating
     * @param searchValue user value
     * @return page with apartments
     */
    Page<Apartment>         searchApartments(Pageable pageable, String searchValue);

    /**
     * Update apartment
     * @param apartment apartment to update
     * @return operation result
     */
    Boolean                  updateApartment(Apartment apartment);
    /**
     * Delete apartment
     * @param apartment apartment to delete
     * @return operation result
     */
    Boolean                  deleteApartment(Apartment apartment);

}
