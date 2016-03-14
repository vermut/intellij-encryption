package lv.kid.vermut.intellij.pgp;

import com.intellij.indentation.AbstractIndentParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

/**
 * Created by admin on 14/03/16.
 */
public class PgpParser extends AbstractIndentParser {

    @Override
    protected void parseRoot(IElementType root) {
        PsiBuilder.Marker wholeFile = mark(true);
        while (!eof()) {
            advance();
        }
        done(wholeFile, PgpElement.INSTANCE);
    }

    @Override
    protected IElementType getIndentElementType() {
        return TokenType.WHITE_SPACE;
    }

    @Override
    protected IElementType getEolElementType() {
        return TokenType.WHITE_SPACE;
    }
}
