package uz.pdp.order_generate_bot.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.order_generate_bot.security.entity.Role;
import uz.pdp.order_generate_bot.security.entity.RoleName;
import uz.pdp.order_generate_bot.security.entity.User;
import uz.pdp.order_generate_bot.security.repo.RoleRepository;
import uz.pdp.order_generate_bot.security.repo.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role role = roleRepository.save(new Role(1, RoleName.ROLE_USER));

        userRepository.save(new User(1,"a",passwordEncoder.encode("123"), List.of(role)));
        userRepository.save(new User(2,"b",passwordEncoder.encode("123"), List.of(role)));
        userRepository.save(new User(3,"c",passwordEncoder.encode("123"), List.of(role)));
        userRepository.save(new User(4,"d",passwordEncoder.encode("123"), List.of(role)));
        userRepository.save(new User(5,"e",passwordEncoder.encode("123"), List.of(role)));
        userRepository.save(new User(6,"f",passwordEncoder.encode("123"), List.of(role)));
    }
}
