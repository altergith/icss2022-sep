grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';




//--- PARSER: ---
color: COLOR;
pixelSize: PIXELSIZE;
percentage: PERCENTAGE; // schrijf nog een test voor percentage
bool: (TRUE | FALSE);
variableReference: CAPITAL_IDENT;

propertyName: LOWER_IDENT;
value: (color | pixelSize | percentage | variableReference);

expression
 : expression (MUL) expression #multiplyOperation
 | expression (PLUS|MIN) expression #addOrSubtractOperation
 | SCALAR #scalar
 | value #expressionValue;

variableAssignment: variableReference ASSIGNMENT_OPERATOR (color | pixelSize | bool | percentage | expression) SEMICOLON;

decleration: propertyName COLON (value | expression) SEMICOLON;

elseClause: ELSE OPEN_BRACE ( variableAssignment | decleration | ifClause)+ CLOSE_BRACE;
ifClause: IF BOX_BRACKET_OPEN (variableReference | bool ) BOX_BRACKET_CLOSE OPEN_BRACE ( variableAssignment | decleration | ifClause)+ CLOSE_BRACE elseClause?;

tagSelector: LOWER_IDENT;
idSelector: ID_IDENT;
classSelector: CLASS_IDENT;

stylerule: (tagSelector | idSelector | classSelector) OPEN_BRACE ( variableAssignment | decleration | ifClause)+ CLOSE_BRACE;
stylesheet:  (variableAssignment | stylerule)+ EOF;

// maak een keuze over reassignen: niet voor extra punten
// GEBRUIK de label #pixelSize voor extra enter en exit methode