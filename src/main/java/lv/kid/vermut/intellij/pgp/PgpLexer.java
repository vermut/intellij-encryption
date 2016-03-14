package lv.kid.vermut.intellij.pgp;


import com.intellij.lexer.DummyLexer;

/**
 * Created by admin on 14/03/16.
 */
public class PgpLexer extends DummyLexer {
    public PgpLexer() {
        super(PgpToken.INSTANCE);
    }
}
