package academy.devdojo.maratonajava.javacore.globalsolution.test;

import academy.devdojo.maratonajava.javacore.globalsolution.service.DoctorService;
import academy.devdojo.maratonajava.javacore.globalsolution.service.HospitalService;

import java.util.Scanner;

public class CrudTest {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int op;
        while (true){
            menu();
            op = Integer.parseInt(SCANNER.nextLine());
            if (op == 0)break;
            switch (op){
                case 1 -> {
                    hospitalMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    HospitalService.menu(op);
                }
                case 2 -> {
                    doctorMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    DoctorService.menu(op);
                }
            }
        }
    }

    private static void menu(){
        System.out.println("Type the number of your operation");
        System.out.println("1. Hospital Management");
        System.out.println("2. Doctor Management");
        System.out.println("0. Exit");
    }

    private static void hospitalMenu(){
        System.out.println("Type the number of your operation");
        System.out.println("1. Search for hospital");
        System.out.println("2. Delete hospital");
        System.out.println("3. Save hospital");
        System.out.println("4. Update hospital");
        System.out.println("9. Go Back");

    }
    private static void doctorMenu(){
        System.out.println("Type the number of your operation");
        System.out.println("1. Search for doctor");
        System.out.println("2. Delete doctor");
        System.out.println("3. Save doctor");
        System.out.println("4. Update doctor");
        System.out.println("9. Go Back");

    }
}
