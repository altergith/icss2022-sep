package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;


public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        HashMap<String, ExpressionType> scope = new HashMap<>();
        variableTypes.insert(0, scope);
        for (ASTNode node : ast.root.getChildren()
             ) {
            if (node instanceof VariableAssignment) {
                variableTypes.get(0).put(((VariableAssignment) node).name.name, getExpressionType(((VariableAssignment) node).expression));
            }
        }
        checkStylesheet(ast.root);
    }

    private static ExpressionType getExpressionType(Expression expression) {
        if (expression instanceof PixelLiteral) {
            return ExpressionType.PIXEL;
        } else if (expression instanceof PercentageLiteral) {
            return ExpressionType.PERCENTAGE;
        } else if (expression instanceof ColorLiteral) {
            return ExpressionType.COLOR;
        } else {
            return ExpressionType.UNDEFINED;
        }
    }

    private void checkStylesheet(Stylesheet node) {
        for (ASTNode child : node.getChildren()
        ) {
            if (child instanceof Stylerule) {
                checkStylerule((Stylerule) child);
            }
        }
    }

    private void checkStylerule(Stylerule node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }

        }
    }

    private void checkDeclaration(Declaration node) {


        if (node.property.name.equals("width") || node.property.name.equals("height")) {

//            if(node.expression instanceof Operation){
//                checkExpression(((Operation) node.expression).rhs);
//                checkExpression(((Operation) node.expression).lhs);
//            }

            if(node.expression instanceof VariableReference) {
                checkVariableReferenceOnSizeExpressionTypes(node);
            } else if (!(node.expression instanceof PixelLiteral) && !(node.expression instanceof PercentageLiteral) && !(node.expression instanceof Operation)) { // pas dit nog aan
                node.expression.setError("Property name '" + node.property.name + "' got assigned an invalid type: " + node.expression.getClass().getSimpleName());
            }
        }

        if (node.property.name.equals("color")) {
            if (node.expression instanceof VariableReference) {
                checkVariableReferenceOnExpressionType(node, ExpressionType.COLOR);
            } else if (!(node.expression instanceof ColorLiteral)) {
                node.expression.setError("Property name: '" + node.property.name + "' got assigned an invalid type: " + node.expression.getClass().getSimpleName());

            }
        }
    }


    private void checkVariableReferenceOnSizeExpressionTypes(Declaration node) {
        if ((variableTypes.getSize() > 0)) {
            for (int i = 0; i < variableTypes.getSize(); i++) {
                if (variableTypes.get(i).containsKey(((VariableReference) node.expression).name)) {
                    ExpressionType type = variableTypes.get(i).get(((VariableReference) node.expression).name);
                    if (type != ExpressionType.PIXEL && type != ExpressionType.PERCENTAGE) {
                        node.expression.setError("Property '" + node.property.name + "' with Variable reference: '" + ((VariableReference) node.expression).name + "' got assigned an invalid type: " + type);
                    }
                } else {
                    node.expression.setError("Variable reference: '" + ((VariableReference) node.expression).name + "' is not defined");
                }
            }
        } else {
            node.expression.setError("Variable reference: '" + ((VariableReference) node.expression).name + "' is not defined");
        }
    }


    private void checkVariableReferenceOnExpressionType(Declaration node, ExpressionType type) {
            if ((variableTypes.getSize() > 0)) {
                for (int i = 0; i < variableTypes.getSize(); i++) {
                    if (variableTypes.get(i).containsKey(((VariableReference) node.expression).name)) {
                        if (variableTypes.get(i).get(((VariableReference) node.expression).name) != type) {
                            node.expression.setError("Property '" + node.property.name + "' with Variable reference: '" + ((VariableReference) node.expression).name + "' got assigned an invalid type: " + variableTypes.get(i).get(((VariableReference) node.expression).name));
                        }
                    } else {
                        node.expression.setError("Variable reference: '" + ((VariableReference) node.expression).name + "' is not defined");
                    }
                }
            } else {
                node.expression.setError("Variable reference: '" + ((VariableReference) node.expression).name + "' is not defined");
            }
    }


}
