package fr.enlight.anima.animamagiccards.ui.spellbooks.bo;


import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import fr.enlight.anima.animamagiccards.R;

public enum SpellGradeLevel {
    INITIAL_LEVEL(R.string.initial_grade_title, R.color.initial_grade_color),
    INTERMEDIATE_LEVEL(R.string.intermediate_grade_title, R.color.intermediate_grade_color),
    ADVANCED_LEVEL(R.string.advanced_grade_title, R.color.advanced_grade_color),
    ARCANE_LEVEL(R.string.arcane_grade_title, R.color.arcane_grade_color);

    public final @StringRes int gradeTitleRes;
    public final @ColorRes int gradeColorRes;

    SpellGradeLevel(int gradeTitle, int gradeTextColor) {
        this.gradeTitleRes = gradeTitle;
        this.gradeColorRes = gradeTextColor;
    }
}
