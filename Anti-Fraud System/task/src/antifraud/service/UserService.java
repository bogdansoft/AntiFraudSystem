package antifraud.service;

import antifraud.model.Role;
import antifraud.model.User;
import antifraud.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException("User " + username + " not found"));
    }

    @Transactional
    public Optional<User> register(User user) {
        if (userRepository.count() == 0) {
            user.setRole(Role.ADMINISTRATOR);
            user.setAccountNonLocked(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return Optional.of(userRepository.save(user));
        }

        if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            return Optional.empty();
        }

        user.setRole(Role.MERCHANT);
        user.setAccountNonLocked(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }

    public List<User> listUsers() {
        return userRepository.findAll(Sort.sort(User.class).by(User::getId).ascending());
    }

    @Transactional
    public boolean delete(String username) {
        return userRepository.deleteByUsernameIgnoreCase(username) == 1;
    }

    public User changeRole(String username, Role role) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (user.getRole() == role) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        user.setRole(role);

        return userRepository.save(user);
    }
}