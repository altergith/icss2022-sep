package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Generator {

    public String generate(AST ast) {
        return generateStylesheet((Stylesheet) ast.root);
    }

    private String generateStylesheet(Stylesheet node) {
        String result = "";
        for (ASTNode child : node.getChildren()
        ) {
            if (child instanceof Stylerule) {
                result += generateStyleRule((Stylerule) child);
                result += "\n";
            }
        }
        return result;
    }

    private String generateStyleRule(Stylerule stylerule) {
        String result = "";
        result += stylerule.selectors.get(0) + " {\n ";
        for (ASTNode child : stylerule.body
        ) {
            result += "\t";
            result += generateDecleration((Declaration) child);
            result += "\n";
        }
        result += "}";
        return result;
    }

    private String generateDecleration(Declaration node) {
        String result = "";
        if (node.expression instanceof ColorLiteral) {
            ColorLiteral l = (ColorLiteral) node.expression;
            result += node.property.name + ": " + l.value;
        }

        if (node.expression instanceof PixelLiteral) {
            PixelLiteral l = (PixelLiteral) node.expression;
            result += node.property.name + ": " + l.value;
        }

        if (node.expression instanceof PercentageLiteral) {
            PercentageLiteral l = (PercentageLiteral) node.expression;
            result += node.property.name + ": " + l.value;
        }
        return result;
    }


}
