package in.imagineer.nwrq;

import java.util.ArrayList;
import java.util.List;


public class PokemonModel {
    public static class ResourceNameUrl {
        public final String name;
        public final String url;

        public ResourceNameUrl(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public static class CharacteristicDesc {
        public final String description;
        public final in.imagineer.nwrq.PokemonModel.ResourceNameUrl language;

        public CharacteristicDesc(String description, in.imagineer.nwrq.PokemonModel.ResourceNameUrl language) {
            this.description = description;
            this.language = language;
        }
    }

    public static class Characteristic {
        public final int id;
        public final int gene_modulo;
        public final List<Integer> possible_values;
        public final in.imagineer.nwrq.PokemonModel.ResourceNameUrl highest_stat;
        public List<in.imagineer.nwrq.PokemonModel.CharacteristicDesc> descriptions = new ArrayList<in.imagineer.nwrq.PokemonModel.CharacteristicDesc>();

        public Characteristic(
                int id, int gene_modulo, List<Integer> possible_values,
                in.imagineer.nwrq.PokemonModel.ResourceNameUrl highest_stat, List<in.imagineer.nwrq.PokemonModel.CharacteristicDesc> descriptions
        ) {
            this.id = id;
            this.gene_modulo = gene_modulo;
            this.possible_values = possible_values;
            this.highest_stat = highest_stat;
            this.descriptions = descriptions;
        }
    }
}