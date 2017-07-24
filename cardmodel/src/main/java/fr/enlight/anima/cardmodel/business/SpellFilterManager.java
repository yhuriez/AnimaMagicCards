package fr.enlight.anima.cardmodel.business;


import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.cardmodel.model.spells.Spell;

public class SpellFilterManager {

    private final List<SpellFilterFactory.SpellFilter> filters;
    private final SpellFilterFactory.SpellFilter quickAccessFilter;

    public SpellFilterManager(List<SpellFilterFactory.SpellFilter> filters, SpellFilterFactory.SpellFilter quickAccessFilter) {
        this.filters = filters;
        this.quickAccessFilter = quickAccessFilter;
    }


    public List<Spell> filterSpells(List<Spell> spells) {
        if ((filters == null || filters.isEmpty()) && quickAccessFilter == null) {
            return spells;
        }

        List<Spell> result = new ArrayList<>();
        for (Spell spell : spells) {
            boolean matching = false;

            // Quick access filter
            if (quickAccessFilter != null) {
                if (quickAccessFilter.matchFilter(spell) && isSpellMatchingFiltered(spell, filters)) {
                    matching = true;
                }

                // Custom filters only
            } else if (isSpellMatchingFiltered(spell, filters)) {
                matching = true;
            }

            if (matching) {
                result.add(spell);
            }
        }

        return result;
    }

    public boolean isSpellMatchingFiltered(Spell spell, List<SpellFilterFactory.SpellFilter> filters) {
        if (filters != null && !filters.isEmpty()) {
            for (SpellFilterFactory.SpellFilter filter : filters) {
                if (!filter.matchFilter(spell)) {
                    return false;
                }
                filter.updateSpellWithFilter(spell);
            }
        }
        return true;
    }
}
