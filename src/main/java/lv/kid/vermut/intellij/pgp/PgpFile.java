package lv.kid.vermut.intellij.pgp;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Created by admin on 14/03/16.
 */
public class PgpFile extends PsiFileBase {
    protected PgpFile(@NotNull FileViewProvider fileViewProvider, @NotNull Language language) {
        super(fileViewProvider, language);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return PgpFileType.INSTANCE;
    }
}
