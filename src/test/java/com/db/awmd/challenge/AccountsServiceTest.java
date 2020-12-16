package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientAmountException;
import com.db.awmd.challenge.service.AccountsService;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

  @Autowired
  private AccountsService accountsService;

  @Test
  public void addAccount() throws Exception {
    Account account = new Account("Id-123");
    account.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(account);

    assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);
  }

  @Test
  public void addAccount_failsOnDuplicateId() throws Exception {
    String uniqueId = "Id-" + System.currentTimeMillis();
    Account account = new Account(uniqueId);
    this.accountsService.createAccount(account);

    try {
      this.accountsService.createAccount(account);
      fail("Should have failed when adding duplicate account");
    } catch (DuplicateAccountIdException ex) {
      assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
    }

  }

 /* @Test
  public void successfulTransfer() throws Exception {
    Account account = new Account("I-121", new BigDecimal("1500.45"));
    Account account1 = new Account("I-122",new BigDecimal("1000"));
    accountsService.createAccount(account1);
    accountsService.createAccount(account);
    accountsService.getAllAccounts();
    boolean flag = this.accountsService.transfer(account.getAccountId(),account1.getAccountId(), new BigDecimal("200"));
    assertThat(flag);
  }

  @Test
  public void unSuccessfulTransferFail() throws Exception {
    Account account = new Account("I-124", new BigDecimal("500"));
    Account account1 = new Account("I-125",new BigDecimal("1000"));
    accountsService.createAccount(account1);
    accountsService.createAccount(account);
    accountsService.getAllAccounts();
    try {
      boolean flag = this.accountsService.transfer(account.getAccountId(), account1.getAccountId(), new BigDecimal("600"));
      fail("Fail");
    }catch (InsufficientAmountException iae){
      assertThat(iae.getMessage()).isEqualTo("Account id " + account.getAccountId() + " Insufficient Balance to withdraw");
    }
  } */
}
