package ru.team451.vtbstaff.wallet;

import lombok.Data;

import java.util.List;

@Data
public class GeneratedNftList {
    private String toPublicKey;
    private List<Integer> tokens;
}
