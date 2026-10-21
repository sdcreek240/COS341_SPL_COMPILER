/* begin of the non-executable code-mine for later inlining */
/* end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;

L0: ; /* until */
int vN0;
vN0 = v1;
int vN1;
vN1 = 3;
if ( vN0 == vN1 ) goto L1;
L2: ; /* do */
/* nop */
/* end */
goto L0;
L1: ;
/* exit */
/* end */
return 0;
}
/* end of executable C */
