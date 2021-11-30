package ua.lyubchenko.domains;

import lombok.*;
import ua.lyubchenko.repositories.Identity;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Skill implements  Identity {
    private Integer id;
    private String department;
    private String level;

    @Override
    public String getName() {
        return null;
    }
}
