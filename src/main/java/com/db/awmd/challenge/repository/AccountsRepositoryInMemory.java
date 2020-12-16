package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransactionAccount;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.db.awmd.challenge.exception.InsufficientAmountException;
import com.db.awmd.challenge.exception.NoAccountsPresentException;
import com.db.awmd.challenge.exception.SameAccountTransferException;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {
  static final Logger log = Logger.getLogger(AccountsRepositoryInMemory.class);

  private final Map<String, Account> accounts = new ConcurrentHashMap<>();

  @Override
  public void createAccount(Account account) throws DuplicateAccountIdException {
    Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
    if (previousAccount != null) {
      throw new DuplicateAccountIdException(
        "Account id " + account.getAccountId() + " already exists!");
    }
  }

  @Override
  public List<Account> getAllAccounts() throws NoAccountsPresentException {
      List<Account> accountList = new ArrayList<Account>();
      for (Map.Entry<String, Account> entry : accounts.entrySet()) {
          accountList.add(entry.getValue());
      }
      if(accountList.size() > 0)
          return accountList;
      else
          throw new NoAccountsPresentException();
  }
  @Override
  public Account getAccount(String accountId) {
    return accounts.get(accountId);
  }

  @Override
  public void clearAccounts() {
    accounts.clear();
  }

    @Override
    public TransactionAccount transfer(TransactionAccount transactionAccount) throws InsufficientAmountException , SameAccountTransferException , NoAccountsPresentException{
        log.info("transfer : Start");
        Account accFrom  = getAccount(transactionAccount.getAccountFrom());
        Account accTo = getAccount(transactionAccount.getAccountTo());
        if(accTo == null || accFrom == null) throw new NoAccountsPresentException();
        synchronized (accTo){
                   synchronized (accFrom) {
                       if (accTo.getAccountId().equals(accFrom.getAccountId())) {
                           throw new SameAccountTransferException();

                       } else if (accFrom.getBalance().compareTo(transactionAccount.getBalance()) == 1 || accFrom.getBalance().compareTo(transactionAccount.getBalance()) == 0) {
                           accFrom.setBalance(accFrom.getBalance().subtract(transactionAccount.getBalance()));
                           accTo.setBalance(accTo.getBalance().add(transactionAccount.getBalance()));
                           accounts.put(accFrom.getAccountId(), accFrom);
                           accounts.put(accTo.getAccountId(), accTo);
                           return transactionAccount;
                       } else {
                           throw new InsufficientAmountException();
                       }
                   }
          }
      }

}
