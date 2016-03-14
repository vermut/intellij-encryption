package lv.kid.vermut.intellij.pgp;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

/**
 * Created by admin on 14/03/16.
 */
public class PgpParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(PgpLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new PgpLexer();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new PgpParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.create(PgpToken.INSTANCE, PgpElement.INSTANCE);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode astNode) {
        return new PgpPsiElement(astNode.getElementType(), "test");
    }

    @Override
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        return new PgpFile(fileViewProvider, PgpLanguage.INSTANCE);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode astNode, ASTNode astNode1) {
        return SpaceRequirements.MUST_LINE_BREAK;
    }
}
