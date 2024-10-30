package nl.han.ica.icss.checker;

import java.util.List;

public class Fixtures {
    public static List<SemanticError> getExprectedErrors() {
        return List.of(
                new SemanticError("ERROR: Variable assignment got assigned with invalid type: SCALAR"),
                new SemanticError("ERROR: Variable assignment got assigned with invalid type: SCALAR"),
                new SemanticError("ERROR: Variable assignment got assigned with invalid type: SCALAR"),
                new SemanticError("ERROR: Multiply operation got assigned with invalid types: PIXEL and PIXEL"),
                new SemanticError("ERROR: Property name 'width' got assigned an invalid OPERATION or VARIABLE REFERENCE"),
                new SemanticError("ERROR: Add operation got assigned with invalid types: UNDEFINED and PIXEL"),
                new SemanticError("ERROR: Variable reference: 'Idk' is not defined"),
                new SemanticError("ERROR: Property name 'height' got assigned an invalid OPERATION or VARIABLE REFERENCE"),
                new SemanticError("ERROR: Add operation got assigned with invalid types: UNDEFINED and SCALAR"),
                new SemanticError("ERROR: Multiply operation got assigned with invalid types: PIXEL and PIXEL"),
                new SemanticError("ERROR: Property name 'width' got assigned an invalid OPERATION or VARIABLE REFERENCE"),
                new SemanticError("ERROR: Add operation got assigned with invalid types: PIXEL and COLOR"),
                new SemanticError("ERROR: Property name 'height' got assigned an invalid type: SCALAR"),
                new SemanticError("ERROR: Property name 'width' got assigned an invalid type: COLOR"),
                new SemanticError("ERROR: Property name 'width' got assigned an invalid type: COLOR"),
                new SemanticError("ERROR: Property name: 'color' got assigned an invalid type: PIXEL"),
                new SemanticError("ERROR: Property name: 'color' got assigned an invalid type: PERCENTAGE"),
                new SemanticError("ERROR: Property name: 'color' got assigned an invalid type: PIXEL"),
                new SemanticError("ERROR: Property name: 'color' got assigned an invalid type: PERCENTAGE"),
                new SemanticError("ERROR: Property with Variable reference: 'V' got assigned an invalid type: COLOR"),
                new SemanticError("ERROR: Property with Variable reference: 'P' got assigned an invalid type: PERCENTAGE"),
                new SemanticError("ERROR: Property name 'background-color' got assigned an invalid OPERATION or VARIABLE REFERENCE"),
                new SemanticError("ERROR: Variable reference: 'Width' is not defined")
        );
    }
}
