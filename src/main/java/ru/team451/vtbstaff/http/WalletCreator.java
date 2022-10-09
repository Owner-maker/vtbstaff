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
import ru.team451.vtbstaff.domain.AppUser;
import ru.team451.vtbstaff.service.UserService;
import ru.team451.vtbstaff.service.UsersApiActions;
import ru.team451.vtbstaff.wallet.WalletKeys;

@Data
@Service
@AllArgsConstructor
@Slf4j
public class WalletCreator implements Callback<WalletKeys>, ApiMethodStarter<String> {
    private UserService userService;
    private UsersApiActions usersApiActions;

    public void start(String username) {
//        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HackAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HackAPI api = retrofit.create(HackAPI.class);
        usersApiActions.getUsersToCreateWallet().add(username);
        Call<WalletKeys> call = api.getWallet();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<WalletKeys> call, Response<WalletKeys> response) {
        if (response.isSuccessful() && !usersApiActions.getUsersToCreateWallet().isEmpty()) {
            WalletKeys wallet = response.body();
            log.info("private key:{}, public key: {}", wallet.getPrivateKey(), wallet.getPublicKey());
            AppUser appUser = userService.getUser(usersApiActions.getUsersToCreateWallet().pop());
            appUser.setPrivateKey(wallet.getPrivateKey());
            appUser.setPublicKey(wallet.getPublicKey());
            userService.saveUser(appUser);
        }
        else {
            log.error("error to get wallet");
        }
    }

    @Override
    public void onFailure(Call<WalletKeys> call, Throwable throwable) {
        log.error("Fail to create user wallet");
    }
}
