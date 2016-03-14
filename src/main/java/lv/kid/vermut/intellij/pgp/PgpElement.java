package lv.kid.vermut.intellij.pgp;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PgpElement extends IElementType {
    public static final PgpElement INSTANCE = new PgpElement("PGP_ELEMENT");

    public PgpElement(@NotNull @NonNls String debugName) {
        super(debugName, PgpLanguage.INSTANCE);
    }

}
