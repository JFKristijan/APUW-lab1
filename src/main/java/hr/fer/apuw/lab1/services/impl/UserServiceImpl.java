package hr.fer.apuw.lab1.services.impl;

import hr.fer.apuw.lab1.exception.NoSuchEntityException;
import hr.fer.apuw.lab1.models.User;
import hr.fer.apuw.lab1.repository.UserRepository;
import hr.fer.apuw.lab1.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public User createOrUpdateUser(Long id, User user) {
        Optional<User> dbUser = userRepository.findById(id);
        User toSave;
        if(dbUser.isPresent()){
            toSave = dbUser.get();
            if(user.getUsername() != null){
                toSave.setUsername(user.getUsername());
            }
            if(user.getPassword() != null){
                toSave.setPassword(user.getPassword());
            }
        }else{
            toSave=user;
        }
        return userRepository.save(toSave);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
