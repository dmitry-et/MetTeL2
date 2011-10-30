// $ANTLR 3.4 /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g 2011-10-30 19:30:56

package mettel.fo;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class MettelFOLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public MettelFOLexer() {} 
    public MettelFOLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public MettelFOLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g"; }

    // $ANTLR start "T__7"
    public final void mT__7() throws RecognitionException {
        try {
            int _type = T__7;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:6:6: ( '&' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:6:8: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__7"

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:7:6: ( '(' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:7:8: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:8:6: ( ')' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:8:8: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:9:7: ( ',' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:9:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:10:7: ( '->' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:10:9: '->'
            {
            match("->"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:11:7: ( '<->' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:11:9: '<->'
            {
            match("<->"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:12:7: ( '=' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:12:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:13:7: ( '[' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:13:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:14:7: ( ']' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:14:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:15:7: ( 'exists' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:15:9: 'exists'
            {
            match("exists"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:16:7: ( 'false' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:16:9: 'false'
            {
            match("false"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:17:7: ( 'forall' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:17:9: 'forall'
            {
            match("forall"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:18:7: ( 'holds' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:18:9: 'holds'
            {
            match("holds"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:19:7: ( 'true' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:19:9: 'true'
            {
            match("true"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:20:7: ( '|' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:20:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:21:7: ( '~' )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:21:9: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:160:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:160:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:160:31: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:164:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='/') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='/') ) {
                    alt5=1;
                }
                else if ( (LA5_1=='*') ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:164:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match("//"); 



                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:164:14: (~ ( '\\n' | '\\r' ) )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0 >= '\u0000' && LA2_0 <= '\t')||(LA2_0 >= '\u000B' && LA2_0 <= '\f')||(LA2_0 >= '\u000E' && LA2_0 <= '\uFFFF')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:164:28: ( '\\r' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\r') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:164:28: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    _channel=HIDDEN;

                    }
                    break;
                case 2 :
                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:165:9: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 



                    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:165:14: ( options {greedy=false; } : . )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0=='*') ) {
                            int LA4_1 = input.LA(2);

                            if ( (LA4_1=='/') ) {
                                alt4=2;
                            }
                            else if ( ((LA4_1 >= '\u0000' && LA4_1 <= '.')||(LA4_1 >= '0' && LA4_1 <= '\uFFFF')) ) {
                                alt4=1;
                            }


                        }
                        else if ( ((LA4_0 >= '\u0000' && LA4_0 <= ')')||(LA4_0 >= '+' && LA4_0 <= '\uFFFF')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:165:42: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    match("*/"); 



                    _channel=HIDDEN;

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:168:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:168:9: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:8: ( T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | ID | COMMENT | WS )
        int alt6=19;
        switch ( input.LA(1) ) {
        case '&':
            {
            alt6=1;
            }
            break;
        case '(':
            {
            alt6=2;
            }
            break;
        case ')':
            {
            alt6=3;
            }
            break;
        case ',':
            {
            alt6=4;
            }
            break;
        case '-':
            {
            alt6=5;
            }
            break;
        case '<':
            {
            alt6=6;
            }
            break;
        case '=':
            {
            alt6=7;
            }
            break;
        case '[':
            {
            alt6=8;
            }
            break;
        case ']':
            {
            alt6=9;
            }
            break;
        case 'e':
            {
            int LA6_10 = input.LA(2);

            if ( (LA6_10=='x') ) {
                int LA6_19 = input.LA(3);

                if ( (LA6_19=='i') ) {
                    int LA6_24 = input.LA(4);

                    if ( (LA6_24=='s') ) {
                        int LA6_29 = input.LA(5);

                        if ( (LA6_29=='t') ) {
                            int LA6_34 = input.LA(6);

                            if ( (LA6_34=='s') ) {
                                int LA6_39 = input.LA(7);

                                if ( ((LA6_39 >= '0' && LA6_39 <= '9')||(LA6_39 >= 'A' && LA6_39 <= 'Z')||LA6_39=='_'||(LA6_39 >= 'a' && LA6_39 <= 'z')) ) {
                                    alt6=17;
                                }
                                else {
                                    alt6=10;
                                }
                            }
                            else {
                                alt6=17;
                            }
                        }
                        else {
                            alt6=17;
                        }
                    }
                    else {
                        alt6=17;
                    }
                }
                else {
                    alt6=17;
                }
            }
            else {
                alt6=17;
            }
            }
            break;
        case 'f':
            {
            switch ( input.LA(2) ) {
            case 'a':
                {
                int LA6_20 = input.LA(3);

                if ( (LA6_20=='l') ) {
                    int LA6_25 = input.LA(4);

                    if ( (LA6_25=='s') ) {
                        int LA6_30 = input.LA(5);

                        if ( (LA6_30=='e') ) {
                            int LA6_35 = input.LA(6);

                            if ( ((LA6_35 >= '0' && LA6_35 <= '9')||(LA6_35 >= 'A' && LA6_35 <= 'Z')||LA6_35=='_'||(LA6_35 >= 'a' && LA6_35 <= 'z')) ) {
                                alt6=17;
                            }
                            else {
                                alt6=11;
                            }
                        }
                        else {
                            alt6=17;
                        }
                    }
                    else {
                        alt6=17;
                    }
                }
                else {
                    alt6=17;
                }
                }
                break;
            case 'o':
                {
                int LA6_21 = input.LA(3);

                if ( (LA6_21=='r') ) {
                    int LA6_26 = input.LA(4);

                    if ( (LA6_26=='a') ) {
                        int LA6_31 = input.LA(5);

                        if ( (LA6_31=='l') ) {
                            int LA6_36 = input.LA(6);

                            if ( (LA6_36=='l') ) {
                                int LA6_41 = input.LA(7);

                                if ( ((LA6_41 >= '0' && LA6_41 <= '9')||(LA6_41 >= 'A' && LA6_41 <= 'Z')||LA6_41=='_'||(LA6_41 >= 'a' && LA6_41 <= 'z')) ) {
                                    alt6=17;
                                }
                                else {
                                    alt6=12;
                                }
                            }
                            else {
                                alt6=17;
                            }
                        }
                        else {
                            alt6=17;
                        }
                    }
                    else {
                        alt6=17;
                    }
                }
                else {
                    alt6=17;
                }
                }
                break;
            default:
                alt6=17;
            }

            }
            break;
        case 'h':
            {
            int LA6_12 = input.LA(2);

            if ( (LA6_12=='o') ) {
                int LA6_22 = input.LA(3);

                if ( (LA6_22=='l') ) {
                    int LA6_27 = input.LA(4);

                    if ( (LA6_27=='d') ) {
                        int LA6_32 = input.LA(5);

                        if ( (LA6_32=='s') ) {
                            int LA6_37 = input.LA(6);

                            if ( ((LA6_37 >= '0' && LA6_37 <= '9')||(LA6_37 >= 'A' && LA6_37 <= 'Z')||LA6_37=='_'||(LA6_37 >= 'a' && LA6_37 <= 'z')) ) {
                                alt6=17;
                            }
                            else {
                                alt6=13;
                            }
                        }
                        else {
                            alt6=17;
                        }
                    }
                    else {
                        alt6=17;
                    }
                }
                else {
                    alt6=17;
                }
            }
            else {
                alt6=17;
            }
            }
            break;
        case 't':
            {
            int LA6_13 = input.LA(2);

            if ( (LA6_13=='r') ) {
                int LA6_23 = input.LA(3);

                if ( (LA6_23=='u') ) {
                    int LA6_28 = input.LA(4);

                    if ( (LA6_28=='e') ) {
                        int LA6_33 = input.LA(5);

                        if ( ((LA6_33 >= '0' && LA6_33 <= '9')||(LA6_33 >= 'A' && LA6_33 <= 'Z')||LA6_33=='_'||(LA6_33 >= 'a' && LA6_33 <= 'z')) ) {
                            alt6=17;
                        }
                        else {
                            alt6=14;
                        }
                    }
                    else {
                        alt6=17;
                    }
                }
                else {
                    alt6=17;
                }
            }
            else {
                alt6=17;
            }
            }
            break;
        case '|':
            {
            alt6=15;
            }
            break;
        case '~':
            {
            alt6=16;
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'g':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt6=17;
            }
            break;
        case '/':
            {
            alt6=18;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt6=19;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 6, 0, input);

            throw nvae;

        }

        switch (alt6) {
            case 1 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:10: T__7
                {
                mT__7(); 


                }
                break;
            case 2 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:15: T__8
                {
                mT__8(); 


                }
                break;
            case 3 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:20: T__9
                {
                mT__9(); 


                }
                break;
            case 4 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:25: T__10
                {
                mT__10(); 


                }
                break;
            case 5 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:31: T__11
                {
                mT__11(); 


                }
                break;
            case 6 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:37: T__12
                {
                mT__12(); 


                }
                break;
            case 7 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:43: T__13
                {
                mT__13(); 


                }
                break;
            case 8 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:49: T__14
                {
                mT__14(); 


                }
                break;
            case 9 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:55: T__15
                {
                mT__15(); 


                }
                break;
            case 10 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:61: T__16
                {
                mT__16(); 


                }
                break;
            case 11 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:67: T__17
                {
                mT__17(); 


                }
                break;
            case 12 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:73: T__18
                {
                mT__18(); 


                }
                break;
            case 13 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:79: T__19
                {
                mT__19(); 


                }
                break;
            case 14 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:85: T__20
                {
                mT__20(); 


                }
                break;
            case 15 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:91: T__21
                {
                mT__21(); 


                }
                break;
            case 16 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:97: T__22
                {
                mT__22(); 


                }
                break;
            case 17 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:103: ID
                {
                mID(); 


                }
                break;
            case 18 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:106: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 19 :
                // /home/dmitry/work/svnwork/MetTeL2/trunk/src/mettel/fo/grammar/MettelFO.g:1:114: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}