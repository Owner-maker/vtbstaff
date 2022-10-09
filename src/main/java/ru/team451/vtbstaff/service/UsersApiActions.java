package ru.team451.vtbstaff.service;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.team451.vtbstaff.wallet.TransferInfoCoins;

import java.util.ArrayDeque;
import java.util.Deque;

@Data
@Service
@Scope("singleton")
public class UsersApiActions {
    private Deque<String> usersToCreateWallet = new ArrayDeque<>();
    private Deque<TransferInfoCoins> walletsToFillWithCoins = new ArrayDeque<>();
}
