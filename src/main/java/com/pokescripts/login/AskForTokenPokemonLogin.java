package com.pokescripts.login;

import com.pokescripts.beans.CustomPokemonGo;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.GoogleUserCredentialProvider;
import okhttp3.OkHttpClient;

import java.util.Scanner;

public class AskForTokenPokemonLogin implements PokemonLogin {

    @Override
    public CustomPokemonGo getPokemonGo() throws Exception{
        OkHttpClient httpClient = new OkHttpClient();
        GoogleUserCredentialProvider provider = new GoogleUserCredentialProvider(httpClient);


        // in this url, you will get a code for the google account that is logged
        System.out.println("Please go to " + GoogleUserCredentialProvider.LOGIN_URL);
        System.out.println("Enter authorization code:");

        // Ask the user to enter it in the standard input
        Scanner sc = new Scanner(System.in);
        String access = sc.nextLine();


        // we should be able to login with this token
        provider.login(access);
        PokemonGo go = new PokemonGo(httpClient);
        go.login(provider);
        String refreshToken = provider.getRefreshToken();
        System.out.println("REFRESH TOKEN: "+refreshToken);
        return new CustomPokemonGo(go, refreshToken);
    }
}
