<?php

if (isset($_GET ['n'])) {
    $n = $_GET ['n'];

    echo "<pre>// Specification of modal logic KEn discussed with michal
specification KEn;

syntax KEn{
	sort formula;

	// to express pi
	sort nominal; 
	formula singleton = '{' nominal '}';
	formula at = '@' nominal formula;
	nominal f = 'f' '('  nominal ',' formula  ')'; //Skolem function
	//for rules which generate more than one nominal\n";

    for ($i = 1; $i <= $n; $i++) {
        echo "        nominal f" . $i . " = 'f" . $i . "' '('  nominal ',' formula  ')';\n";
    }

    echo "        formula true = 'true';
	formula false = 'false';
	formula	negation = '~' formula;
	formula	conjunction  = formula '&' formula;
	formula	disjuntion  = formula '|' formula;
	formula diamond = '<>' formula;\n";

    for ($i = 0; $i <= $n - 1; $i++) {
        echo "        formula existl" . $i . " = 'E<" . $i . "' formula;\n";
        echo "        formula existe" . $i . " = 'E=" . $i . "' formula;\n";
        echo "        formula existg" . $i . " = 'E>" . $i . "' formula;\n";
    }

    echo " 
//	formula equivalence = formula  '<->' formula; //Testing some features
//	formula equality = '[' nominal '=' nominal ']';
//	nominal f = 'f' '('  nominal ',' formula  ')'; //Skolem function
}</pre><br/><hr/><br/>";


    echo "<pre>//Tableau calculus
@l (~(~P)) / @l P $;
@l (P&Q) / @l P @l Q $;
@l (~(P&Q)) / @l (~P) $| @l (~Q) $;
//@l (<>P) / @l (<>{f(l,P)}) @f(l,P) P $;
@l (~(<>P)) @l (<>{l2}) / @l2 (~P) $;\n";


    //Generate E>
    for ($i = 0; $i <= $n - 1; $i++) {
        echo "       @l ( E>" . $i . " P)/";

        for ($j = 1; $j <= $i + 1; $j++) {
            echo "@f" . $j . "(l,P) P ";

            for ($k = 1; $k <= $i + 1; $k++) {
                if ($j < $k) {
                    echo "@f" . $j . "(l,P) (~({f" . $k . "(l,P)})) ";
                }
            }
        }

        echo "$;\n";
    };

    //Generate ~E
    for ($i = 0; $i <= $n - 1; $i++) {
        echo "       @l (~( E>" . $i . " P)) ";

        for ($j = 1; $j <= $i + 1; $j++) {
            echo "@l" . $j . " P ";
        }

        echo " / ";
        $omitLastLoop = "";
        for ($j = 1; $j <= $i + 1; $j++) {
            for ($k = $j + 1; $k <= $i + 1; $k++) {
                $omitLastLoop = $omitLastLoop . " @l" . $j . " ({l" . $k . "}) $|";
            }
        }
        $omitLastLoop = substr($omitLastLoop, 0, -3); //strlen($omitLastLoop))
        echo $omitLastLoop . "$;\n";
    };


    //Generate E< 
    for ($i = 1; $i <= $n - 1; $i++) {
       echo "       @l (E<".$i. " P) / @l (~(E>".($i - 1) . " P)) $;\n";
    };

    //Generate E= 
    for ($i = 0; $i <= ($n - 1); $i++) {
        if ($i == 0) {
            echo "       @l (E=" . $i . " P) / @l (~(E>" . $i . " P)) $;\n";
        }else if ($i== ($n-1)){
            echo "       @l (E=". $i ." P) / @l ((~(E>". $i ." P))&(E>". ($i-1) ." P)) $;\n";
        }else {
            echo "       @l (E=". $i ." P) / @l ((E<". ($i+1) ." P)&(E>". ($i-1) ." P)) $;\n";
        }

    };

    echo "
//equality rules
@l{l2} / @l2{l} $;
@l (~{l2}) / @l2{l2} $;

@l P / @l{l} $;

@l (<>{l2}) / @l2{l2} $;
@l P  @l{l2} /  @l2 P $;
@l (<>{l2})  @l2{l3} / @l (<>{l3}) $;


// Closure rule
@l P  @l (~P) /  $;
false / $;</pre>";
} else {
    echo "you need to provide n";
}
?>
