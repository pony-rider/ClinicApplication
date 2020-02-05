/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.clinic.repository;

import com.mycompany.clinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface PatientRepository extends JpaRepository<Patient, Long>{

}
