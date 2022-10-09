package ru.team451.vtbstaff.wallet;

import lombok.Data;

@Data
public class NftInfo {
    private Integer tokenId;
    private String uri;
    private String publicKey;
}
