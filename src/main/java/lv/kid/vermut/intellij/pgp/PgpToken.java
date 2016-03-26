package lv.kid.vermut.intellij.pgp;

import com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PgpToken extends ILazyParseableElementType {
    public static final PgpToken INSTANCE = new PgpToken("PGP_TOKEN");

    public PgpToken(@NotNull @NonNls String debugName) {
        super(debugName, PgpLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "PgpTokenType." + super.toString();
    }
}
