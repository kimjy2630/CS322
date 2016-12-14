grammar RegularExpr;
expr
	: closure
	| union
	| union_term
	| concat
	;

closure
	: union '*'		#closure_union
	| Id '*'		#closure_id
	;

union
	: '(' union_term ')'
	;

union_term
	: concat '+' union_term		#union_rec
	| concat					#union_base
	;

concat
	: (string | closure | union) concat		#concat_rec
	| (string | closure | union)			#concat_base
	;

string
	: (Id)+
	;

Id
	: [a-z]
	| [0-9]
	;

E
	: '(' ')'
	;
	
WS : [ \t\r\n]+ -> skip ;