package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;


public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        HashMap<String, ExpressionType> scope = new HashMap<>();
        variableTypes.addFirst(scope);
        checkStylesheet(ast.root);
        variableTypes.removeFirst();
    }

    private static ExpressionType getExpressionType(Expression expression) {
        if (expression instanceof PixelLiteral) {
            return ExpressionType.PIXEL;
        } else if (expression instanceof PercentageLiteral) {
            return ExpressionType.PERCENTAGE;
        } else if (expression instanceof ColorLiteral) {
            return ExpressionType.COLOR;
        } else if (expression instanceof BoolLiteral) {
            return ExpressionType.BOOL;
        } else if(expression instanceof ScalarLiteral){
            return ExpressionType.SCALAR; // deal with operations
        } else {
            return ExpressionType.UNDEFINED;
        }
    }

    private void checkStylesheet(Stylesheet node) {
        for (ASTNode child : node.getChildren()
        ) {
            if (child instanceof VariableAssignment) {
                variableTypes.getFirst().put(((VariableAssignment) child).name.name, getExpressionType(((VariableAssignment) child).expression));
            }
            if (child instanceof Stylerule) {
                checkStyleRule((Stylerule) child);
            }
        }
    }

    private void checkStyleRule(Stylerule node) {
        HashMap<String, ExpressionType> styleRuleScope = new HashMap<>();
        variableTypes.addFirst(styleRuleScope);
        for (ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment){
                styleRuleScope.put(((VariableAssignment) child).name.name, getExpressionType(((VariableAssignment) child).expression));
            }
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }
            if (child instanceof IfClause){
                checkIfClause((IfClause) child);
            }
        }
        variableTypes.removeFirst();
    }

    private void checkIfClause(IfClause child) {
        checkCondition(child.conditionalExpression);
        HashMap<String, ExpressionType> ifClauseScope = new HashMap<>();
        variableTypes.addFirst(ifClauseScope);

        for (ASTNode node : child.body
             ) {
            if (node instanceof VariableAssignment){
                ifClauseScope.put(((VariableAssignment) node).name.name, getExpressionType(((VariableAssignment) node).expression));
            }

            if (node instanceof Declaration) {
                checkDeclaration((Declaration) node);
            }

            if (node instanceof IfClause){
                checkIfClause((IfClause) node);
            }
        }
        variableTypes.removeFirst();
    }

    private void checkCondition(ASTNode node) {
        if (node instanceof VariableReference) {
            checkVariableReferenceOnExpressionType((VariableReference) node, ExpressionType.BOOL);
        } else if (!(node instanceof BoolLiteral)) {
            node.setError("Condition got assigned an invalid type: " + node.getClass().getSimpleName());
        }
    }

    private void checkDeclaration(Declaration node) {
        if (node.property.name.equals("width") || node.property.name.equals("height")) {
            ExpressionType nodeType;
            if(node.expression instanceof Operation){
               nodeType = checkExpression(((Operation) node.expression));
            } else {
                nodeType = getExpressionType(node.expression);
            }

            if(node.expression instanceof VariableReference) {
                checkVariableReferenceOnSizeExpressionTypes(node); // check the expressiontype of the operation
            } else if (!(nodeType == ExpressionType.PERCENTAGE) && !(nodeType == ExpressionType.PIXEL) && !(node.expression instanceof Operation)) { // moet ik nu scalars toestaan?
                node.expression.setError("Property name '" + node.property.name + "' got assigned an invalid type: " + node.expression.getClass().getSimpleName());
            }
        }

        if (node.property.name.equals("color") || node.property.name.equals("background-color")) {
            if (node.expression instanceof VariableReference) {
                checkVariableReferenceOnExpressionType((VariableReference) node.expression, ExpressionType.COLOR);
            } else if (!(node.expression instanceof ColorLiteral)) {
                node.expression.setError("Property name: '" + node.property.name + "' got assigned an invalid type: " + node.expression.getClass().getSimpleName());
            }
        }
    }

    private ExpressionType checkExpression(Operation handSide) {
        ExpressionType type = ExpressionType.UNDEFINED;

        if(handSide instanceof AddOperation){
           type = checkAddOperation((AddOperation) handSide);
        } else if (handSide instanceof SubtractOperation){
           type = checkSubtractOperation((SubtractOperation) handSide);
        } else if (handSide instanceof MultiplyOperation){
           type = checkMultiplyOperation((MultiplyOperation) handSide);
        }
        return type;
    }



    private ExpressionType checkMultiplyOperation(MultiplyOperation handSide) {

        ExpressionType lhs = getLhsExpressionType(handSide);
        ExpressionType rhs = getRhsExpressionType(handSide);

        if(((lhs == ExpressionType.PIXEL || lhs == ExpressionType.PERCENTAGE) && rhs == ExpressionType.SCALAR) || ((rhs == ExpressionType.PIXEL|| rhs == ExpressionType.PERCENTAGE) && lhs == ExpressionType.SCALAR) || (lhs == ExpressionType.SCALAR && rhs == ExpressionType.SCALAR)){
            if(rhs == ExpressionType.SCALAR){
                return lhs;
            } else {
                return rhs;
            }
        }
        handSide.setError("Multiply operation got assigned with invalid types: " + lhs + " and " + rhs);
        return ExpressionType.UNDEFINED;
    }

    private ExpressionType getRhsExpressionType(Operation handSide) {
        ExpressionType rhs;
        if(handSide.rhs instanceof VariableReference){
           rhs = getVariableReferenceExpressionType((VariableReference) handSide.rhs);
        } else {
            rhs = (handSide.rhs instanceof Operation) ? checkExpression((Operation) handSide.rhs) :  getExpressionType(handSide.rhs);
        }
        return rhs;
    }

    private ExpressionType getLhsExpressionType(Operation handSide) {
        ExpressionType lhs;
        if(handSide.lhs instanceof VariableReference){
            lhs = getVariableReferenceExpressionType((VariableReference) handSide.lhs);
        } else{
            lhs = (handSide.lhs instanceof Operation) ? checkExpression((Operation) handSide.lhs) :  getExpressionType(handSide.lhs);
        }
        return lhs;
    }

    private ExpressionType checkSubtractOperation(SubtractOperation handSide) {

        ExpressionType lhs = getLhsExpressionType(handSide);
        ExpressionType rhs = getRhsExpressionType(handSide);

        if(lhs == ExpressionType.PIXEL && rhs == ExpressionType.PIXEL || lhs == ExpressionType.PERCENTAGE && rhs == ExpressionType.PERCENTAGE){
            return lhs;
        }
        handSide.setError("Subtract operation got assigned with invalid types: " + lhs + " and " + rhs);
        return ExpressionType.UNDEFINED;
    }

    private ExpressionType checkAddOperation(AddOperation handSide) {

        ExpressionType lhs = getLhsExpressionType(handSide);
        ExpressionType rhs = getRhsExpressionType(handSide);

        if(lhs == ExpressionType.PIXEL && rhs == ExpressionType.PIXEL || lhs == ExpressionType.PERCENTAGE && rhs == ExpressionType.PERCENTAGE){
            return lhs;
        }
        handSide.setError("Add operation got assigned with invalid types: " + lhs + " and " + rhs);
        return ExpressionType.UNDEFINED;
    }

    private ExpressionType getVariableReferenceExpressionType(VariableReference node) {
        if (variableTypes.getSize() > 0) {
            for (int i = 0; i < variableTypes.getSize(); i++) {
                if (variableTypes.get(i).containsKey(node.name)) {
                    return variableTypes.get(i).get(node.name);
                }
            }
        }
        node.setError("Variable reference: '" + node.name + "' is not defined");
        return ExpressionType.UNDEFINED;
    }

    private void checkVariableReferenceOnSizeExpressionTypes(Declaration node) {
        boolean isInScope = false;
        if ((variableTypes.getSize() > 0)) {
            for (int i = 0; i < variableTypes.getSize(); i++) {
                if (variableTypes.get(i).containsKey(((VariableReference) node.expression).name)) {
                    isInScope = true;
                    ExpressionType type = variableTypes.get(i).get(((VariableReference) node.expression).name);
                    if (type != ExpressionType.PIXEL && type != ExpressionType.PERCENTAGE) {
                        node.expression.setError("Property '" + node.property.name + "' with Variable reference: '" + ((VariableReference) node.expression).name + "' got assigned an invalid type: " + type);
                    }
                }
            }
            if (!isInScope) {
                node.expression.setError("Variable reference: '" + ((VariableReference) node.expression).name + "' is not defined");
            }
        } else {
            node.expression.setError("Variable reference: '" + ((VariableReference) node.expression).name + "' is not defined");
        }
    }


    private void checkVariableReferenceOnExpressionType(VariableReference node, ExpressionType type) {
        boolean isInScope = false;
            if ((variableTypes.getSize() > 0)) {
                for (int i = 0; i < variableTypes.getSize(); i++) {
                    if (variableTypes.get(i).containsKey((node).name)) {
                        isInScope = true;
                        if (variableTypes.get(i).get((node).name) != type) {
                            node.setError("Property with Variable reference: '" + node.name + "' got assigned an invalid type: " + variableTypes.get(i).get(node.name));
                        }
                    }
                }
                if (!isInScope) {
                    node.setError("Variable reference: '" + node.name + "' is not defined");
                }
            } else {
                node.setError("Variable reference: '" + node.name + "' is not defined");
            }
    }
}
