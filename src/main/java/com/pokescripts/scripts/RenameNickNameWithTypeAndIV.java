package com.pokescripts.scripts;

import POGOProtos.Networking.Responses.NicknamePokemonResponseOuterClass;
import com.pokescripts.beans.CustomPokemonGo;

/**
 * Renames all pokemons to follow this format: <POKEMON><IV>.
 * For example: Vaporeon75
 *
 */
public class RenameNickNameWithTypeAndIV {

    private final CustomPokemonGo go;

    public RenameNickNameWithTypeAndIV(CustomPokemonGo go) {
        this.go = go;
    }

    public void execute(){
        go.getPokemons().stream()
            .filter(p -> !p.isNickNameEqualsToTypeAndIV())
            .forEach(pokemon -> {
                try {
                    NicknamePokemonResponseOuterClass.NicknamePokemonResponse.Result result = pokemon.renameWithTypeAndIV();
                    System.out.println("Renamed to "+pokemon.getNickNameWithIV()+" with result "+result);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
    }

    public static void main(String args[]){
        try {
            new RenameNickNameWithTypeAndIV(CustomPokemonGo.loginAndCreate())
                    .execute();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

}
