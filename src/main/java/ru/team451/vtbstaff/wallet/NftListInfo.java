package ru.team451.vtbstaff.wallet;

import lombok.Data;

import java.util.List;

@Data
public class NftListInfo {
    private String wallet_id;
    private List<Integer> tokens;
}
