// $ANTLR 3.2 Fedora release 15 (Rawhide) Tue Feb  8 02:02:23 UTC 2011 /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g 2012-02-22 12:43:43

package mettel.fo;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class MettelFOLexer extends Lexer {
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

    public MettelFOLexer() {;} 
    public MettelFOLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public MettelFOLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g"; }

    // $ANTLR start "T__7"
    public final void mT__7() throws RecognitionException {
        try {
            int _type = T__7;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:7:6: ( '<->' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:7:8: '<->'
            {
            match("<->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__7"

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:8:6: ( '->' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:8:8: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:9:6: ( '|' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:9:8: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:10:7: ( '&' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:10:9: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:11:7: ( 'exists' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:11:9: 'exists'
            {
            match("exists"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:12:7: ( 'forall' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:12:9: 'forall'
            {
            match("forall"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:13:7: ( '[' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:13:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:14:7: ( ']' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:14:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:15:7: ( '(' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:15:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:16:7: ( ')' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:16:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:17:7: ( '~' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:17:9: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:18:7: ( 'true' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:18:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:19:7: ( 'false' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:19:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:20:7: ( 'holds' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:20:9: 'holds'
            {
            match("holds"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:21:7: ( ',' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:21:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:22:7: ( '=' )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:22:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:190:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:190:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:190:31: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:194:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
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
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:194:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match("//"); 

                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:194:14: (~ ( '\\n' | '\\r' ) )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>='\u0000' && LA2_0<='\t')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='\uFFFF')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:194:14: ~ ( '\\n' | '\\r' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:194:28: ( '\\r' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\r') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:194:28: '\\r'
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
                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:195:9: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 

                    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:195:14: ( options {greedy=false; } : . )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0=='*') ) {
                            int LA4_1 = input.LA(2);

                            if ( (LA4_1=='/') ) {
                                alt4=2;
                            }
                            else if ( ((LA4_1>='\u0000' && LA4_1<='.')||(LA4_1>='0' && LA4_1<='\uFFFF')) ) {
                                alt4=1;
                            }


                        }
                        else if ( ((LA4_0>='\u0000' && LA4_0<=')')||(LA4_0>='+' && LA4_0<='\uFFFF')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:195:42: .
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
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:198:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:198:9: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:8: ( T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | ID | COMMENT | WS )
        int alt6=19;
        alt6 = dfa6.predict(input);
        switch (alt6) {
            case 1 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:10: T__7
                {
                mT__7(); 

                }
                break;
            case 2 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:15: T__8
                {
                mT__8(); 

                }
                break;
            case 3 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:20: T__9
                {
                mT__9(); 

                }
                break;
            case 4 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:25: T__10
                {
                mT__10(); 

                }
                break;
            case 5 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:31: T__11
                {
                mT__11(); 

                }
                break;
            case 6 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:37: T__12
                {
                mT__12(); 

                }
                break;
            case 7 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:43: T__13
                {
                mT__13(); 

                }
                break;
            case 8 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:49: T__14
                {
                mT__14(); 

                }
                break;
            case 9 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:55: T__15
                {
                mT__15(); 

                }
                break;
            case 10 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:61: T__16
                {
                mT__16(); 

                }
                break;
            case 11 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:67: T__17
                {
                mT__17(); 

                }
                break;
            case 12 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:73: T__18
                {
                mT__18(); 

                }
                break;
            case 13 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:79: T__19
                {
                mT__19(); 

                }
                break;
            case 14 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:85: T__20
                {
                mT__20(); 

                }
                break;
            case 15 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:91: T__21
                {
                mT__21(); 

                }
                break;
            case 16 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:97: T__22
                {
                mT__22(); 

                }
                break;
            case 17 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:103: ID
                {
                mID(); 

                }
                break;
            case 18 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:106: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 19 :
                // /home/dmitry/private/work/eclipse-workspace/MetTeL2/src/mettel/fo/grammar/MettelFO.g:1:114: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    static final String DFA6_eotS =
        "\5\uffff\2\20\5\uffff\2\20\5\uffff\15\20\1\45\3\20\1\51\1\uffff"+
        "\1\52\1\53\1\54\4\uffff";
    static final String DFA6_eofS =
        "\55\uffff";
    static final String DFA6_minS =
        "\1\11\4\uffff\1\170\1\141\5\uffff\1\162\1\157\5\uffff\1\151\1\162"+
        "\1\154\1\165\1\154\1\163\1\141\1\163\1\145\1\144\1\164\1\154\1\145"+
        "\1\60\2\163\1\154\1\60\1\uffff\3\60\4\uffff";
    static final String DFA6_maxS =
        "\1\176\4\uffff\1\170\1\157\5\uffff\1\162\1\157\5\uffff\1\151\1\162"+
        "\1\154\1\165\1\154\1\163\1\141\1\163\1\145\1\144\1\164\1\154\1\145"+
        "\1\172\2\163\1\154\1\172\1\uffff\3\172\4\uffff";
    static final String DFA6_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\2\uffff\1\7\1\10\1\11\1\12\1\13\2\uffff"+
        "\1\17\1\20\1\21\1\22\1\23\22\uffff\1\14\3\uffff\1\15\1\16\1\5\1"+
        "\6";
    static final String DFA6_specialS =
        "\55\uffff}>";
    static final String[] DFA6_transitionS = {
            "\2\22\2\uffff\1\22\22\uffff\1\22\5\uffff\1\4\1\uffff\1\11\1"+
            "\12\2\uffff\1\16\1\2\1\uffff\1\21\14\uffff\1\1\1\17\3\uffff"+
            "\32\20\1\7\1\uffff\1\10\1\uffff\1\20\1\uffff\4\20\1\5\1\6\1"+
            "\20\1\15\13\20\1\14\6\20\1\uffff\1\3\1\uffff\1\13",
            "",
            "",
            "",
            "",
            "\1\23",
            "\1\25\15\uffff\1\24",
            "",
            "",
            "",
            "",
            "",
            "\1\26",
            "\1\27",
            "",
            "",
            "",
            "",
            "",
            "\1\30",
            "\1\31",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\40",
            "\1\41",
            "\1\42",
            "\1\43",
            "\1\44",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\46",
            "\1\47",
            "\1\50",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | ID | COMMENT | WS );";
        }
    }
 

}