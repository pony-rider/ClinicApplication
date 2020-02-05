/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.clinic.repository;

import com.mycompany.clinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 */
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    @Query("Select COUNT(*) from Recipe r where r.doctor.id=:doctorId")
    long getRecipesCount(@Param("doctorId") Long doctorId);

}
