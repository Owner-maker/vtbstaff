package ru.team451.vtbstaff.wallet;

import lombok.Data;

@Data
public class TransferInfoCoins {
    private String fromPrivateKey;
    private String toPublicKey;
    private int amount;
}