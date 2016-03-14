package lv.kid.vermut.intellij.pgp;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PgpFileType extends LanguageFileType {
    public static final PgpFileType INSTANCE = new PgpFileType();

    private PgpFileType() {
        super(PgpLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "PGP";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "PGP encrypted data";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "gpg";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }
}
