package uz.pdp.order_generate_bot.bot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.order_generate_bot.bot.entity.enums.UserState;

import javax.xml.stream.Location;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class TgUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long chatId;

    private String username;

    private String phone;

    private String firstName;

    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    private State state;

    private float lat;
    private float lng;


}
