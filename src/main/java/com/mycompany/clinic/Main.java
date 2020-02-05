package com.mycompany.clinic;

import com.mycompany.clinic.entity.Doctor;
import com.mycompany.clinic.entity.Patient;
import com.mycompany.clinic.entity.Priority;
import com.mycompany.clinic.entity.Recipe;
import com.mycompany.clinic.repository.DoctorRepository;
import com.mycompany.clinic.repository.PatientRepository;
import com.mycompany.clinic.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication(scanBasePackages = "com.mycompany.clinic")
public class Main {

    List<String> firstNames = Arrays.asList("Иван", "Федор", "Андрей", "Сергей",
            "Алексей");
    List<String> lastNames = Arrays.asList("Иванов", "Федоров", "Андреев", "Сергеев",
            "Алексеев");
    List<String> patronymics = Arrays.asList("Иванович", "Федорович", "Андреевич",
            "Сергеевич", "Алексеевич");
    List<String> specializations = Arrays.asList("хирург", "невролог", "лор",
            "терапевт", "кардиолог");

    private String getRandomString(List<String> strings) {
        Random r = new Random();
        int index = r.nextInt(strings.size());
        return strings.get(index);
    }

    private String getRandomFirstName() {
        return getRandomString(firstNames);
    }

    private String getRandomLastName() {
        return getRandomString(lastNames);
    }

    private String getRandomPatronymic() {
        return getRandomString(patronymics);
    }

    private String getRandomSpecialization() {
        return getRandomString(specializations);
    }

    private Priority getRandomPriority() {
        Random r = new Random();
        int index = r.nextInt(3);
        switch (index) {
            case 0: {
                return Priority.NORMAL;
            }
            case 1: {
                return Priority.CITO;
            }
            case 2: {
                return Priority.STAIM;
            }
            default: {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Bean
    public CommandLineRunner initRepos(PatientRepository patientRepository,
            DoctorRepository doctorRepository, RecipeRepository recipeRepository) {
        return (args) -> {

            List<Patient> patients = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Patient p = new Patient(getRandomFirstName(), getRandomLastName(),
                        getRandomPatronymic(), "123456789" + i);
                p.setPhone(p.getPhone().substring(0, 10));
                patients.add(p);
            }
            patientRepository.saveAll(patients);

            List<Doctor> doctors = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Doctor doctor = new Doctor(getRandomFirstName(), getRandomLastName(),
                        getRandomPatronymic(), getRandomSpecialization());
                doctors.add(doctor);
            }
            doctorRepository.saveAll(doctors);

            List<Recipe> recipes = new ArrayList<>();
            String description = "Супер_пупер_антиболин 3 раза в день по 1 табл.";
            for (int i = 0; i < 10; i++) {
                Recipe recipe = new Recipe(description + "№ " + i,
                        patientRepository.findAll().get(i),
                        doctorRepository.findAll().get(i), 14, getRandomPriority());
                recipes.add(recipe);
            }
            recipeRepository.saveAll(recipes);
        };
    }
}
