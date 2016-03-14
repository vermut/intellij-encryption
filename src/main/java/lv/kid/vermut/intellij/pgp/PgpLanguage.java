package lv.kid.vermut.intellij.pgp;

import com.intellij.lang.Language;

/**
 * Created by admin on 14/03/16.
 */
public class PgpLanguage extends Language {
    public static final PgpLanguage INSTANCE = new PgpLanguage();

    private PgpLanguage() {
        super("PGP");
    }
}
