package ru.team451.vtbstaff.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.team451.vtbstaff.service.UserService;
import ru.team451.vtbstaff.service.UsersApiActions;
import ru.team451.vtbstaff.wallet.TransferInfoCoins;
import ru.team451.vtbstaff.wallet.TransferOutput;

@Data
@Service
@AllArgsConstructor
@Slf4j
public class WalletCoinsFiller implements ApiMethodStarter<TransferInfoCoins>, Callback<TransferOutput> {
    private static final String ADMIN_PUBLIC_KEY = "0x7259c657e3430411208013Aa7D62b2D22B343462";
    private static final String ADMIN_PRIVATE_KEY = "72793296cc28887ed0db271006fb2b641d8c078273292ae0c0a84fa36af1dd6c";
    private static final int STARTER_RUBLE_BONUS = 15_000;
    private UsersApiActions usersApiActions;
    private UserService userService;
    @Override
    public void start(TransferInfoCoins info) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HackAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HackAPI api = retrofit.create(HackAPI.class);
        usersApiActions.getWalletsToFillWithCoins().add(info);
        Call<TransferOutput> call = api.transferRuble(info);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TransferOutput> call, Response<TransferOutput> response) {
        if (response.isSuccessful() && !usersApiActions.getWalletsToFillWithCoins().isEmpty()) {
            TransferOutput transactionHash = response.body();
            TransferInfoCoins appUser = usersApiActions.getWalletsToFillWithCoins().pop();

//            userService.saveUser(appUser);
        }
        else {
            log.error("error to get wallet");
        }
    }

    @Override
    public void onFailure(Call<TransferOutput> call, Throwable throwable) {
        log.error("Fail to create user wallet");
    }
}
