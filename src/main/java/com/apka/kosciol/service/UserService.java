package com.apka.kosciol.service;

import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.entity.Role;
import com.apka.kosciol.entity.User;
import com.apka.kosciol.exceptions.AlreadyExistException;
import com.apka.kosciol.exceptions.DoesNotExistException;
import com.apka.kosciol.repository.IUser;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractChangeService implements UserDetailsService {

    private final IUser userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByLogin(username)
                .map(this::build)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
    }

    UserDetails build(User u) {
        var roles = Collections.singletonList(new SimpleGrantedAuthority(u.getRole().name()));
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), roles);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void delete(Object changeEntity) {
        if (changeEntity != null && changeEntity instanceof User) {
            userRepository.delete((User) changeEntity);
        } else {
            System.err.println("Your user doesn't have data.");
        }
    }

    @Override
    public void save(Object changeEntity) {
        if (changeEntity instanceof User && changeEntity != null) {
            userRepository.save((User) changeEntity);
        } else {
            System.err.println("Your user doesn't have data.");
        }
    }

    @Override
    public Optional<Object> findById(int id) {
        return Optional.of(userRepository.findById(id));
    }

    @Override
    public void update(int id, Object changeEntity) {
        User newUser;
        if (changeEntity instanceof User && changeEntity != null) {
            newUser = ((User) changeEntity);
        } else {
            System.err.println("Your user doesn't have data.");
            return;
        }
        User userTableEntity = userRepository.getOne(id);

        userTableEntity.setLogin(newUser.getLogin());
        userTableEntity.setEmail(newUser.getEmail());
        userTableEntity.setFirstName(newUser.getFirstName());
        userTableEntity.setLastName(newUser.getLastName());
        userTableEntity.setPassword(newUser.getPassword());
        userTableEntity.setRole(newUser.getRole());

        userRepository.save(userTableEntity);
    }

    // zwraca znalezionego usera o danym adresie email
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void edit(UserDto userDto) {
        try {
            User user = userRepository.getOne(userDto.getId());
            user = setAllFieldsOfUser(userDto, user, false);
            userRepository.save(user);
        } catch (Exception e) {
            int i = 0; // cos poszlo nie tak
        }
    }
    public boolean checkLogin(UserDto userDto) throws DoesNotExistException{
        if(loginExists(userDto.getLogin())) {
            User user = userRepository.getByLogin(userDto.getLogin());
            user = setAllFieldsOfUser(userDto, user, false);
            userRepository.save(user);
            return true;
        } else{
            throw new DoesNotExistException ("There is no account with that login: "
                    + userDto.getLogin() + ". Please enter diffrent login.");

        }
    }
    public boolean checkIsChangedPassword(UserDto userDto) {
        try {
            User user = userRepository.getOne(userDto.getId());
            user = setAllFieldsOfUser(userDto, user, false);
            userRepository.save(user);
        } catch (Exception e) {
            int i = 0; // cos poszlo nie tak
        }
        return false;
    }

    public void registerNewUserAccount(UserDto userDto) throws AlreadyExistException {
        if (loginExists(userDto.getLogin())) {
            throw new AlreadyExistException("There is an account with that login: "
                    + userDto.getLogin() + ". Please enter diffrent login.");
        }
        if (emailExists(userDto.getEmail())) {
            throw new AlreadyExistException("There is an account with that email: "
                    + userDto.getLogin() + ". Please enter diffrent email.");
        }
        userRepository.save(setAllFieldsOfUser(userDto, new User(), true));
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean loginExists(String login) {
        return userRepository.existsUserByLogin(login);//findByLogin(login).isPresent(); //metoda exist, w repo ja napisac
    }

    private User setAllFieldsOfUser(UserDto userDto, User user, boolean isNew) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        //te co musza byc:

        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if (isNew) {
            user.setRole(Role.ROLE_ADMIN);
            user.setChangedPassword(false);
            user.setQtyOfWrongPassword(0);
            user.setActive(true);
        } else {
            user.setChangedPassword(true); //todo
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); //todo czy jest haslo
        return user;
    }

    private UserDto setAllFieldsOfUserDto(User user) {
        //todo password odkodowac
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
