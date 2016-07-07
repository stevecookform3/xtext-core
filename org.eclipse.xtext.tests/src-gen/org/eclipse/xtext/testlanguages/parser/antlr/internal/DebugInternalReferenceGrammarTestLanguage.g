/*
 * generated by Xtext
 */
grammar DebugInternalReferenceGrammarTestLanguage ;

// Rule Spielplatz
ruleSpielplatz :
	(
		'spielplatz' RULE_INT RULE_STRING? '{' (
			ruleKind |
			ruleErwachsener |
			ruleSpielzeug |
			ruleFamilie
		)* '}'
	)?
;

// Rule Kind
ruleKind :
	'kind' '(' RULE_ID RULE_INT ')'
;

// Rule Erwachsener
ruleErwachsener :
	'erwachsener' '(' RULE_ID RULE_INT ')'
;

// Rule Spielzeug
ruleSpielzeug :
	'spielzeug' '(' RULE_ID ruleFarbe ')'
;

// Rule Farbe
ruleFarbe :
	'ROT' |
	'BLAU' |
	'GELB' |
	'GR\u00DCN'
;

// Rule Familie
ruleFamilie :
	'familie' '(' (
		'keyword' |
		RULE_STRING |
		RULE_ID
	) RULE_ID RULE_ID RULE_ID (
		',' RULE_ID
	)* ')'
;

RULE_ID :
	'^'? (
		'a' .. 'z' |
		'A' .. 'Z' |
		'_'
	) (
		'a' .. 'z' |
		'A' .. 'Z' |
		'_' |
		'0' .. '9'
	)*
;

RULE_INT :
	'0' .. '9'+
;

RULE_STRING :
	'"' (
		'\\' . |
		~ (
			'\\' |
			'"'
		)
	)* '"' |
	'\'' (
		'\\' . |
		~ (
			'\\' |
			'\''
		)
	)* '\''
;

RULE_ML_COMMENT :
	'/*' (
		options { greedy = false ; } : .
	)* '*/' { skip(); }
;

RULE_SL_COMMENT :
	'//' ~ (
		'\n' |
		'\r'
	)* (
		'\r'? '\n'
	)? { skip(); }
;

RULE_WS :
	(
		' ' |
		'\t' |
		'\r' |
		'\n'
	)+ { skip(); }
;

RULE_ANY_OTHER :
	.
;