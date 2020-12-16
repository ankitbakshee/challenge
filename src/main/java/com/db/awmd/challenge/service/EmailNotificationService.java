package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.TransactionAccount;
import org.jboss.logging.Logger;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailNotificationService implements NotificationService {

  static final Logger log = Logger.getLogger(EmailNotificationService.class);

  private final ResourceBundleMessageSource resourceBundleMessageSource;

  public EmailNotificationService(ResourceBundleMessageSource resourceBundleMessageSource) {
    this.resourceBundleMessageSource = resourceBundleMessageSource;
  }

  @Override
  public String notifyAboutTransfer(TransactionAccount transactionAccount) {
    log.info("notifyAboutTransfer : Start");
   return transactionAccount.getBalance()+ this.resourceBundleMessageSource.getMessage("text.amountTransferText" ,null, Locale.getDefault())+
            this.resourceBundleMessageSource.getMessage("text.fromAccount" ,null, Locale.getDefault()) +
            transactionAccount.getAccountFrom() + " "+
            this.resourceBundleMessageSource.getMessage("text.toAccount" ,null, Locale.getDefault())+
            transactionAccount.getAccountTo();
  }

  @Override
  public String notifyAboutAccountCreation() {
    log.info("notifyAboutAccountCreation : Start");
    return  this.resourceBundleMessageSource.getMessage("text.amountCreated" ,null, Locale.getDefault());

  }

}
