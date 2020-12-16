package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransactionAccount;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientAmountException;
import com.db.awmd.challenge.exception.NoAccountsPresentException;
import com.db.awmd.challenge.exception.SameAccountTransferException;
import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.challenge.web.AccountsController;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

  static final Logger log = Logger.getLogger(AccountsService.class);

  private final AccountsRepository accountsRepository;
  private final EmailNotificationService emailNotificationService;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository, EmailNotificationService emailNotificationService) {
    this.accountsRepository = accountsRepository;
    this.emailNotificationService = emailNotificationService;
  }

  public String createAccount(Account account) throws DuplicateAccountIdException {
    log.info("createAccount : Start");
    try {
      this.accountsRepository.createAccount(account);
      return this.emailNotificationService.notifyAboutAccountCreation();
    } catch (DuplicateAccountIdException daie) {
      return daie.getMessage();
    }
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

  public AccountsRepository getAccountsRepository() {
    return accountsRepository;
  }

  public  String transfer(TransactionAccount transactionAccount) throws SameAccountTransferException, InsufficientAmountException {
    log.info("transfer : Start");
    try{
      this.accountsRepository.transfer(transactionAccount);
      return this.emailNotificationService.notifyAboutTransfer(transactionAccount);
    }catch (InsufficientAmountException | SameAccountTransferException | NoAccountsPresentException e ){
      log.error("transfer :error");
      return e.getMessage();
    }
  }

  public Object getAllAccounts() {
    log.info("getAllAccounts : Start");
    try{
      return this.accountsRepository.getAllAccounts();
    }catch (NoAccountsPresentException nae){
      log.error("getAllAccounts : error");
      return nae.getMessage();
    }
  }

}
