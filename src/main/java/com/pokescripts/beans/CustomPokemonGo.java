package com.pokescripts.beans;

import com.pokescripts.login.AskForTokenPokemonLogin;
import com.pokescripts.login.RefreshTokenPokemonLogin;
import com.pokescripts.utils.FileUtils;
import com.pokegoapi.api.PokemonGo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class CustomPokemonGo {

    private final PokemonGo go;
    private final String refreshToken;

    private static final String REFRESHTOKEN_FILE = "refreshtoken.txt";

    public CustomPokemonGo(PokemonGo go, String refreshToken) {
        this.go = go;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public List<CustomPokemon> getPokemons(){
        return go.getInventories().getPokebank().getPokemons().stream()
                .map(CustomPokemon::new)
                .collect(Collectors.toList());
    }

    public Map<String, List<CustomPokemon>> getPokemonsGroupedByType(){
        return getPokemons().stream()
                .collect(groupingBy(p ->  p.getPokemonType()));
    }

    public static CustomPokemonGo loginAndCreate() throws Exception {
        try {
            String refreshToken = FileUtils.readFileFromFileSystem(REFRESHTOKEN_FILE);
            return new RefreshTokenPokemonLogin(refreshToken).getPokemonGo();
        }catch (Exception e){
            CustomPokemonGo pokemonGo = new AskForTokenPokemonLogin().getPokemonGo();
            FileUtils.writeStringToFile(pokemonGo.getRefreshToken(), REFRESHTOKEN_FILE);
            return pokemonGo;
        }
    }

}
