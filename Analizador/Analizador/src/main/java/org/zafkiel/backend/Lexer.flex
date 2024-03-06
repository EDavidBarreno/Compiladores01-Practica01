package org.zafkiel.backend;
import static org.zafkiel.backend.Tokens.*;
%%
%class Lexer
%type Tokens
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r]+
%{
    public String lexeme;
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Salto de linea */
( "\n" ) {return Linea;}

/* Comillas */
( "\"" ) {lexeme=yytext(); return Comillas;}

/* Tipos de datos */
( byte | int | char | long | float | double ) {lexeme=yytext(); return T_dato;}

/* Tipo de dato String */
( String ) {lexeme=yytext(); return Cadena;}

/* Palabra reservada SELECCIONAR */
( SELECCIONAR ) {lexeme=yytext(); return SELECCIONAR;}

/* Palabra reservada FILTRAR */
( FILTRAR ) {lexeme=yytext(); return FILTRAR;}

/* Palabra reservada INSERTAR */
( INSERTAR ) {lexeme=yytext(); return INSERTAR;}

/* Palabra reservada ACTUALIZAR */
( ACTUALIZAR ) {lexeme=yytext(); return ACTUALIZAR;}

/* Palabra reservada ASIGNAR */
( ASIGNAR ) {lexeme=yytext(); return ASIGNAR;}

/* Palabra reservada ELIMINAR */
( ELIMINAR ) {lexeme=yytext(); return ELIMINAR;}

/* Palabra reservada EN */
( EN ) {lexeme=yytext(); return EN;}

/* Operador Igual */
( "=" ) {lexeme=yytext(); return Igual;}

/* Operador Suma */
( "+" ) {lexeme=yytext(); return Suma;}

/* Operador Resta */
( "-" ) {lexeme=yytext(); return Resta;}

/* Operador Multiplicacion */
( "*" ) {lexeme=yytext(); return Multiplicacion;}

/* Operador Division */
( "/" ) {lexeme=yytext(); return Division;}

/* Operadores logicos */
( "AND" | "OR" ) {lexeme=yytext(); return Op_logico;}

/*Operadores Relacionales */
( "==" | "!=" | ">=" | "<=" | "<<" | ">>" | "<>" ) {lexeme = yytext(); return Op_relacional;}

/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" ) {lexeme = yytext(); return Op_atribucion;}

/* Operadores Incremento y decremento */
( "++" | "--" ) {lexeme = yytext(); return Op_incremento;}

/* MayorQue de apertura */
( "<" ) {lexeme=yytext(); return MayorQue;}

/* MenorQue de cierre */
( ">" ) {lexeme=yytext(); return MenorQue;}

/* Parentesis de apertura */
( "(" ) {lexeme=yytext(); return Parentesis_a;}

/* Parentesis de cierre */
( ")" ) {lexeme=yytext(); return Parentesis_c;}

/* Llave de apertura */
( "{" ) {lexeme=yytext(); return Llave_a;}

/* Llave de cierre */
( "}" ) {lexeme=yytext(); return Llave_c;}

/* Corchete de apertura */
( "[" ) {lexeme = yytext(); return Corchete_a;}

/* Corchete de cierre */
( "]" ) {lexeme = yytext(); return Corchete_c;}

/* Punto y coma */
( ";" ) {lexeme=yytext(); return P_coma;}

/* Identificador */
{L}({L}|{D})* {lexeme=yytext(); return Identificador;}

/* Numero */
("(-"{D}+")")|{D}+ {lexeme=yytext(); return Numero;}

/* Error de analisis */
 . {return ERROR;}
