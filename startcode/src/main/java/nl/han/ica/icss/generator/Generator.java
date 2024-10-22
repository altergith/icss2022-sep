package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;

import java.util.ArrayList;

public class Generator {

	public String generate(AST ast) {
        return generateStylesheet((Stylesheet)ast.root);


	}

    private String generateStylesheet(Stylesheet node) {
        return generateStyleRule((Stylerule) node.getChildren().get(0));
    }

    private String generateStyleRule(Stylerule stylerule) {
        String result = stylerule.selectors.get(0) + " {\n ";
        result += "\t" + generateDecleration((Declaration)stylerule.body.get(0));
        result += "\n}";
        return result;
    }

    private String generateDecleration(Declaration node) {
        return "decleration";
    }


}
