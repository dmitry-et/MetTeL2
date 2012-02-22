// $ANTLR 3.2 Fedora release 15 (Rawhide) Tue Feb  8 02:02:23 UTC 2011 /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g 2012-02-22 12:43:43

package mettel.fo;

import mettel.core.MettelExpression;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class MettelFOParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "COMMENT", "WS", "'<->'", "'->'", "'|'", "'&'", "'exists'", "'forall'", "'['", "']'", "'('", "')'", "'~'", "'true'", "'false'", "'holds'", "','", "'='"
    };
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;
    public static final int T__19=19;
    public static final int WS=6;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int COMMENT=5;

    // delegates
    // delegators


        public MettelFOParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public MettelFOParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return MettelFOParser.tokenNames; }
    public String getGrammarFileName() { return "/home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g"; }


    public Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
           throws RecognitionException{
        MismatchedTokenException e = new MismatchedTokenException(ttype, input);
        reportError(e);
        throw e;
    }

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
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:69:1: formulae : ( formula )+ ;
    public final void formulae() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:70:2: ( ( formula )+ )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:71:2: ( formula )+
            {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:71:2: ( formula )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||(LA1_0>=11 && LA1_0<=13)||LA1_0==15||(LA1_0>=17 && LA1_0<=20)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:0:0: formula
            	    {
            	    pushFollow(FOLLOW_formula_in_formulae66);
            	    formula();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "formulae"


    // $ANTLR start "formula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:74:1: formula : equivalenceFormula ;
    public final void formula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:75:2: ( equivalenceFormula )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:76:2: equivalenceFormula
            {
            pushFollow(FOLLOW_equivalenceFormula_in_formula79);
            equivalenceFormula();

            state._fsp--;
            if (state.failed) return ;

            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "formula"


    // $ANTLR start "equivalenceFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:80:1: equivalenceFormula : implicationFormula ( '<->' implicationFormula )* ;
    public final void equivalenceFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:81:2: ( implicationFormula ( '<->' implicationFormula )* )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:82:2: implicationFormula ( '<->' implicationFormula )*
            {
            pushFollow(FOLLOW_implicationFormula_in_equivalenceFormula92);
            implicationFormula();

            state._fsp--;
            if (state.failed) return ;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:83:2: ( '<->' implicationFormula )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==7) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:83:3: '<->' implicationFormula
            	    {
            	    match(input,7,FOLLOW_7_in_equivalenceFormula96); if (state.failed) return ;
            	    pushFollow(FOLLOW_implicationFormula_in_equivalenceFormula99);
            	    implicationFormula();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "equivalenceFormula"


    // $ANTLR start "implicationFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:88:1: implicationFormula : disjunctionFormula ( '->' disjunctionFormula )* ;
    public final void implicationFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:89:2: ( disjunctionFormula ( '->' disjunctionFormula )* )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:90:2: disjunctionFormula ( '->' disjunctionFormula )*
            {
            pushFollow(FOLLOW_disjunctionFormula_in_implicationFormula114);
            disjunctionFormula();

            state._fsp--;
            if (state.failed) return ;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:91:2: ( '->' disjunctionFormula )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==8) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:91:3: '->' disjunctionFormula
            	    {
            	    match(input,8,FOLLOW_8_in_implicationFormula118); if (state.failed) return ;
            	    pushFollow(FOLLOW_disjunctionFormula_in_implicationFormula121);
            	    disjunctionFormula();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "implicationFormula"


    // $ANTLR start "disjunctionFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:96:1: disjunctionFormula : conjunctionFormula ( '|' conjunctionFormula )* ;
    public final void disjunctionFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:97:2: ( conjunctionFormula ( '|' conjunctionFormula )* )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:98:2: conjunctionFormula ( '|' conjunctionFormula )*
            {
            pushFollow(FOLLOW_conjunctionFormula_in_disjunctionFormula136);
            conjunctionFormula();

            state._fsp--;
            if (state.failed) return ;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:99:2: ( '|' conjunctionFormula )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==9) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:99:3: '|' conjunctionFormula
            	    {
            	    match(input,9,FOLLOW_9_in_disjunctionFormula140); if (state.failed) return ;
            	    pushFollow(FOLLOW_conjunctionFormula_in_disjunctionFormula143);
            	    conjunctionFormula();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "disjunctionFormula"


    // $ANTLR start "conjunctionFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:103:1: conjunctionFormula : existentialFormula ( '&' existentialFormula )* ;
    public final void conjunctionFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:104:2: ( existentialFormula ( '&' existentialFormula )* )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:105:2: existentialFormula ( '&' existentialFormula )*
            {
            pushFollow(FOLLOW_existentialFormula_in_conjunctionFormula157);
            existentialFormula();

            state._fsp--;
            if (state.failed) return ;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:106:2: ( '&' existentialFormula )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==10) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:106:3: '&' existentialFormula
            	    {
            	    match(input,10,FOLLOW_10_in_conjunctionFormula161); if (state.failed) return ;
            	    pushFollow(FOLLOW_existentialFormula_in_conjunctionFormula164);
            	    existentialFormula();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "conjunctionFormula"


    // $ANTLR start "existentialFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:110:1: existentialFormula : ( 'exists' variableList )* universalFormula ;
    public final void existentialFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:111:2: ( ( 'exists' variableList )* universalFormula )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:112:2: ( 'exists' variableList )* universalFormula
            {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:112:2: ( 'exists' variableList )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==11) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:112:3: 'exists' variableList
            	    {
            	    match(input,11,FOLLOW_11_in_existentialFormula179); if (state.failed) return ;
            	    pushFollow(FOLLOW_variableList_in_existentialFormula181);
            	    variableList();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            pushFollow(FOLLOW_universalFormula_in_existentialFormula191);
            universalFormula();

            state._fsp--;
            if (state.failed) return ;

            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "existentialFormula"


    // $ANTLR start "universalFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:121:1: universalFormula : ( 'forall' variableList )* negationFormula ;
    public final void universalFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:122:2: ( ( 'forall' variableList )* negationFormula )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:123:2: ( 'forall' variableList )* negationFormula
            {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:123:2: ( 'forall' variableList )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==12) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:123:3: 'forall' variableList
            	    {
            	    match(input,12,FOLLOW_12_in_universalFormula204); if (state.failed) return ;
            	    pushFollow(FOLLOW_variableList_in_universalFormula206);
            	    variableList();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            pushFollow(FOLLOW_negationFormula_in_universalFormula216);
            negationFormula();

            state._fsp--;
            if (state.failed) return ;

            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "universalFormula"


    // $ANTLR start "variableList"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:132:1: variableList : ( ID | '[' ( ID )+ ']' | '(' ( ID )+ ')' );
    public final void variableList() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:133:2: ( ID | '[' ( ID )+ ']' | '(' ( ID )+ ')' )
            int alt10=3;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt10=1;
                }
                break;
            case 13:
                {
                alt10=2;
                }
                break;
            case 15:
                {
                alt10=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:134:3: ID
                    {
                    match(input,ID,FOLLOW_ID_in_variableList229); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:136:3: '[' ( ID )+ ']'
                    {
                    match(input,13,FOLLOW_13_in_variableList237); if (state.failed) return ;
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:136:7: ( ID )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==ID) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:0:0: ID
                    	    {
                    	    match(input,ID,FOLLOW_ID_in_variableList239); if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);

                    match(input,14,FOLLOW_14_in_variableList242); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:138:3: '(' ( ID )+ ')'
                    {
                    match(input,15,FOLLOW_15_in_variableList250); if (state.failed) return ;
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:138:7: ( ID )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==ID) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:0:0: ID
                    	    {
                    	    match(input,ID,FOLLOW_ID_in_variableList252); if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    match(input,16,FOLLOW_16_in_variableList255); if (state.failed) return ;

                    }
                    break;

            }
        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "variableList"


    // $ANTLR start "negationFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:141:1: negationFormula : ( '~' )* basicFormula ;
    public final void negationFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:142:2: ( ( '~' )* basicFormula )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:143:2: ( '~' )* basicFormula
            {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:143:2: ( '~' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==17) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:143:3: '~'
            	    {
            	    match(input,17,FOLLOW_17_in_negationFormula268); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            pushFollow(FOLLOW_basicFormula_in_negationFormula273);
            basicFormula();

            state._fsp--;
            if (state.failed) return ;

            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "negationFormula"


    // $ANTLR start "basicFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:147:1: basicFormula : ( 'true' | 'false' | '(' formula ')' | atomicFormula );
    public final void basicFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:148:2: ( 'true' | 'false' | '(' formula ')' | atomicFormula )
            int alt12=4;
            switch ( input.LA(1) ) {
            case 18:
                {
                alt12=1;
                }
                break;
            case 19:
                {
                alt12=2;
                }
                break;
            case 15:
                {
                int LA12_3 = input.LA(2);

                if ( (synpred15_MettelFO()) ) {
                    alt12=3;
                }
                else if ( (true) ) {
                    alt12=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 3, input);

                    throw nvae;
                }
                }
                break;
            case ID:
            case 13:
            case 20:
                {
                alt12=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:149:2: 'true'
                    {
                    match(input,18,FOLLOW_18_in_basicFormula285); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:151:2: 'false'
                    {
                    match(input,19,FOLLOW_19_in_basicFormula291); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:153:2: '(' formula ')'
                    {
                    match(input,15,FOLLOW_15_in_basicFormula297); if (state.failed) return ;
                    pushFollow(FOLLOW_formula_in_basicFormula299);
                    formula();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,16,FOLLOW_16_in_basicFormula301); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:155:2: atomicFormula
                    {
                    pushFollow(FOLLOW_atomicFormula_in_basicFormula307);
                    atomicFormula();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "basicFormula"


    // $ANTLR start "atomicFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:158:1: atomicFormula : ( 'holds' '(' ( ',' term )+ ')' | ID '(' ( termList )? ')' | equalityFormula );
    public final void atomicFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:159:2: ( 'holds' '(' ( ',' term )+ ')' | ID '(' ( termList )? ')' | equalityFormula )
            int alt15=3;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt15=1;
                }
                break;
            case ID:
                {
                alt15=2;
                }
                break;
            case 13:
            case 15:
                {
                alt15=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:160:2: 'holds' '(' ( ',' term )+ ')'
                    {
                    match(input,20,FOLLOW_20_in_atomicFormula319); if (state.failed) return ;
                    match(input,15,FOLLOW_15_in_atomicFormula321); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      expression();
                    }
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:160:30: ( ',' term )+
                    int cnt13=0;
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==21) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:160:31: ',' term
                    	    {
                    	    match(input,21,FOLLOW_21_in_atomicFormula326); if (state.failed) return ;
                    	    pushFollow(FOLLOW_term_in_atomicFormula328);
                    	    term();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt13 >= 1 ) break loop13;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(13, input);
                                throw eee;
                        }
                        cnt13++;
                    } while (true);

                    match(input,16,FOLLOW_16_in_atomicFormula332); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:162:2: ID '(' ( termList )? ')'
                    {
                    match(input,ID,FOLLOW_ID_in_atomicFormula338); if (state.failed) return ;
                    match(input,15,FOLLOW_15_in_atomicFormula340); if (state.failed) return ;
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:162:9: ( termList )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==ID) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:0:0: termList
                            {
                            pushFollow(FOLLOW_termList_in_atomicFormula342);
                            termList();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }

                    match(input,16,FOLLOW_16_in_atomicFormula345); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:164:5: equalityFormula
                    {
                    pushFollow(FOLLOW_equalityFormula_in_atomicFormula354);
                    equalityFormula();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "atomicFormula"


    // $ANTLR start "equalityFormula"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:167:1: equalityFormula : ( '[' term '=' term ']' | '(' term '=' term ')' );
    public final void equalityFormula() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:168:2: ( '[' term '=' term ']' | '(' term '=' term ')' )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==13) ) {
                alt16=1;
            }
            else if ( (LA16_0==15) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:169:2: '[' term '=' term ']'
                    {
                    match(input,13,FOLLOW_13_in_equalityFormula366); if (state.failed) return ;
                    pushFollow(FOLLOW_term_in_equalityFormula368);
                    term();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,22,FOLLOW_22_in_equalityFormula370); if (state.failed) return ;
                    pushFollow(FOLLOW_term_in_equalityFormula372);
                    term();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,14,FOLLOW_14_in_equalityFormula374); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:171:2: '(' term '=' term ')'
                    {
                    match(input,15,FOLLOW_15_in_equalityFormula380); if (state.failed) return ;
                    pushFollow(FOLLOW_term_in_equalityFormula382);
                    term();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,22,FOLLOW_22_in_equalityFormula384); if (state.failed) return ;
                    pushFollow(FOLLOW_term_in_equalityFormula386);
                    term();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,16,FOLLOW_16_in_equalityFormula388); if (state.failed) return ;

                    }
                    break;

            }
        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "equalityFormula"


    // $ANTLR start "term"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:174:1: term : ID ( '(' ( termList )? ')' )? ;
    public final void term() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:175:2: ( ID ( '(' ( termList )? ')' )? )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:176:2: ID ( '(' ( termList )? ')' )?
            {
            match(input,ID,FOLLOW_ID_in_term400); if (state.failed) return ;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:177:2: ( '(' ( termList )? ')' )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==15) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:177:4: '(' ( termList )? ')'
                    {
                    match(input,15,FOLLOW_15_in_term405); if (state.failed) return ;
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:177:8: ( termList )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==ID) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:0:0: termList
                            {
                            pushFollow(FOLLOW_termList_in_term407);
                            termList();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }

                    match(input,16,FOLLOW_16_in_term410); if (state.failed) return ;

                    }
                    break;

            }


            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "term"


    // $ANTLR start "termList"
    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:180:1: termList : term ( ',' term )+ ;
    public final void termList() throws RecognitionException {
        try {
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:181:2: ( term ( ',' term )+ )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:182:2: term ( ',' term )+
            {
            pushFollow(FOLLOW_term_in_termList425);
            term();

            state._fsp--;
            if (state.failed) return ;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:183:2: ( ',' term )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==21) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:183:3: ',' term
            	    {
            	    match(input,21,FOLLOW_21_in_termList429); if (state.failed) return ;
            	    pushFollow(FOLLOW_term_in_termList432);
            	    term();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt19 >= 1 ) break loop19;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(19, input);
                        throw eee;
                }
                cnt19++;
            } while (true);


            }

        }

        catch (RecognitionException e) {
        reportError(e);
        throw e;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "termList"

    // $ANTLR start synpred15_MettelFO
    public final void synpred15_MettelFO_fragment() throws RecognitionException {   
        // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:153:2: ( '(' formula ')' )
        // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:153:2: '(' formula ')'
        {
        match(input,15,FOLLOW_15_in_synpred15_MettelFO297); if (state.failed) return ;
        pushFollow(FOLLOW_formula_in_synpred15_MettelFO299);
        formula();

        state._fsp--;
        if (state.failed) return ;
        match(input,16,FOLLOW_16_in_synpred15_MettelFO301); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_MettelFO

    // Delegated rules

    public final boolean synpred15_MettelFO() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_MettelFO_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_formula_in_formulae66 = new BitSet(new long[]{0x00000000001EB812L});
    public static final BitSet FOLLOW_equivalenceFormula_in_formula79 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_implicationFormula_in_equivalenceFormula92 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_7_in_equivalenceFormula96 = new BitSet(new long[]{0x00000000001EB890L});
    public static final BitSet FOLLOW_implicationFormula_in_equivalenceFormula99 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_disjunctionFormula_in_implicationFormula114 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_8_in_implicationFormula118 = new BitSet(new long[]{0x00000000001EB910L});
    public static final BitSet FOLLOW_disjunctionFormula_in_implicationFormula121 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_conjunctionFormula_in_disjunctionFormula136 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_9_in_disjunctionFormula140 = new BitSet(new long[]{0x00000000001EBA10L});
    public static final BitSet FOLLOW_conjunctionFormula_in_disjunctionFormula143 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_existentialFormula_in_conjunctionFormula157 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_10_in_conjunctionFormula161 = new BitSet(new long[]{0x00000000001EBC10L});
    public static final BitSet FOLLOW_existentialFormula_in_conjunctionFormula164 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_11_in_existentialFormula179 = new BitSet(new long[]{0x000000000000A010L});
    public static final BitSet FOLLOW_variableList_in_existentialFormula181 = new BitSet(new long[]{0x00000000001EB810L});
    public static final BitSet FOLLOW_universalFormula_in_existentialFormula191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_universalFormula204 = new BitSet(new long[]{0x000000000000A010L});
    public static final BitSet FOLLOW_variableList_in_universalFormula206 = new BitSet(new long[]{0x00000000001EB810L});
    public static final BitSet FOLLOW_negationFormula_in_universalFormula216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_variableList229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_variableList237 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_variableList239 = new BitSet(new long[]{0x0000000000004010L});
    public static final BitSet FOLLOW_14_in_variableList242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_variableList250 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_variableList252 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_16_in_variableList255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_negationFormula268 = new BitSet(new long[]{0x00000000001EB810L});
    public static final BitSet FOLLOW_basicFormula_in_negationFormula273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_basicFormula285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_basicFormula291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_basicFormula297 = new BitSet(new long[]{0x00000000001FB810L});
    public static final BitSet FOLLOW_formula_in_basicFormula299 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_basicFormula301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicFormula_in_basicFormula307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_atomicFormula319 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_atomicFormula321 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_atomicFormula326 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_term_in_atomicFormula328 = new BitSet(new long[]{0x0000000000210000L});
    public static final BitSet FOLLOW_16_in_atomicFormula332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atomicFormula338 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_atomicFormula340 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_termList_in_atomicFormula342 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_atomicFormula345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_equalityFormula_in_atomicFormula354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_equalityFormula366 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_term_in_equalityFormula368 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_equalityFormula370 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_term_in_equalityFormula372 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_equalityFormula374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_equalityFormula380 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_term_in_equalityFormula382 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_equalityFormula384 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_term_in_equalityFormula386 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_equalityFormula388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_term400 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_term405 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_termList_in_term407 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_term410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_termList425 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_termList429 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_term_in_termList432 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_15_in_synpred15_MettelFO297 = new BitSet(new long[]{0x00000000001FB810L});
    public static final BitSet FOLLOW_formula_in_synpred15_MettelFO299 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_synpred15_MettelFO301 = new BitSet(new long[]{0x0000000000000002L});

}