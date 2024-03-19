package com.banking.service.impl;
import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.repository.AccountRepository;
import com.banking.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = mapToEntity(accountDto);
        Account savedAccount = accountRepository.save(account);
        return mapToDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(()->
                new RuntimeException("Account does not exist"));
        return mapToDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()->
                new RuntimeException("Account does not exist"));
        double total = account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return mapToDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()->
                new RuntimeException("Account does not exist"));
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient Amount");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return mapToDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(account -> mapToDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()->
                new RuntimeException("Account does not exist"));
        accountRepository.deleteById(id);
    }

    //CONVERTING ENTITY INTO DTO
    private AccountDto mapToDto(Account account) {

        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        return accountDto;
    }
    //CONVERTING DTO INTO ENTITY
    private Account mapToEntity(AccountDto accountDto) {

        Account account = modelMapper.map(accountDto, Account.class);
        return account;
    }
}