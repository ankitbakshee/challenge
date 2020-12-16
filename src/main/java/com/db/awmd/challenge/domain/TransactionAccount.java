package com.db.awmd.challenge.domain;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransactionAccount {


    @NotNull
    private String accountTo;

    @NotNull
    private String accountFrom;

    @NotNull
    @Min(value = 0, message = "Initial balance must be positive.")
    private BigDecimal balance;

    public String getAccountTo() {
        return accountTo;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}
