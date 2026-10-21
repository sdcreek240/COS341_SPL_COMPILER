/* begin of the non-executable code-mine for later inlining */
/* end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;

/* if */
int vN0;
vN0 = v1;
int vN1;
vN1 = 3;
if ( vN0 > vN1 ) goto L0;
L1: ; /* else */
std::cout << "no";
/* end */
goto L2;
L0: ; /* then */
std::cout << "yes";
/* end */
L2: ; /* continue */
/* end */
return 0;
}
/* end of executable C */
