package com.mycompany.clinic.services;

import com.mycompany.clinic.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @Test
    public void deleteWithFail() {
        try {
            Patient p = patientService.get(11L);
            patientService.delete(p);
            patientService.delete(p);
        } catch (Exception ex) {
            System.out.println(ex.getClass());
        }
    }
}
