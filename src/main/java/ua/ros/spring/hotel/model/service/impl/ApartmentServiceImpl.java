package ua.ros.spring.hotel.model.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.repository.ApartmentRepository;
import ua.ros.spring.hotel.model.service.ApartmentService;

import java.sql.Connection;
import java.util.ArrayList;

@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    @PersistenceContext
    private EntityManager entityManager;
    private final ApartmentRepository apartmentRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public Boolean createApartment(Apartment apartment) {
        if( apartment.getId() == null ) {
            entityManager.persist(apartment);
            return true;
        }
        return false;
    }

    @Transactional(readOnly=true)
    @Override
    public Apartment findApartmentByField(String field, Object value) {
       return apartmentRepository.findByField(field, value).orElseThrow();
    }

    @Transactional(readOnly=true)
    @Override
    public Page<Apartment> findApartments(Pageable pageable) {
        return apartmentRepository.findAll(pageable);
    }

    @Transactional(readOnly=true)
    @Override
    public ArrayList<Apartment> searchApartment(String value, int start, int total) {
        /*Connection connection = dbManager.getConnection();
        @SuppressWarnings("unchecked")
        ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> {
                    ArrayList<Apartment> r = apartmentDAO.searchApartments(connection, value, start-1, total);
                    rowsNumber = apartmentDAO.countRowsInLastQuery(connection);
                    return r;
                }
        );
        return result;*/
     throw new UnsupportedOperationException();
    }

    @Transactional(readOnly=true)
    @Override
    public ArrayList<Apartment> findFewApartmentsAndSort(String secondQueryPart, String... fields) {
        /*Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> {
                ArrayList<Apartment> r = apartmentDAO.getWithDynamicQuery(connection, secondQueryPart, fields);
                rowsNumber = apartmentDAO.countRowsInLastQuery(connection);
                return r;
                }
        );
        return result;*/
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean updateApartment(Apartment apartment) {
        if(apartment.getId() != null ) {
            entityManager.merge(apartment);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteApartment(Apartment apartment) {
        apartmentRepository.delete(apartment);
        return true;
    }
}
