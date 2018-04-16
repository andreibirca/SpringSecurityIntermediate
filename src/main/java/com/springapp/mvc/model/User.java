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

    @Length(min = 3, message = "The field must be at least 3 characters !")
    @Column(name = "username")
    private String username;

    @ManyToMany
    private Set<Role> roles;

    @NotNull
    @Size(min = 5, message = "length must be more or \nequal to 5 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&.+=])(?=\\S+$).{8,}$",message = "Password must have 1 Big/small letter 1 special sign 1 number")
    private String password;

    private String passwConfirm;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @Size(min = 6, max = 30, message = "Lungimea trebuie sa corespunda cond. >=6 and <=25")
    @Pattern.List({
            @Pattern(regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message
                    ="Nu corespunde sablonului: \nemail_name@provider_name.extention"),

    })
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String role = "ROLE_USER";

}
