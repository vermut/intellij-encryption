package lv.kid.vermut.intellij.pgp;

import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class PgpPsiElement extends LeafPsiElement {
    public PgpPsiElement(@NotNull IElementType iElementType, CharSequence charSequence) {
        super(iElementType, charSequence);
    }
}
