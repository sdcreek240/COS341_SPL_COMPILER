/* begin of the non-executable code-mine for later inlining

end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;

/* if */
int vN0;
vN0 = v1;
int vN1;
vN1 = 1;
if ( vN0 == vN1 ) goto L0;
L1: ; /* else */
std::cout << "c";
/* end */
goto L2;
L0: ; /* then */
/* if */
int vN2;
vN2 = v1;
int vN3;
vN3 = 2;
if ( vN2 == vN3 ) goto L3;
L4: ; /* else */
std::cout << "b";
/* end */
goto L5;
L3: ; /* then */
std::cout << "a";
/* end */
L5: ; /* continue */
/* end */
L2: ; /* continue */
/* end */
return 0;
}
/* end of executable C */
