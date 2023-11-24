package academy.devdojo.maratonajava.javacore.globalsolution.service;

import academy.devdojo.maratonajava.javacore.globalsolution.model.Hospital;
import academy.devdojo.maratonajava.javacore.globalsolution.repository.HospitalRepository;

import java.util.Optional;
import java.util.Scanner;


public class HospitalService {
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
        HospitalRepository.findByName(name).forEach(h -> System.out.printf(" [%d] - %s %n", h.getId(), h.getName()));

    }

    private static void delete() {
        System.out.println("Type the 'id' of the producer you want to 'delete'");
        final int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? Y / N");
        String choice = SCANNER.nextLine();
        if ("y".equalsIgnoreCase(choice)) {
            HospitalRepository.delete(id);
        }

    }

    private static void save() {
        System.out.println("Type the 'name' of the hospital");
        String name = SCANNER.nextLine();
        final Hospital hospital = Hospital.builder().name(name).build();
        HospitalRepository.save(hospital);
    }

    private static void update() {
        System.out.println("Type the 'id' of the object you want to 'update'");
        final Optional<Hospital> hospitalOptional = HospitalRepository.findById(Integer.parseInt(SCANNER.nextLine()));
        if (hospitalOptional.isEmpty()) {
            System.out.println("Hospital not found");
            return;
        }
        final Hospital hospitalFromDb = hospitalOptional.get();
        System.out.println("Hospital found " + hospitalFromDb);
        System.out.println("Type the 'name' or 'enter' to keep the same");
        String name = SCANNER.nextLine();
        name = name.isEmpty() ? hospitalFromDb.getName() : name;
        final Hospital hospitalToUpdate = Hospital.builder().id(hospitalFromDb.getId()).name(name).build();

        HospitalRepository.update(hospitalToUpdate);

    }


}
