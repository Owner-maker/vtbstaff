package ru.team451.vtbstaff.wallet;

import lombok.Data;

@Data
public class TransferInfoNft {
    private String fromPrivateKey;
    private String toPublicKey;
    private int tokenId;
}
