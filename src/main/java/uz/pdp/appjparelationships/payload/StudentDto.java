package uz.pdp.appjparelationships.payload;

import lombok.Data;

import java.util.List;

@Data
public class StudentDto {
    private String firstName;
    private String lastName;
    private Integer address_id;
    private Integer group_id;
    private List<Integer> subject_id;

}
