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
	formula equivalence = formula  '<->' formula; //Testing some features
	formula equality = '[' nominal '=' nominal ']';
//	nominal f = 'f' '('  nominal ',' formula  ')'; //Skolem function
}</pre><br/><hr/><br/>";


    echo "<pre>//Tableau calculus
@l (~(~P)) / @l P priority 1$;
@l (P&Q) / @l P @l Q priority 1$;
@l (~(P&Q)) / @l (~P) $| @l (~Q) priority 3$;

@l (<>P) / @l (<>{f(l,P)}) @f(l,P) P priority 8$;

@l (~(<>P)) @l (<>{l2}) / @l2 (~P) priority 2$;\n";


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

        echo " priority 8$;\n";
    };

    //Generate ~ E>
    for ($i = 0; $i <= $n - 1; $i++) {
        echo "       @l (~( E>" . $i . " P)) ";

        for ($j = 1; $j <= $i + 1; $j++) {
            echo "@l" . $j . " ({l" . $j ."}) ";
        }

        echo " / ";
        
        $omitLastLoop = "";
        
        for ($j = 1; $j <= $i + 1; $j++) {
            $omitLastLoop =$omitLastLoop . "@l" . $j . " (~ P) $| ";
        }
        
        if ($i == 0){
            $omitLastLoop = substr($omitLastLoop, 0, -3);
        }
        echo $omitLastLoop;
        
        $omitLastLoop = "";
        for ($j = 1; $j <= $i + 1; $j++) {
            for ($k = $j + 1; $k <= $i + 1; $k++) {
                $omitLastLoop = $omitLastLoop . " @l" . $j . " ({l" . $k . "}) $|";
            }
        }
        $omitLastLoop = substr($omitLastLoop, 0, -3); //strlen($omitLastLoop))
        echo $omitLastLoop . " priority 2$;\n";
    };


    //Generate E< 
    for ($i = 1; $i <= $n - 1; $i++) {
       echo "       @l (E<".$i. " P) / @l (~(E>".($i - 1) . " P)) priority 1$;\n";
    };
    
    
    //Generate ~E< 
    for ($i = 1; $i <= $n - 1; $i++) {
       echo "       @l (~(E<".$i. " P)) / @l (E>".($i - 1) . " P) priority 1$;\n";
    };

    //Generate E= 
    for ($i = 0; $i <= ($n - 1); $i++) {
        if ($i == 0) {
            echo "       @l (E=" . $i . " P) / @l (~(E>" . $i . " P)) priority 1$;\n";
        }else if ($i== ($n-1)){
            echo "       @l (E=". $i ." P) / @l ((~(E>". $i ." P))&(E>". ($i-1) ." P)) priority 1$;\n";
        }else {
            echo "       @l (E=". $i ." P) / @l ((E<". ($i+1) ." P)&(E>". ($i-1) ." P)) priority 1$;\n";
        }

    };
    
    
    //Generate ~E= 
    for ($i = 0; $i <= ($n - 1); $i++) {
        if ($i == 0) {
            echo "       @l (~(E=" . $i . " P)) / @l (E>" . $i . " P) priority 1$;\n";
        }else if ($i== ($n-1)){
            echo "       @l (~(E=". $i ." P)) / @l (~((~(E>". $i ." P))&(E>". ($i-1) ." P))) priority 1$;\n";
        }else {
            echo "       @l (~(E=". $i ." P)) / @l (~((E<". ($i+1) ." P)&(E>". ($i-1) ." P))) priority 1$;\n";
        }

    };

    echo "
//equality rules
@l{l2} / @l2{l} priority 1$;
@l (~{l2}) / @l2{l2} priority 1$;

@l P / @l{l} priority 1$;

@l (<>{l2}) / @l2{l2} priority 1$;
@l P  @l{l2} /  @l2 P priority 2$;
@l (<>{l2})  @l2{l3} / @l (<>{l3}) priority 2$;


// Closure rule
@l P  @l (~P) /  priority 0$;
false / priority 0$;



//Blocking related
@l{l0} / [l=l0] priority 1$;
[l=l0] / @l{l0} priority 1$;
@l (~{l0}) / (~([l=l0])) priority 1$;
(~([l=l0])) / @l (~{l0}) priority 1$;
@l{l} @l0{l0} / [l=l0] $| (~([l=l0])) priority 7$;
</pre>";
} else {
    echo "you need to provide n";
}
?>
