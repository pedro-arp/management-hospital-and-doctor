package academy.devdojo.maratonajava.javacore.globalsolution.model;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Doctor {
    Integer id;
    String name;
    int crm;
    Hospital hospital;
}
