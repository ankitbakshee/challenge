package com.db.awmd.challenge.service;
import com.db.awmd.challenge.domain.TransactionAccount;

public interface NotificationService {

  String notifyAboutTransfer(TransactionAccount transactionAccount);
  String notifyAboutAccountCreation();
}
