package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.Node;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        variableValues = new HANLinkedList<>();

    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();
        applyStyleSheet((Stylesheet) ast.root);

    }

    private void applyStyleSheet(Stylesheet node) {
        for (ASTNode child:node.getChildren()
             ) {
            applyStyleRule((Stylerule) child);
        }
    }

    private void applyStyleRule(Stylerule node) {
        for (ASTNode child : node.getChildren()
        ) {
            if (child instanceof Declaration) {
                applyDeclaration((Declaration) child);
            }

        }
    }

    private void applyDeclaration(Declaration node) {
        node.expression = evalExpression(node.expression);
    }

    private Expression evalExpression(Expression expression) {
        if (expression instanceof Operation) {
            if (expression instanceof AddOperation) {
                expression = evalAdd((AddOperation) expression);
            }
            if (expression instanceof SubtractOperation) {
                expression = evalSubtract((SubtractOperation) expression);
            }
            if (expression instanceof MultiplyOperation) {
                expression = evalMul((MultiplyOperation) expression);
            }
        }
        return expression;
    }

    private Literal evalMul(MultiplyOperation node) {
        Literal lhs = (node.lhs instanceof Operation) ? evalOperation((Operation) node.lhs) : (Literal) node.lhs;
        Literal rhs = (node.rhs instanceof Operation) ? evalOperation((Operation) node.rhs) : (Literal) node.rhs;

        if (lhs instanceof ScalarLiteral && rhs instanceof ScalarLiteral) {
            return new ScalarLiteral(((ScalarLiteral) lhs).value * ((ScalarLiteral) rhs).value);
        }

        if (lhs instanceof PixelLiteral && rhs instanceof ScalarLiteral) {
            return new PixelLiteral(((PixelLiteral) lhs).value * ((ScalarLiteral) rhs).value);
        }

        if (lhs instanceof ScalarLiteral && rhs instanceof PixelLiteral) {
            return new PixelLiteral(((ScalarLiteral) lhs).value * ((PixelLiteral) rhs).value);
        }

        if (lhs instanceof PercentageLiteral && rhs instanceof ScalarLiteral) {
            return new ScalarLiteral(((PercentageLiteral) lhs).value * ((ScalarLiteral) rhs).value);
        }

        if (lhs instanceof ScalarLiteral && rhs instanceof PercentageLiteral) {
            return new ScalarLiteral(((ScalarLiteral) lhs).value * ((PercentageLiteral) rhs).value);
        }

        return null;
    }


    private Literal evalSubtract(SubtractOperation node) {

        Literal lhs = (node.lhs instanceof Operation) ? evalOperation((Operation) node.lhs) : (Literal) node.lhs;
        Literal rhs = (node.rhs instanceof Operation) ? evalOperation((Operation) node.rhs) : (Literal) node.rhs;

        if (lhs instanceof ScalarLiteral && rhs instanceof ScalarLiteral) {
            return new ScalarLiteral(((ScalarLiteral) lhs).value - ((ScalarLiteral) rhs).value);
        }

        if (lhs instanceof PixelLiteral && rhs instanceof PixelLiteral) {
            return new PixelLiteral(((PixelLiteral) lhs).value - ((PixelLiteral) rhs).value);
        }

        if (lhs instanceof PercentageLiteral && rhs instanceof PercentageLiteral) {
            return new PercentageLiteral(((PercentageLiteral) lhs).value - ((PercentageLiteral) rhs).value);
        }

        return null;
    }


    private Literal evalAdd(AddOperation node) {

        Literal lhs = (node.lhs instanceof Operation) ? evalOperation((Operation) node.lhs) : (Literal) node.lhs;
        Literal rhs = (node.rhs instanceof Operation) ? evalOperation((Operation) node.rhs) : (Literal) node.rhs;

        if (lhs instanceof ScalarLiteral && rhs instanceof ScalarLiteral) {
            return new ScalarLiteral(((ScalarLiteral) lhs).value + ((ScalarLiteral) rhs).value);
        }

        if (lhs instanceof PixelLiteral && rhs instanceof PixelLiteral) {
            return new PixelLiteral(((PixelLiteral) lhs).value + ((PixelLiteral) rhs).value);
        }

        if (lhs instanceof PercentageLiteral && rhs instanceof PercentageLiteral) {
            return new PercentageLiteral(((PercentageLiteral) lhs).value + ((PercentageLiteral) rhs).value);
        }
        return null;
    }


    private Literal evalOperation(Operation node) {
        Literal result = null;

        if (node instanceof AddOperation) {
            result = evalAdd((AddOperation) node);
        } else if (node instanceof SubtractOperation) {
            result = evalSubtract((SubtractOperation) node);
        } else if (node instanceof MultiplyOperation) {
            result = evalMul((MultiplyOperation) node);
        }
        if (result != null) {
            return result;
        }
        return result;
    }
}
