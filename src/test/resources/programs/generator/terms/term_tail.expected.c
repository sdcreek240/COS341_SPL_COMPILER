/* begin of the non-executable code-mine for later inlining */
/* end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;
int v2 ;

int vN0;
vN0 = 1;
v1 = vN0;
int vN1;
int vN2;
vN2 = v1;
int vN3;
vN3 = 2;
vN1 = vN2 + vN3;
v2 = vN1;
/* end */
return 0;
}
/* end of executable C */
