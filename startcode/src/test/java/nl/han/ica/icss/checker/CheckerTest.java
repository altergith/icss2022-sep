package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.parser.Fixtures;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckerTest {

    Checker checker = new Checker();

    @Test
    void testAllErrors() throws IOException {

        List<SemanticError> exp = nl.han.ica.icss.checker.Fixtures.getExprectedErrors();
        AST ast = Fixtures.uncheckedTestAllErrors();
        checker.check(Fixtures.uncheckedTestAllErrors());
        List<SemanticError> sut = ast.getErrors();
        assertEquals(exp,sut);
    }

}
