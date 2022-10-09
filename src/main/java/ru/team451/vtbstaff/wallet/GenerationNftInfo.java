package ru.team451.vtbstaff.wallet;

import lombok.Data;

@Data
public class GenerationNftInfo {
    private String toPublicKey;
    private String uri;
    private int nftCount;
}
