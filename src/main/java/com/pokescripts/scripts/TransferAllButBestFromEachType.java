package com.pokescripts.scripts;

import POGOProtos.Networking.Responses.ReleasePokemonResponseOuterClass;
import com.pokescripts.beans.CustomPokemon;
import com.pokescripts.beans.CustomPokemonGo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

/**
 * For every Pokemon Type (Pidgey, Vaporeon, Eevee, etc...) we transfer all pokemons but the one with the higher IV.
 * Also Pokemons with IV higher than 80 are not transferred.
 */
public class TransferAllButBestFromEachType {

    private final CustomPokemonGo go;

    public TransferAllButBestFromEachType(CustomPokemonGo go) {
        this.go = go;
    }

    public void execute(){
        Map<String, List<CustomPokemon>> pokemonsByType = go.getPokemonsGroupedByType();
        for (String pokemonType : pokemonsByType.keySet()) {
            List<CustomPokemon> pokemons = pokemonsByType.get(pokemonType).stream()
                    .sorted(comparing(CustomPokemon::getIvInPercentage).reversed())
                    .collect(Collectors.toList());
            System.out.println(pokemonType+": "+pokemons.size());
            if (pokemons.size() > 1){
                CustomPokemon bestPokemon = pokemons.get(0);
                System.out.println("  Best Pokemon: "+bestPokemon.getNickname());
                for (int i = 1; i < pokemons.size(); i++) {
                    CustomPokemon pokemonToBeTransfered = pokemons.get(i);
                    if (pokemonToBeTransfered.getIvInPercentage() > 80){
                        System.out.println("  Pokemon not transferred because IV > 80: "+pokemonToBeTransfered.getNickname());
                    }else {
                        System.out.println("  Pokemon To be transferred: " + pokemonToBeTransfered.getNickname());
                        try {
                            ReleasePokemonResponseOuterClass.ReleasePokemonResponse.Result result = pokemonToBeTransfered.transfer();
                            System.out.println("  Pokemon transferred: " + pokemonToBeTransfered.getNickname() + " with result " + result);
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
            }else{
                CustomPokemon bestPokemon = pokemons.get(0);
                System.out.println("  Best Pokemon: "+bestPokemon.getNickname());
            }
        }
    }

    public static void main(String args[]){
        try {
            new TransferAllButBestFromEachType(CustomPokemonGo.loginAndCreate())
                    .execute();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

}
