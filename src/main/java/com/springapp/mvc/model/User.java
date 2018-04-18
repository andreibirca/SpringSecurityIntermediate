package com.springapp.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "name")
    private String  name;

    @Column(name = "surname")
    private String surname;

    @Size(min = 2, message = "The field must be at least 5 characters !")
    @Column(name = "username")
    private String username;

    @ManyToMany
    private Set<Role> roles;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&.+=])(?=\\S+$).{8,}$"
            ,message = "Password must contain 1 uppercase char & 1 & special simbol & 1 digit" +
            "& length>=8")
    private String password;

    private transient String passwConfirm;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @Size(min = 6, max = 30, message = "Lungimea trebuie sa corespunda cond. 6<= length <=25")
    @Pattern.List({
            @Pattern(regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message
                    ="Nu corespunde sablonului: \nemail_name@provider_name.extention"),

    })
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;


}
