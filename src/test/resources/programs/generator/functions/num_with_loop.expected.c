/* begin of the non-executable code-mine for later inlining
begin of code-mine for Function f1
Parameters for inlining:
int v2 ;
 
Body of the procedure:
int v3 ;
 
int vN0;
vN0 = 0;
v3 = vN0;
L0: ; (* until *)
int vN1;
vN1 = v3;
int vN2;
vN2 = v2;
if ( vN1 < vN2 ) goto L2;
goto L1;
L2: ; (* do *)
int vN3;
int vN4;
vN4 = v3;
int vN5;
vN5 = 1;
vN3 = vN4 + vN5;
v3 = vN3;
(* end *)
goto L0;
L1: ;
(* exit *)
(* end *)
f1vN6 = v3;
end of code-mine for Function f1
The result is found in the return value f1vN6
end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;

int vN7;
int vN8;
vN8 = 3;
{ /* inlined call of f1 */
int v2 ;
v2 = vN8 ;
int v3 ;
int vN9;
vN9 = 0;
v3 = vN9;
L3: ; /* until */
int vN10;
vN10 = v3;
int vN11;
vN11 = v2;
if ( vN10 < vN11 ) goto L5;
goto L4;
L5: ; /* do */
int vN12;
int vN13;
vN13 = v3;
int vN14;
vN14 = 1;
vN12 = vN13 + vN14;
v3 = vN12;
/* end */
goto L3;
L4: ;
/* exit */
/* end */
int f1vN15 ;
f1vN15 = v3;
vN7 = f1vN15 ;
}
v1 = vN7;
int vN16;
vN16 = v1;
std::cout << vN16;
/* end */
return 0;
}
/* end of executable C */
