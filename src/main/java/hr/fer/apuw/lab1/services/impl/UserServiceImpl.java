package hr.fer.apuw.lab1.services.impl;

import hr.fer.apuw.lab1.exception.EntityAlreadyExistsException;
import hr.fer.apuw.lab1.exception.ForbiddenAccessException;
import hr.fer.apuw.lab1.exception.NoSuchEntityException;
import hr.fer.apuw.lab1.models.User;
import hr.fer.apuw.lab1.repository.UserRepository;
import hr.fer.apuw.lab1.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new EntityAlreadyExistsException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public void deleteById(Long id, org.springframework.security.core.userdetails.User user) {
        if(!findByUsername(user.getUsername()).getId().equals(id)){
            throw new ForbiddenAccessException();
        }
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public User updateUser(Long id, User user, org.springframework.security.core.userdetails.User secUser) {
        User dbUser = userRepository.findById(id).orElseThrow(NoSuchEntityException::new);
        if(!findByUsername(secUser.getUsername()).getId().equals(id)){
            throw new ForbiddenAccessException();
        }
        if(user.getUsername() != null){
            dbUser.setUsername(user.getUsername());
        }
        if(user.getPassword() != null){
            dbUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(dbUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(NoSuchEntityException::new);
    }


}
