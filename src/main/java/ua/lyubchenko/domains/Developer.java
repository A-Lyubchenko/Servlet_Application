package ua.lyubchenko.domains;

import lombok.*;
import ua.lyubchenko.repositories.Identity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Developer implements  Identity {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private String phone_number;
    private Integer salary;

}
