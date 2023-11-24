package academy.devdojo.maratonajava.javacore.globalsolution.service;

import academy.devdojo.maratonajava.javacore.globalsolution.model.Doctor;
import academy.devdojo.maratonajava.javacore.globalsolution.model.Hospital;
import academy.devdojo.maratonajava.javacore.globalsolution.repository.DoctorRepository;

import java.util.Optional;
import java.util.Scanner;


public class DoctorService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void menu(int op) {
        switch (op) {
            case 1 -> findByName();
            case 2 -> delete();
            case 3 -> save();
            case 4 -> update();
        }
    }


    private static void findByName() {
        System.out.println("Type the 'name' or 'empty' to all");
        String name = SCANNER.nextLine();
        DoctorRepository.findByName(name).forEach(h -> System.out.printf(" [%d] - Name: %s, CRM: %d, Hospital: %s %n", h.getId(), h.getName(), h.getCrm(), h.getHospital().getName()));

    }

    private static void delete() {
        System.out.println("Type the 'id' of the producer you want to 'delete'");
        final int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? Y / N");
        String choice = SCANNER.nextLine();
        if ("y".equalsIgnoreCase(choice)) {
            DoctorRepository.delete(id);
        }

    }

    private static void save() {
        System.out.println("Type the 'name' of the doctor");
        String name = SCANNER.nextLine();
        System.out.println("Type the number of 'CRM'");
        int crm = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Type the 'id' of the 'hospital'");
        Integer hospitalId = Integer.parseInt(SCANNER.nextLine());

        final Doctor doctor = Doctor.builder()
                .crm(crm)
                .name(name)
                .hospital(Hospital.builder().id(hospitalId).build())
                .build();
        DoctorRepository.save(doctor);
    }

    private static void update() {
        System.out.println("Type the 'id' of the object you want to 'update'");
        final Optional<Doctor> doctorOptional = DoctorRepository.findById(Integer.parseInt(SCANNER.nextLine()));
        if (doctorOptional.isEmpty()) {
            System.out.println("Doctor not found");
            return;
        }
        final Doctor doctorFromDb = doctorOptional.get();
        System.out.println("Doctor found " + doctorFromDb);

        System.out.println("Type the 'name' or 'enter' to keep the same");
        String name = SCANNER.nextLine();
        name = name.isEmpty() ? doctorFromDb.getName() : name;

        System.out.println("Type the new number of 'crm'");
        int crm = Integer.parseInt(SCANNER.nextLine());
        name = name.isEmpty() ? doctorFromDb.getName() : name;

        final Doctor doctorToUpdate = Doctor.builder()
                .id(doctorFromDb.getId())
                .crm(crm)
                .hospital(doctorFromDb.getHospital())
                .name(name)
                .build();

        DoctorRepository.update(doctorToUpdate);

    }


}
