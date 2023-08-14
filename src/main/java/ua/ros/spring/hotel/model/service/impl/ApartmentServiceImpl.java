package ua.ros.spring.hotel.model.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.repository.ApartmentRepository;
import ua.ros.spring.hotel.model.service.ApartmentService;


import java.util.Optional;
import com.querydsl.core.types.Predicate;

@Slf4j
@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    @PersistenceContext
    private EntityManager entityManager;
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ModelMapper modelMapper) {
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean createApartment(Apartment apartment) {
        if( apartment.getId() == null ) {
            log.info("Create new Apartment");
            entityManager.persist(apartment);
            return true;
        }
        return false;
    }

    @Transactional(readOnly=true)
    @Override
    @SuppressWarnings("unchecked")
    public Optional<Apartment> findOne(Predicate predicate) {
        log.info("Find one Apartment");
        Object reflectionObject = (Object) apartmentRepository.findOne(predicate);
        return (Optional<Apartment>) reflectionObject;
    }

    @Transactional(readOnly=true)
    @Override
    @SuppressWarnings("unchecked")
    public Page<Apartment> findAll(Predicate predicate, Pageable pageable) {
        log.info("Find all Apartments");
        Object reflectionObject = apartmentRepository.findAll(predicate, pageable);
        return (Page<Apartment>) reflectionObject;
    }

    @Transactional(readOnly=true)
    @Override
    public Page<Apartment> searchApartments(Pageable pageable, String searchValue) {
        log.info("Search Apartments By Criteria -> " + searchValue);
        return apartmentRepository.searchApartments(pageable, searchValue);
    }

    @Override
    public Boolean updateApartment(Apartment apartment) {
        if(apartment.getId() != null ) {
            log.info("Update Apartment By Id -> " +apartment.getId());
            entityManager.merge(apartment);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteApartment(Apartment apartment) {
        log.info("Delete Apartment By Id -> " +apartment.getId());
        return apartmentRepository.costumDeleteById(apartment.getId()) > 0;
    }
}
