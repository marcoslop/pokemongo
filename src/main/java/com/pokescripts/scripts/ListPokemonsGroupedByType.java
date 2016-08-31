package com.pokescripts.scripts;

import com.pokescripts.beans.CustomPokemon;
import com.pokescripts.beans.CustomPokemonGo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Script that shows all pokemons grouped by type.
 */
public class ListPokemonsGroupedByType {

    private final CustomPokemonGo go;

    public ListPokemonsGroupedByType(CustomPokemonGo go) {
        this.go = go;
    }

    public void execute(){
        Map<String, List<CustomPokemon>> pokemonsByType = go.getPokemonsGroupedByType();
        for (String pokemonType : pokemonsByType.keySet()) {
            List<CustomPokemon> pokemons = pokemonsByType.get(pokemonType).stream()
                    .sorted(comparing(CustomPokemon::getIvInPercentage).reversed())
                    .collect(Collectors.toList());
            System.out.println(pokemonType+": "+pokemons.get(0).getCandys()+" candies");
            for (CustomPokemon pokemon : pokemons) {
                String nickName;
                if (pokemon.isNicknameEmpty()){
                    nickName = "EMPTY";
                }else{
                    nickName = pokemon.getNickname();
                }
                System.out.println("   "+nickName+", CP: "+pokemon.getCP()+", IV: "+pokemon.getIvInPercentage());
            }
        }
    }

    public static void main(String args[]){
        try {
            new ListPokemonsGroupedByType(CustomPokemonGo.loginAndCreate())
                    .execute();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

}
