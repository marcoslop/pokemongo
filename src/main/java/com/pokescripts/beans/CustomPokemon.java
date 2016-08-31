package com.pokescripts.beans;

import POGOProtos.Networking.Responses.NicknamePokemonResponseOuterClass;
import POGOProtos.Networking.Responses.ReleasePokemonResponseOuterClass;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

public class CustomPokemon {

    Pokemon pokemon;

    public CustomPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public String getPokemonType (){
        String rename = pokemon.getMeta().getTemplateId();
        rename = rename.substring(rename.lastIndexOf("_")+1).toLowerCase();
        rename = rename.substring(0, 1).toUpperCase() + rename.substring(1);
        return rename;
    }

    public double getIvInPercentage(){
        return pokemon.getIvInPercentage();
    }

    public String getNickname(){
        return pokemon.getNickname();
    }

    public double getCP(){
        return pokemon.getCp();
    }

    public ReleasePokemonResponseOuterClass.ReleasePokemonResponse.Result transfer() throws LoginFailedException, RemoteServerException {
        return pokemon.transferPokemon();
    }

    public NicknamePokemonResponseOuterClass.NicknamePokemonResponse.Result rename(String nickName) throws LoginFailedException, RemoteServerException {
        return pokemon.renamePokemon(nickName);
    }

    public NicknamePokemonResponseOuterClass.NicknamePokemonResponse.Result renameWithTypeAndIV() throws LoginFailedException, RemoteServerException {
        return pokemon.renamePokemon(getNickNameWithIV());
    }

    public String getNickNameWithIV(){
        String rename = getPokemonType();
        rename = rename+pokemon.getIvInPercentage();
        rename = rename.substring(0, rename.lastIndexOf("."));
        return rename;
    }

    public boolean isNickNameEqualsToTypeAndIV(){
        return !isNicknameEmpty() && getNickNameWithIV().equals(getNickname());
    }

    public boolean isNicknameEmpty(){
        return getNickname()==null || getNickname().trim().equals("");
    }

    public int getCandys() {
        return pokemon.getCandy();
    }
}
