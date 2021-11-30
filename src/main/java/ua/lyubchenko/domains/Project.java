package ua.lyubchenko.domains;

import lombok.*;
import ua.lyubchenko.repositories.Identity;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Project implements Identity {
    private Integer id;
    private String name;
    private Date start;
    private Integer coast;
}
