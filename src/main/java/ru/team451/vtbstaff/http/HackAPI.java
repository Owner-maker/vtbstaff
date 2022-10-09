package ru.team451.vtbstaff.http;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.team451.vtbstaff.wallet.*;

public interface HackAPI {
    String BASE_URL = "https://hackathon.lsp.team/hk/v1/";

    @POST("wallets/new")
    Call<WalletKeys> getWallet();

    @POST("transfers/matic")
    Call<TransferOutput> transferMatic(@RequestBody TransferInfoCoins info);

    @POST("transfers/ruble")
    Call<TransferOutput> transferRuble(@RequestBody TransferInfoCoins info);

    @POST("transfers/nft")
    Call<TransferOutput> transferNft(@RequestBody TransferInfoNft info);

    @GET("transfers/status")
    Call<TransferStatus> getTransferStatus(@PathVariable String transactionHash);

    @GET("{publicKey}/balance")
    Call<Wallet> getBalanceWallet(@PathVariable String publicKey);

    @GET("wallets/{publicKey}/nft/balance")
    Call<WalletNft> getBalanceNTF(@PathVariable String publicKey);

    @POST("nft/generate")
    Call<TransferOutput> generateNftToWallet(@RequestBody GenerationNftInfo nftInfo);

    @GET("nft/{tokenId}")
    Call<NftInfo> getNftInfo(@PathVariable String tokenId);

    @GET("nft/generate/{transactionHash}")
    Call<NftListInfo> getGeneratedNftList(@PathVariable String transactionHash);

//    @GET("/v1/wallets/{publicKey}/history")
//    Call<NftListInfo> getGeneratedNftList(@RequestBody HistoryRequest historyRequest, @PathVariable String publicKey);


}
