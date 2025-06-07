package lv.venta.cbs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String username;
    private String passwordHash;
    private String email;
    private String fullName;
    private String phoneNumber;
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;
}
