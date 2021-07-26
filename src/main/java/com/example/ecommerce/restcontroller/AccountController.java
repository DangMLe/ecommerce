package com.example.ecommerce.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ecommerce.DTO.AccountDTO;
import com.example.ecommerce.entity.Account;
import com.example.ecommerce.service.AccountService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
class AccountController {
  @Autowired
  private AccountService accountService;

  @Autowired
  private ModelMapper modelMapper;


  @GetMapping("/accounts")
  @PreAuthorize("hasRole('ADMIN)")
  List<AccountDTO> all() {
    List<Account> accounts = accountService.retrieveAccounts();
    return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
  }
  private AccountDTO convertToDTO(Account account){
    AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
    return accountDTO;
  }
  //convert to 2 entities
  private Account convertToEntity(AccountDTO accountDTO) throws ParseException {
    Account account = modelMapper.map(accountDTO, Account.class);
    return account;
}

  @PostMapping("/accounts")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  AccountDTO newAccount(@RequestBody AccountDTO newAccountDTO) {
    return convertToDTO(accountService.saveAccount(convertToEntity(newAccountDTO)));
  }

  @GetMapping("/account/{id}")
  AccountDTO getAccount(@PathVariable Long id) {
    
    // return accountService.AccountById(id)
    //   .orElseThrow(() -> new AccountException(id));
    return convertToDTO(accountService.getAccount(id));
  }

  @PutMapping("/account/{id}")
  void replaceAccount(@RequestBody AccountDTO accountDTO, @PathVariable Long id) {
    Account account = convertToEntity(accountDTO);
    accountService.updateAccount(account, id);
    // return accountService.findById(id)
    //   .map(account -> {
    //     account.setName(newAccount.getName());
    //     account.setpassword(newAccount.getpassword());
    //     return accountService.save(account);
    //   })
    //   .orElseGet(() -> {
    //     newAccount.setId(id);
    //     return accountService.save(newAccount);
    //   });
  }

  @DeleteMapping("/account/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  void deleteAccount(@PathVariable Long id) {
    accountService.deleteAccount(id);
  }
}