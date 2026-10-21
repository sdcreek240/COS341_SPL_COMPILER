/* begin of the non-executable code-mine for later inlining

end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;

int vN0;
vN0 = 0;
v1 = vN0;
L0: ; /* until */
int vN1;
vN1 = v1;
int vN2;
vN2 = 3;
if ( vN1 < vN2 ) goto L2;
goto L1;
L2: ; /* do */
/* if */
int vN3;
int vN4;
vN4 = v1;
int vN5;
vN5 = 1;
vN3 = vN4 + vN5;
int vN6;
vN6 = 2;
if ( vN3 > vN6 ) goto L3;
L4: ; /* else */
int vN7;
vN7 = v1;
std::cout << vN7;
/* end */
goto L5;
L3: ; /* then */
std::cout << "big";
/* end */
L5: ; /* continue */
int vN8;
int vN9;
vN9 = v1;
int vN10;
vN10 = 1;
vN8 = vN9 + vN10;
v1 = vN8;
/* end */
goto L0;
L1: ;
/* exit */
/* end */
return 0;
}
/* end of executable C */
