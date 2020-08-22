package com.startup.shoppyauto;

public interface ConvertMoneyService {
    @GET("api/live")
    Call<ResponseCurrency> convertVNDtoUSD(@Query("access_key") String access_key,
                                           @Query("currencies") String currencies,
                                           @Query("source") String source,
                                           @Query("format") int format);
}
