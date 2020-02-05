/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.clinic.services;

import com.mycompany.clinic.entity.Patient;
import com.mycompany.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class PatientService implements EntityService<Patient>{
    
    @Autowired
    private PatientRepository patientRepository;
    
    
    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
    
    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }
    
    @Override
    public Patient update(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void delete(Patient e) {
        patientRepository.delete(e);
    }

    @Override
    public Patient get(Long id) {
        return patientRepository.findById(id).get();
    }
    
}
