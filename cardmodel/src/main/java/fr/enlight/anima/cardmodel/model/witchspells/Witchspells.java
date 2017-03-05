package fr.enlight.anima.cardmodel.model.witchspells;

import android.util.Pair;

import java.util.Date;
import java.util.List;

public class Witchspells {

    public int witchspellsId;
    public Date creationDate;

    public List<WitchspellsPath> witchPaths;

    // First item is bookId, second is spellId
    public List<Pair<Integer, Integer>> witchChoosenSpellsIds;
}
