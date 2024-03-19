package com.banking.service.impl;
import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.repository.AccountRepository;
import com.banking.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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