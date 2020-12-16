package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransactionAccount;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientAmountException;
import com.db.awmd.challenge.exception.NoAccountsPresentException;
import com.db.awmd.challenge.exception.SameAccountTransferException;

import java.util.List;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);

  void clearAccounts();
  TransactionAccount transfer(TransactionAccount transactionAccount) throws SameAccountTransferException, InsufficientAmountException ,NoAccountsPresentException;

  List<Account> getAllAccounts() throws NoAccountsPresentException;
}
