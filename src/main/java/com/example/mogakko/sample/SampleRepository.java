package com.example.mogakko.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SampleRepository {

    private final EntityManager em;


    public void save(Sample sample) {
        em.persist(sample);
    }

    public Sample findOne(Long id) {
        return em.find(Sample.class, id);
    }

    public List<Sample> findAll() {
        return em.createQuery("select s from Sample s", Sample.class)
                .getResultList();
    }

    public List<Sample> findByName(String name) {
        return em.createQuery("select s from Sample s where s.name = :name", Sample.class)
                .setParameter("name", name)
                .getResultList();
    }
}
