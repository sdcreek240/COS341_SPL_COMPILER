/* begin of the non-executable code-mine for later inlining

end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;
int v2 ;

int vN0;
vN0 = 0;
v1 = vN0;
L0: ; /* until */
int vN1;
vN1 = v1;
int vN2;
vN2 = 2;
if ( vN1 < vN2 ) goto L2;
goto L1;
L2: ; /* do */
int vN3;
vN3 = 0;
v2 = vN3;
L3: ; /* do */
int vN4;
int vN5;
vN5 = v2;
int vN6;
vN6 = 1;
vN4 = vN5 + vN6;
v2 = vN4;
/* end */
/* while */
int vN7;
vN7 = v2;
int vN8;
vN8 = 2;
if ( vN7 < vN8 ) goto L3;
L4: ; /* exit */
int vN9;
int vN10;
vN10 = v1;
int vN11;
vN11 = 1;
vN9 = vN10 + vN11;
v1 = vN9;
/* end */
goto L0;
L1: ;
/* exit */
/* end */
return 0;
}
/* end of executable C */
