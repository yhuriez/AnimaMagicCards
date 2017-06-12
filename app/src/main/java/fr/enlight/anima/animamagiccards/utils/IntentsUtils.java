package fr.enlight.anima.animamagiccards.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import de.cketti.mailto.EmailIntentBuilder;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.Spell;

public class IntentsUtils {

    public static Intent navigateToSendBugMail(Context context){
        return EmailIntentBuilder.from(context)
                .to(context.getString(R.string.Mail_TMA))
                .subject(context.getString(R.string.Notify_Bug))
                .body(context.getString(R.string.Notify_Bug_Body))
                .build();
    }

    public static Intent navigateToSendMisspellingMail(Context context){
        String body = context.getString(R.string.Notify_Misspelling_Body_Reduced);

        return EmailIntentBuilder.from(context)
                .to(context.getString(R.string.Mail_TMA))
                .subject(context.getString(R.string.Notify_Misspelling))
                .body(body)
                .build();
    }

    public static Intent navigateToSendMisspellingMail(Context context, @NonNull Spell spell){
        String subject = context.getString(R.string.Notify_Misspelling_Spell_subject, spell.name);
        String body = context.getString(R.string.Notify_Misspelling_Body, spell.effect);

        return EmailIntentBuilder.from(context)
                .to(context.getString(R.string.Mail_TMA))
                .subject(subject)
                .body(body)
                .build();
    }

}
