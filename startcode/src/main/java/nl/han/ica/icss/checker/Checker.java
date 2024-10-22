package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
         variableTypes = new HANLinkedList<>();
         checkStylesheet(ast.root);

    }

    private void checkStylesheet(Stylesheet node) {
        for (ASTNode child: node.getChildren()
             ) {
            if(child instanceof Stylerule){
                checkStylerule((Stylerule) child);
            }
        }
    }

    private void checkStylerule(Stylerule node) {
        for (ASTNode child: node.getChildren())
              {
                  if(child instanceof Declaration){
                      checkDeclaration((Declaration) child);
                  }

        }
    }

    private void checkDeclaration(Declaration node) {
        if(node.property.name.equals("width") || node.property.name.equals("height")){
            if(!(node.expression instanceof PixelLiteral) && !(node.expression instanceof PercentageLiteral) && !(node.expression instanceof Operation)){ // pas dit nog aan
                node.expression.setError("Property name '"+ node.property.name + "' got assigned an invalid type: " + node.expression.getClass().getSimpleName());
            }
        }

        if(node.property.name.equals("color")){
            if(!(node.expression instanceof ColorLiteral)){
                node.expression.setError("Property name: 'color' got assigned an invalid type: " + node.expression.getClass().getSimpleName());
            }
        }
    }


}
