/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinic.services;

import com.mycompany.clinic.entity.Doctor;
import com.mycompany.clinic.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class DoctorService implements EntityService<Doctor> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void delete(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Override
    public Doctor get(Long id) {
        return doctorRepository.findById(id).get();
    }

    public long getRecipesCount(Doctor d) {
        if (d.getId() == null) {
            throw new IllegalArgumentException("doctor is not yet persisted");
        }
        return doctorRepository.getRecipesCount(d.getId());
    }
}
