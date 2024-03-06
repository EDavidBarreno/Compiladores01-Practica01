package org.zafkiel.backend;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r, \n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Comillas */
( "\"" ) {return new Symbol(sym.Comillas, yychar, yyline, yytext());}

/* Tipos de datos */
( byte | int | char | long | float | double ) {return new Symbol(sym.T_dato, yychar, yyline, yytext());}

/* Tipo de dato String */
( String ) {return new Symbol(sym.Cadena, yychar, yyline, yytext());}

/* Palabra reservada SELECCIONAR */
( SELECCIONAR ) {return new Symbol(sym.SELECCIONAR, yychar, yyline, yytext());}

/* Palabra reservada FILTRAR */
( FILTRAR ) {return new Symbol(sym.FILTRAR, yychar, yyline, yytext());}

/* Palabra reservada INSERTAR */
( INSERTAR ) {return new Symbol(sym.INSERTAR, yychar, yyline, yytext());}

/* Palabra reservada ACTUALIZAR */
( ACTUALIZAR ) {return new Symbol(sym.ACTUALIZAR, yychar, yyline, yytext());}

/* Palabra reservada ASIGNAR */
( ASIGNAR ) {return new Symbol(sym.ASIGNAR, yychar, yyline, yytext());}

/* Palabra reservada ELIMINAR */
( ELIMINAR ) {return new Symbol(sym.ELIMINAR, yychar, yyline, yytext());}

/* Palabra reservada EN */
( EN ) {return new Symbol(sym.EN, yychar, yyline, yytext());}

/* Operador Igual */
( "=" ) {return new Symbol(sym.Igual, yychar, yyline, yytext());}

/* Operador Suma */
( "+" ) {return new Symbol(sym.Suma, yychar, yyline, yytext());}

/* Operador Resta */
( "-" ) {return new Symbol(sym.Resta, yychar, yyline, yytext());}

/* Operador Multiplicacion */
( "*" ) {return new Symbol(sym.Multiplicacion, yychar, yyline, yytext());}

/* Operador Division */
( "/" ) {return new Symbol(sym.Division, yychar, yyline, yytext());}

/* Operadores logicos */
( "AND" | "OR" ) {return new Symbol(sym.Op_Logico, yychar, yyline, yytext());}

/*Operadores Relacionales */
( "==" | "!=" | ">=" | "<=" | "<<" | ">>" | "<>" ) {return new Symbol(sym.Op_relacional, yychar, yyline, yytext());}

/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" ) {return new Symbol(sym.Op_atribucion, yychar, yyline, yytext());}

/* Operadores Incremento y decremento */
( "++" | "--" ) {return new Symbol(sym.Op_incremento, yychar, yyline, yytext());}

/* MayorQue de apertura */
( "<" ) {return new Symbol(sym.MayorQue, yychar, yyline, yytext());}

/* MenorQue de cierre */
( ">" ) {return new Symbol(sym.MenorQue, yychar, yyline, yytext());}

/* Parentesis de apertura */
( "(" ) {return new Symbol(sym.Parentesis_a, yychar, yyline, yytext());}

/* Parentesis de cierre */
( ")" ) {return new Symbol(sym.Parentesis_c, yychar, yyline, yytext());}

/* Llave de apertura */
( "{" ) {return new Symbol(sym.Llave_a, yychar, yyline, yytext());}

/* Llave de cierre */
( "}" ) {return new Symbol(sym.Llave_c, yychar, yyline, yytext());}

/* Corchete de apertura */
( "[" ) {return new Symbol(sym.Corchete_a, yychar, yyline, yytext());}

/* Corchete de cierre */
( "]" ) {return new Symbol(sym.Corchete_c, yychar, yyline, yytext());}

/* Punto y coma */
( ";" ) {return new Symbol(sym.P_coma, yychar, yyline, yytext());}

/* Identificador */
{L}({L}|{D})* {return new Symbol(sym.Identificador, yychar, yyline, yytext());}

/* Numero */
("(-"{D}+")")|{D}+ {return new Symbol(sym.Numero, yychar, yyline, yytext());}

/* Error de analisis */
 . {return ERROR;}
