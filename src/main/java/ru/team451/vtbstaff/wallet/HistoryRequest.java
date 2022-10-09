package ru.team451.vtbstaff.wallet;

import lombok.Data;

@Data
public class HistoryRequest {
    private Integer page;
    private Integer offset;
    private String sort;
}
