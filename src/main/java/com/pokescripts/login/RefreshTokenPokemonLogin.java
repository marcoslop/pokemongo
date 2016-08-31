package com.pokescripts.login;

import com.pokescripts.beans.CustomPokemonGo;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.GoogleUserCredentialProvider;
import okhttp3.OkHttpClient;

public class RefreshTokenPokemonLogin implements PokemonLogin {

    private String token;

    public RefreshTokenPokemonLogin(String token) {
        this.token = token;
    }

    @Override
    public CustomPokemonGo getPokemonGo() throws Exception {
        OkHttpClient httpClient = new OkHttpClient();
        PokemonGo go = new PokemonGo(httpClient);
        go.login(new GoogleUserCredentialProvider(httpClient, token));
        return new CustomPokemonGo(go, token);
    }
}
