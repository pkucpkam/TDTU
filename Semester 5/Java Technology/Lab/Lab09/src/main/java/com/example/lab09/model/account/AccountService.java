package com.example.lab09.model.account;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public Account addAccount(Account account){
        account.setDisable(false);
        account.setPassword(encoder.encode(account.getPassword()));
        repository.save(account);
        return account;
    }

    public Account findByUsername(String username){
        return repository.findByUsername(username);
    }

    public List<Account> getAllAccount(){
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = repository.findByUsername(username);
        if(account == null)
            throw new UsernameNotFoundException("No account with " + username);
        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRoles())
                .disabled(account.isDisable()).build();
    }
}
