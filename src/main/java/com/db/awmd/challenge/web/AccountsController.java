package com.db.awmd.challenge.web;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransactionAccount;
import com.db.awmd.challenge.service.AccountsService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/v1/accounts")
public class AccountsController {

  static final Logger log = Logger.getLogger(AccountsController.class);

  private final AccountsService accountsService;

  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
   }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
    log.info("Creating account {}"+ account);
    return new ResponseEntity<>(this.accountsService.createAccount(account),HttpStatus.CREATED);
  }

  @GetMapping(path = "/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
    log.info("Retrieving account for id {}" + accountId);
    return this.accountsService.getAccount(accountId);
  }

  @PutMapping(path = "/transfer" ,consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> transfer(@RequestBody @Valid TransactionAccount transactionAccount) {
    log.info("transfer : Start");
    return new ResponseEntity<>(this.accountsService.transfer(transactionAccount) , HttpStatus.OK);
  }


  @GetMapping(path = "/getAllAccounts")
  public ResponseEntity<Object> getAllAccounts() {
    return new ResponseEntity<>(this.accountsService.getAllAccounts() , HttpStatus.OK);
  }

}
