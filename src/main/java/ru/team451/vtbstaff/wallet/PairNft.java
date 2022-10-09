package ru.team451.vtbstaff.wallet;

import lombok.Data;

import java.util.List;

@Data
public class PairNft {
    private String URI;
    private List<Integer> tokens;
}
