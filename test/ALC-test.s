specification ALCO;

syntax ALCOsorts{
	sort individual, concept, role;
}

syntax ALCO extends ALCOsorts{
	concept singleton = '{' individual '}' |
			negation = '~' concept |
			conjunction = concept '&' concept |
			disjunction = concept '|' concept |
			implication = concept "->" concept|
			equivalence = concept "<->" concept|
			existentialRestriction = "exists"
									 role '.'
									 concept|
			universalRestriction = "forall"
									 role '.'
									 concept;
	role composition = role '.' role |
		 union = role '|' role |
		 converse = role '-'|
		 weirdRole = "weird(" role ',' individual ';' concept ')'|
		 = "empty" role '.' concept;									 									 
										 
}
