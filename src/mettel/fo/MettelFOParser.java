// $ANTLR 3.4 /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g 2011-10-30 19:30:56

package mettel.fo;

import mettel.core.MettelExpression;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class MettelFOParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "ID", "WS", "'&'", "'('", "')'", "','", "'->'", "'<->'", "'='", "'['", "']'", "'exists'", "'false'", "'forall'", "'holds'", "'true'", "'|'", "'~'"
    };

    public static final int EOF=-1;
    public static final int T__7=7;
    public static final int T__8=8;
    public static final int T__9=9;
    public static final int T__10=10;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int COMMENT=4;
    public static final int ID=5;
    public static final int WS=6;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public MettelFOParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public MettelFOParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return MettelFOParser.tokenNames; }
    public String getGrammarFileName() { return "/home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g"; }


    private MettelLogicParser islandParser = null;
    private Lexer islandLexer = null;

    private MettelExpression expression() throws RecognitionException {
    		CharStream in = ((Lexer)this.input.getTokenSource()).getCharStream();
    		int index = in.index();
    		int line = in.getLine();
    		int charPositionInLine = in.getCharPositionInLine();
            islandLexer.setCharStream(in);
            in.seek(index);
            in.setLine(line);
            in.setCharPositionInLine(charPositionInLine);

    		return islandParser.expression();
    	}




    // $ANTLR start "formulae"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:55:1: formulae : ( formula )+ ;
    public final void formulae() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:56:2: ( ( formula )+ )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:57:2: ( formula )+
            {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:57:2: ( formula )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||LA1_0==8||LA1_0==14||(LA1_0 >= 16 && LA1_0 <= 20)||LA1_0==22) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:57:2: formula
            	    {
            	    pushFollow(FOLLOW_formula_in_formulae60);
            	    formula();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "formulae"



    // $ANTLR start "formula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:60:1: formula : equivalenceFormula ;
    public final void formula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:61:2: ( equivalenceFormula )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:62:2: equivalenceFormula
            {
            pushFollow(FOLLOW_equivalenceFormula_in_formula73);
            equivalenceFormula();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "formula"



    // $ANTLR start "equivalenceFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:66:1: equivalenceFormula : implicationFormula ( '<->' implicationFormula )* ;
    public final void equivalenceFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:67:2: ( implicationFormula ( '<->' implicationFormula )* )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:68:2: implicationFormula ( '<->' implicationFormula )*
            {
            pushFollow(FOLLOW_implicationFormula_in_equivalenceFormula86);
            implicationFormula();

            state._fsp--;


            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:69:2: ( '<->' implicationFormula )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==12) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:69:3: '<->' implicationFormula
            	    {
            	    match(input,12,FOLLOW_12_in_equivalenceFormula90); 

            	    pushFollow(FOLLOW_implicationFormula_in_equivalenceFormula93);
            	    implicationFormula();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "equivalenceFormula"



    // $ANTLR start "implicationFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:74:1: implicationFormula : disjunctionFormula ( '->' disjunctionFormula )* ;
    public final void implicationFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:75:2: ( disjunctionFormula ( '->' disjunctionFormula )* )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:76:2: disjunctionFormula ( '->' disjunctionFormula )*
            {
            pushFollow(FOLLOW_disjunctionFormula_in_implicationFormula108);
            disjunctionFormula();

            state._fsp--;


            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:77:2: ( '->' disjunctionFormula )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==11) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:77:3: '->' disjunctionFormula
            	    {
            	    match(input,11,FOLLOW_11_in_implicationFormula112); 

            	    pushFollow(FOLLOW_disjunctionFormula_in_implicationFormula115);
            	    disjunctionFormula();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "implicationFormula"



    // $ANTLR start "disjunctionFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:82:1: disjunctionFormula : conjunctionFormula ( '|' conjunctionFormula )* ;
    public final void disjunctionFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:83:2: ( conjunctionFormula ( '|' conjunctionFormula )* )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:84:2: conjunctionFormula ( '|' conjunctionFormula )*
            {
            pushFollow(FOLLOW_conjunctionFormula_in_disjunctionFormula130);
            conjunctionFormula();

            state._fsp--;


            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:85:2: ( '|' conjunctionFormula )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==21) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:85:3: '|' conjunctionFormula
            	    {
            	    match(input,21,FOLLOW_21_in_disjunctionFormula134); 

            	    pushFollow(FOLLOW_conjunctionFormula_in_disjunctionFormula137);
            	    conjunctionFormula();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "disjunctionFormula"



    // $ANTLR start "conjunctionFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:89:1: conjunctionFormula : existentialFormula ( '&' existentialFormula )* ;
    public final void conjunctionFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:90:2: ( existentialFormula ( '&' existentialFormula )* )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:91:2: existentialFormula ( '&' existentialFormula )*
            {
            pushFollow(FOLLOW_existentialFormula_in_conjunctionFormula151);
            existentialFormula();

            state._fsp--;


            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:92:2: ( '&' existentialFormula )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==7) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:92:3: '&' existentialFormula
            	    {
            	    match(input,7,FOLLOW_7_in_conjunctionFormula155); 

            	    pushFollow(FOLLOW_existentialFormula_in_conjunctionFormula158);
            	    existentialFormula();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "conjunctionFormula"



    // $ANTLR start "existentialFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:96:1: existentialFormula : ( 'exists' ( ID ( 'exists' ID )* | '[' ( ID )+ ']' ) )? universalFormula ;
    public final void existentialFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:97:2: ( ( 'exists' ( ID ( 'exists' ID )* | '[' ( ID )+ ']' ) )? universalFormula )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:98:2: ( 'exists' ( ID ( 'exists' ID )* | '[' ( ID )+ ']' ) )? universalFormula
            {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:98:2: ( 'exists' ( ID ( 'exists' ID )* | '[' ( ID )+ ']' ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==16) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:98:3: 'exists' ( ID ( 'exists' ID )* | '[' ( ID )+ ']' )
                    {
                    match(input,16,FOLLOW_16_in_existentialFormula173); 

                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:99:4: ( ID ( 'exists' ID )* | '[' ( ID )+ ']' )
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==ID) ) {
                        alt8=1;
                    }
                    else if ( (LA8_0==14) ) {
                        alt8=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 0, input);

                        throw nvae;

                    }
                    switch (alt8) {
                        case 1 :
                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:99:5: ID ( 'exists' ID )*
                            {
                            match(input,ID,FOLLOW_ID_in_existentialFormula179); 

                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:99:8: ( 'exists' ID )*
                            loop6:
                            do {
                                int alt6=2;
                                int LA6_0 = input.LA(1);

                                if ( (LA6_0==16) ) {
                                    alt6=1;
                                }


                                switch (alt6) {
                            	case 1 :
                            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:99:9: 'exists' ID
                            	    {
                            	    match(input,16,FOLLOW_16_in_existentialFormula182); 

                            	    match(input,ID,FOLLOW_ID_in_existentialFormula184); 

                            	    }
                            	    break;

                            	default :
                            	    break loop6;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:101:4: '[' ( ID )+ ']'
                            {
                            match(input,14,FOLLOW_14_in_existentialFormula196); 

                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:101:8: ( ID )+
                            int cnt7=0;
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( (LA7_0==ID) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:101:8: ID
                            	    {
                            	    match(input,ID,FOLLOW_ID_in_existentialFormula198); 

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt7 >= 1 ) break loop7;
                                        EarlyExitException eee =
                                            new EarlyExitException(7, input);
                                        throw eee;
                                }
                                cnt7++;
                            } while (true);


                            match(input,15,FOLLOW_15_in_existentialFormula201); 

                            }
                            break;

                    }


                    }
                    break;

            }


            pushFollow(FOLLOW_universalFormula_in_existentialFormula213);
            universalFormula();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "existentialFormula"



    // $ANTLR start "universalFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:107:1: universalFormula : ( 'forall' ( ID ( 'forall' ID )* | '[' ( ID )+ ']' ) )? negationFormula ;
    public final void universalFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:108:2: ( ( 'forall' ( ID ( 'forall' ID )* | '[' ( ID )+ ']' ) )? negationFormula )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:109:2: ( 'forall' ( ID ( 'forall' ID )* | '[' ( ID )+ ']' ) )? negationFormula
            {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:109:2: ( 'forall' ( ID ( 'forall' ID )* | '[' ( ID )+ ']' ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==18) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:109:3: 'forall' ( ID ( 'forall' ID )* | '[' ( ID )+ ']' )
                    {
                    match(input,18,FOLLOW_18_in_universalFormula226); 

                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:110:4: ( ID ( 'forall' ID )* | '[' ( ID )+ ']' )
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==ID) ) {
                        alt12=1;
                    }
                    else if ( (LA12_0==14) ) {
                        alt12=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;

                    }
                    switch (alt12) {
                        case 1 :
                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:110:5: ID ( 'forall' ID )*
                            {
                            match(input,ID,FOLLOW_ID_in_universalFormula232); 

                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:110:8: ( 'forall' ID )*
                            loop10:
                            do {
                                int alt10=2;
                                int LA10_0 = input.LA(1);

                                if ( (LA10_0==18) ) {
                                    alt10=1;
                                }


                                switch (alt10) {
                            	case 1 :
                            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:110:9: 'forall' ID
                            	    {
                            	    match(input,18,FOLLOW_18_in_universalFormula235); 

                            	    match(input,ID,FOLLOW_ID_in_universalFormula237); 

                            	    }
                            	    break;

                            	default :
                            	    break loop10;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:112:4: '[' ( ID )+ ']'
                            {
                            match(input,14,FOLLOW_14_in_universalFormula249); 

                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:112:8: ( ID )+
                            int cnt11=0;
                            loop11:
                            do {
                                int alt11=2;
                                int LA11_0 = input.LA(1);

                                if ( (LA11_0==ID) ) {
                                    alt11=1;
                                }


                                switch (alt11) {
                            	case 1 :
                            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:112:8: ID
                            	    {
                            	    match(input,ID,FOLLOW_ID_in_universalFormula251); 

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt11 >= 1 ) break loop11;
                                        EarlyExitException eee =
                                            new EarlyExitException(11, input);
                                        throw eee;
                                }
                                cnt11++;
                            } while (true);


                            match(input,15,FOLLOW_15_in_universalFormula254); 

                            }
                            break;

                    }


                    }
                    break;

            }


            pushFollow(FOLLOW_negationFormula_in_universalFormula266);
            negationFormula();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "universalFormula"



    // $ANTLR start "negationFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:118:1: negationFormula : ( '~' )* basicFormula ;
    public final void negationFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:119:2: ( ( '~' )* basicFormula )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:120:2: ( '~' )* basicFormula
            {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:120:2: ( '~' )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==22) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:120:3: '~'
            	    {
            	    match(input,22,FOLLOW_22_in_negationFormula279); 

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            pushFollow(FOLLOW_basicFormula_in_negationFormula284);
            basicFormula();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "negationFormula"



    // $ANTLR start "basicFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:124:1: basicFormula : ( 'true' | 'false' | '(' formula ')' | atomicFormula );
    public final void basicFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:125:2: ( 'true' | 'false' | '(' formula ')' | atomicFormula )
            int alt15=4;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt15=1;
                }
                break;
            case 17:
                {
                alt15=2;
                }
                break;
            case 8:
                {
                alt15=3;
                }
                break;
            case ID:
            case 14:
            case 19:
                {
                alt15=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }

            switch (alt15) {
                case 1 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:126:2: 'true'
                    {
                    match(input,20,FOLLOW_20_in_basicFormula296); 

                    }
                    break;
                case 2 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:128:2: 'false'
                    {
                    match(input,17,FOLLOW_17_in_basicFormula302); 

                    }
                    break;
                case 3 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:130:2: '(' formula ')'
                    {
                    match(input,8,FOLLOW_8_in_basicFormula308); 

                    pushFollow(FOLLOW_formula_in_basicFormula310);
                    formula();

                    state._fsp--;


                    match(input,9,FOLLOW_9_in_basicFormula312); 

                    }
                    break;
                case 4 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:132:2: atomicFormula
                    {
                    pushFollow(FOLLOW_atomicFormula_in_basicFormula318);
                    atomicFormula();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "basicFormula"



    // $ANTLR start "atomicFormula"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:135:1: atomicFormula : ( 'holds' '(' ( ',' term )+ ')' | ID '(' ( termList )? ')' | '[' term '=' term ']' );
    public final void atomicFormula() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:136:2: ( 'holds' '(' ( ',' term )+ ')' | ID '(' ( termList )? ')' | '[' term '=' term ']' )
            int alt18=3;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt18=1;
                }
                break;
            case ID:
                {
                alt18=2;
                }
                break;
            case 14:
                {
                alt18=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }

            switch (alt18) {
                case 1 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:137:2: 'holds' '(' ( ',' term )+ ')'
                    {
                    match(input,19,FOLLOW_19_in_atomicFormula330); 

                    match(input,8,FOLLOW_8_in_atomicFormula332); 

                    expression();

                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:137:30: ( ',' term )+
                    int cnt16=0;
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==10) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:137:31: ',' term
                    	    {
                    	    match(input,10,FOLLOW_10_in_atomicFormula337); 

                    	    pushFollow(FOLLOW_term_in_atomicFormula339);
                    	    term();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt16 >= 1 ) break loop16;
                                EarlyExitException eee =
                                    new EarlyExitException(16, input);
                                throw eee;
                        }
                        cnt16++;
                    } while (true);


                    match(input,9,FOLLOW_9_in_atomicFormula343); 

                    }
                    break;
                case 2 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:139:2: ID '(' ( termList )? ')'
                    {
                    match(input,ID,FOLLOW_ID_in_atomicFormula349); 

                    match(input,8,FOLLOW_8_in_atomicFormula351); 

                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:139:9: ( termList )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==ID) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:139:9: termList
                            {
                            pushFollow(FOLLOW_termList_in_atomicFormula353);
                            termList();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input,9,FOLLOW_9_in_atomicFormula356); 

                    }
                    break;
                case 3 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:141:2: '[' term '=' term ']'
                    {
                    match(input,14,FOLLOW_14_in_atomicFormula362); 

                    pushFollow(FOLLOW_term_in_atomicFormula364);
                    term();

                    state._fsp--;


                    match(input,13,FOLLOW_13_in_atomicFormula366); 

                    pushFollow(FOLLOW_term_in_atomicFormula368);
                    term();

                    state._fsp--;


                    match(input,15,FOLLOW_15_in_atomicFormula370); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "atomicFormula"



    // $ANTLR start "term"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:144:1: term : ID ( '(' ( termList )? ')' )? ;
    public final void term() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:145:2: ( ID ( '(' ( termList )? ')' )? )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:146:2: ID ( '(' ( termList )? ')' )?
            {
            match(input,ID,FOLLOW_ID_in_term382); 

            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:147:2: ( '(' ( termList )? ')' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==8) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:147:4: '(' ( termList )? ')'
                    {
                    match(input,8,FOLLOW_8_in_term387); 

                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:147:8: ( termList )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==ID) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:147:8: termList
                            {
                            pushFollow(FOLLOW_termList_in_term389);
                            termList();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input,9,FOLLOW_9_in_term392); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "term"



    // $ANTLR start "termList"
    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:150:1: termList : term ( ',' term )+ ;
    public final void termList() throws RecognitionException {
        try {
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:151:2: ( term ( ',' term )+ )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:152:2: term ( ',' term )+
            {
            pushFollow(FOLLOW_term_in_termList407);
            term();

            state._fsp--;


            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:153:2: ( ',' term )+
            int cnt21=0;
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==10) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:153:3: ',' term
            	    {
            	    match(input,10,FOLLOW_10_in_termList411); 

            	    pushFollow(FOLLOW_term_in_termList414);
            	    term();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt21 >= 1 ) break loop21;
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "termList"

    // Delegated rules


 

    public static final BitSet FOLLOW_formula_in_formulae60 = new BitSet(new long[]{0x00000000005F4122L});
    public static final BitSet FOLLOW_equivalenceFormula_in_formula73 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_implicationFormula_in_equivalenceFormula86 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_12_in_equivalenceFormula90 = new BitSet(new long[]{0x00000000005F4120L});
    public static final BitSet FOLLOW_implicationFormula_in_equivalenceFormula93 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_disjunctionFormula_in_implicationFormula108 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_11_in_implicationFormula112 = new BitSet(new long[]{0x00000000005F4120L});
    public static final BitSet FOLLOW_disjunctionFormula_in_implicationFormula115 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_conjunctionFormula_in_disjunctionFormula130 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_21_in_disjunctionFormula134 = new BitSet(new long[]{0x00000000005F4120L});
    public static final BitSet FOLLOW_conjunctionFormula_in_disjunctionFormula137 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_existentialFormula_in_conjunctionFormula151 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_7_in_conjunctionFormula155 = new BitSet(new long[]{0x00000000005F4120L});
    public static final BitSet FOLLOW_existentialFormula_in_conjunctionFormula158 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_16_in_existentialFormula173 = new BitSet(new long[]{0x0000000000004020L});
    public static final BitSet FOLLOW_ID_in_existentialFormula179 = new BitSet(new long[]{0x00000000005F4120L});
    public static final BitSet FOLLOW_16_in_existentialFormula182 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_existentialFormula184 = new BitSet(new long[]{0x00000000005F4120L});
    public static final BitSet FOLLOW_14_in_existentialFormula196 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_existentialFormula198 = new BitSet(new long[]{0x0000000000008020L});
    public static final BitSet FOLLOW_15_in_existentialFormula201 = new BitSet(new long[]{0x00000000005E4120L});
    public static final BitSet FOLLOW_universalFormula_in_existentialFormula213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_universalFormula226 = new BitSet(new long[]{0x0000000000004020L});
    public static final BitSet FOLLOW_ID_in_universalFormula232 = new BitSet(new long[]{0x00000000005E4120L});
    public static final BitSet FOLLOW_18_in_universalFormula235 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_universalFormula237 = new BitSet(new long[]{0x00000000005E4120L});
    public static final BitSet FOLLOW_14_in_universalFormula249 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_universalFormula251 = new BitSet(new long[]{0x0000000000008020L});
    public static final BitSet FOLLOW_15_in_universalFormula254 = new BitSet(new long[]{0x00000000005A4120L});
    public static final BitSet FOLLOW_negationFormula_in_universalFormula266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_negationFormula279 = new BitSet(new long[]{0x00000000005A4120L});
    public static final BitSet FOLLOW_basicFormula_in_negationFormula284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_basicFormula296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_basicFormula302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_8_in_basicFormula308 = new BitSet(new long[]{0x00000000005F4120L});
    public static final BitSet FOLLOW_formula_in_basicFormula310 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_basicFormula312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicFormula_in_basicFormula318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_atomicFormula330 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_atomicFormula332 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_atomicFormula337 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_term_in_atomicFormula339 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_9_in_atomicFormula343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicFormula349 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_atomicFormula351 = new BitSet(new long[]{0x0000000000000220L});
    public static final BitSet FOLLOW_termList_in_atomicFormula353 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_atomicFormula356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_atomicFormula362 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_term_in_atomicFormula364 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_atomicFormula366 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_term_in_atomicFormula368 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_atomicFormula370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_term382 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_8_in_term387 = new BitSet(new long[]{0x0000000000000220L});
    public static final BitSet FOLLOW_termList_in_term389 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_term392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_termList407 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_termList411 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_term_in_termList414 = new BitSet(new long[]{0x0000000000000402L});

}