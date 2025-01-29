package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.User.User;
import com.picpaysimplificado.domain.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() != UserType.valueOf("COMMON")){
            throw new Exception("Usuário do tipo lojista não está autorizado a realizar transação");
        }

        if(sender.getBalance().compareTo(amount) < 0 ){
            throw new Exception("Usuário não tem valor o suficiente para realizar transação");
        }
    }

    public User findById(Long id){
        Optional<User> userOpt = repository.findById(id);
        if(userOpt.isPresent()){
            return userOpt.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }

    public void saveUser(User user){
        this.repository.save(user);
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        List<User> users =  this.repository.findAll();

        if(users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há usuarios cadastrados");
        }
        return users;
    }

    public void deleteUser(Long id){
        if(id == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id passado está nulo!");
        }
        User userDelete = findById(id);
        repository.delete(userDelete);
    }

    public User patchUser(User user, Long id){
        User updateUser = findById(id);

    }
}
