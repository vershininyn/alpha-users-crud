package dev.projects.alpha.userscrud.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-sequence-generator")
//    @SequenceGenerator(name = "user-sequence-generator", sequenceName = "user_sequence", allocationSize = 1, initialValue = 1)
    private Integer id;

    @Column(name = "login", nullable = false, length = 25, unique = true)
    private String login;

    @Column(name = "password", length = 25)
    private String password;

    @Column(name = "firstname", length = 25)
    private String firstname;

    @Column(name = "secondname", length = 25)
    private String secondname;

    @Column(name = "thirdname", length = 25)
    private String thirdname;

    @Column(name = "is_banned")
    private Boolean isBanned;
}