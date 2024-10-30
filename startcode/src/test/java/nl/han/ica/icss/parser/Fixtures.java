package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.checker.SemanticError;

import java.util.List;

public class Fixtures {

    public static AST uncheckedLevel0() {
        Stylesheet stylesheet = new Stylesheet();
		/*
		p {
			background-color: #ffffff;
			width: 500px;
		}
		*/
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new Declaration("width"))
                        .addChild(new PixelLiteral("500px")))
        );
		/*
		a {
			color: #ff0000;
		}
		*/
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("a"))
                .addChild((new Declaration("color"))
                        .addChild(new ColorLiteral("#ff0000")))
        );
		/*
		#menu {
			width: 520px;
		}
		*/
        stylesheet.addChild((new Stylerule())
                .addChild(new IdSelector("#menu"))
                .addChild((new Declaration("width"))
                        .addChild(new PixelLiteral("520px")))
        );
		/*
		.menu {
			color: #000000;
		}
		*/
        stylesheet.addChild((new Stylerule())
                .addChild(new ClassSelector(".menu"))
                .addChild((new Declaration("color"))
                        .addChild(new ColorLiteral("#000000")))
        );

        return new AST(stylesheet);
    }

    public static AST uncheckedLevel1() {
        Stylesheet stylesheet = new Stylesheet();
		/*
			LinkColor := #ff0000;
			ParWidth := 500px;
			AdjustColor := TRUE;
			UseLinkColor := FALSE;
		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("500px"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("UseLinkColor"))
                .addChild(new BoolLiteral(false))
        );
   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("ParWidth")))
        );
        /*
        a {
	        color: LinkColor;
        }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("a"))
                .addChild((new Declaration("color"))
                        .addChild(new VariableReference("LinkColor")))
        );
        /*
            #menu {
	            width: 520px;
            }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new IdSelector("#menu"))
                .addChild((new Declaration("width"))
                        .addChild(new PixelLiteral("520px")))
        );
        /*
            .menu {
	            color: #000000;
            }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new ClassSelector(".menu"))
                .addChild((new Declaration("color"))
                        .addChild(new ColorLiteral("#000000")))
        );
        return new AST(stylesheet);
    }

    public static AST uncheckedLevel2() {
        Stylesheet stylesheet = new Stylesheet();
		/*
			LinkColor := #ff0000;
			ParWidth := 500px;
			AdjustColor := TRUE;
			UseLinkColor := FALSE;
		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("500px"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("UseLinkColor"))
                .addChild(new BoolLiteral(false))
        );
   	    /*
	        p {
	        background-color: #ffffff;
	        width: ParWidth;
            }
	    */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("ParWidth")))
        );
        /*
        a {
	        color: LinkColor;
        }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("a"))
                .addChild((new Declaration("color"))
                        .addChild(new VariableReference("LinkColor")))
        );
        /*
            #menu {
        	width: ParWidth + 2 * 10px;
            }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new IdSelector("#menu"))
                .addChild((new Declaration("width"))
                        .addChild((new AddOperation())
                                .addChild(new VariableReference("ParWidth"))
                                .addChild((new MultiplyOperation())
                                        .addChild(new ScalarLiteral("2"))
                                        .addChild(new PixelLiteral("10px"))

                                ))));
        /*
            .menu {
	            color: #000000;
            }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new ClassSelector(".menu"))
                .addChild((new Declaration("color"))
                        .addChild(new ColorLiteral("#000000")))
        );
        return new AST(stylesheet);
    }

    public static AST uncheckedLevel3() {
        Stylesheet stylesheet = new Stylesheet();
		/*
			LinkColor := #ff0000;
			ParWidth := 500px;
			AdjustColor := TRUE;
			UseLinkColor := FALSE;
		 */
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("500px"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("UseLinkColor"))
                .addChild(new BoolLiteral(false))
        );
   	    /*
	        p {
				background-color: #ffffff;
				width: ParWidth;
				if[AdjustColor] {
	    			color: #124532;
	    			if[UseLinkColor]{
	        			bg-color: LinkColor;
	    			}
				}
			}
			p {
				background-color: #ffffff;
				width: ParWidth;
				if[AdjustColor] {
	    			color: #124532;
	    		if[UseLinkColor]{
	        		background-color: LinkColor;
	    		} else {
	        		background-color: #000000;
	    		}
	    		height: 20px;
			}
}
	    */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("ParWidth")))
                .addChild((new IfClause())
                        .addChild(new VariableReference("AdjustColor"))
                        .addChild((new Declaration("color")
                                .addChild(new ColorLiteral("#124532"))))
                        .addChild((new IfClause())
                                .addChild(new VariableReference("UseLinkColor"))
                                .addChild(new Declaration("background-color").addChild(new VariableReference("LinkColor")))
                                .addChild((new ElseClause())
                                        .addChild(new Declaration("background-color").addChild(new ColorLiteral("#000000")))

                                )
                        ))
                .addChild((new Declaration("height"))
                        .addChild(new PixelLiteral("20px")))
        );
        /*
        a {
	        color: LinkColor;
        }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("a"))
                .addChild((new Declaration("color"))
                        .addChild(new VariableReference("LinkColor"))
                )
        );
        /*
            #menu {
        	width: ParWidth + 20px;
            }
        */
        stylesheet.addChild((new Stylerule())
                .addChild(new IdSelector("#menu"))
                .addChild((new Declaration("width"))
                        .addChild((new AddOperation())
                                .addChild(new VariableReference("ParWidth"))
                                .addChild(new PixelLiteral("20px"))
                        )
                )
        );
        /*


         .menu {
				color: #000000;
    			background-color: LinkColor;

			}

        */
        stylesheet.addChild((new Stylerule())
                .addChild(new ClassSelector(".menu"))

                .addChild((new Declaration("color"))
                        .addChild(new ColorLiteral("#000000"))
                )
                .addChild((new Declaration("background-color"))
                        .addChild(new VariableReference("LinkColor"))
                )

        );

        return new AST(stylesheet);
    }

    public static AST uncheckedTestElseIf() {
        Stylesheet stylesheet = new Stylesheet();

        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("LinkColor"))
                .addChild(new ColorLiteral("#ff0000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("ParWidth"))
                .addChild(new PixelLiteral("500px"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("UseLinkColor"))
                .addChild(new BoolLiteral(false))
        );

        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("background-color"))
                        .addChild(new ColorLiteral("#ffffff")))
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("ParWidth")))
                .addChild((new IfClause())
                        .addChild(new VariableReference("AdjustColor"))
                        .addChild((new Declaration("color")
                                .addChild(new ColorLiteral("#124532"))))
                        .addChild((new IfClause())
                                .addChild(new VariableReference("UseLinkColor"))
                                .addChild(new Declaration("background-color").addChild(new VariableReference("LinkColor")))
                                .addChild((new ElseClause())
                                        .addChild(new IfClause())
                                        .addChild(new VariableReference("UseLinkColor"))
                                        .addChild(new Declaration("background-color").addChild(new ColorLiteral("#000000")))
                                )
                        ))
                .addChild((new Declaration("height"))
                        .addChild(new PixelLiteral("20px")))
        );
        return new AST(stylesheet);
    }

    public static AST uncheckedTestAllErrors() {
        Stylesheet stylesheet = new Stylesheet();
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("V"))
                .addChild(new ColorLiteral("#ffffff"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Color"))
                .addChild(new ColorLiteral("#000000"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("P"))
                .addChild(new PercentageLiteral("360%"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Pi"))
                .addChild(new PixelLiteral("314px"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Z"))
                .addChild(new ScalarLiteral("40"))
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("X"))
                .addChild((new AddOperation())
                        .addChild(new ScalarLiteral("40"))
                        .addChild(new ScalarLiteral("40"))
                ));
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Y"))
                .addChild((new MultiplyOperation())
                        .addChild(new ScalarLiteral("50px"))
                        .addChild(new ScalarLiteral("50px"))
                ));
        /*
        V := #ffffff;
        Color := #000000;
        P := 360%;
        Pi := 314px;
        Z := 40;
        X := 40 + 40;
        y := 50px * 50px;
         */

        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new AddOperation())
                        .addChild(new VariableReference("Idk"))
                        .addChild(new PixelLiteral("50px"))
                )
                .addChild((new Declaration("height"))
                        .addChild(new AddOperation())
                        .addChild(new MultiplyOperation())
                        .addChild(new PixelLiteral("50px"))
                        .addChild(new PixelLiteral("50px"))
                        .addChild(new ScalarLiteral("50"))
                )
                .addChild((new Declaration("width"))
                        .addChild(new AddOperation())
                        .addChild(new PixelLiteral("600px"))
                        .addChild(new ColorLiteral("#ffffff"))
                )
                .addChild((new Declaration("Width"))
                        .addChild(new PixelLiteral("50px"))
                )
                .addChild((new Declaration("height"))
                        .addChild(new AddOperation())
                        .addChild(new ScalarLiteral("50"))
                        .addChild(new ScalarLiteral("50"))
                ));

        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("m"))
                .addChild((new Declaration("width"))
                        .addChild(new ColorLiteral("#ffffff"))
                )
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("Color"))
                )
                .addChild((new Declaration("color"))
                        .addChild(new PixelLiteral("50px"))
                )
                .addChild((new Declaration("color"))
                        .addChild(new PercentageLiteral("5%"))
                )
                .addChild((new Declaration("color"))
                        .addChild(new VariableReference("Pi"))
                )
                .addChild((new Declaration("color"))
                        .addChild(new VariableReference("P"))
                ));
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("o"))
                .addChild((new IfClause())
                        .addChild(new VariableReference("V"))
                        .addChild((new Declaration("color"))
                                .addChild(new ColorLiteral("#124532")))
                        .addChild((new IfClause())
                                .addChild(new VariableReference("P"))
                                .addChild((new Declaration("background-color"))
                                        .addChild(new VariableReference("Width")))
                                .addChild((new ElseClause())
                                        .addChild(new Declaration("background-color"))
                                        .addChild(new ColorLiteral("#000000"))
                                )
                        )
                ));

        /*
        p {
	width: Idk + 50px;
	height: 50px * 50px +50;
        width: 600px + #ffffff;
	Width := 50px;
	height: 50 + 50;
}
m {
	width: #ffffff;
	width: Color;
	color: 50px;
	color: 5%;
	color: Pi;
	color: P;
}
o {
	if[V] {
	    color: #124532;
	    if[P]{
	        background-color: Width;
	    } else {
	        background-color: #000000;
	    }
	}
}
         */

        return new AST(stylesheet);
    }

    public static AST uncheckedTestVarUsedInWrongScope() {
        /*
       p {
            Width := 50px;
       }

       m {
        width: Width;
        }
         */

        Stylesheet stylesheet = new Stylesheet();
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("Width"))
                        .addChild(new PixelLiteral("50px"))
                )
        );
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("m"))
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("Width"))
                )
        );
        return new AST(stylesheet);
    }

    public static AST uncheckedTestVarUsedBeforeDefinition() {
        /*
       p {
            width: Width;
       }

       Width := 50px;
         */

        Stylesheet stylesheet = new Stylesheet();
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new Declaration("width"))
                        .addChild(new VariableReference("Width"))
                )
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Width"))
                .addChild(new PixelLiteral("50px"))
        );
        return new AST(stylesheet);
    }
}
