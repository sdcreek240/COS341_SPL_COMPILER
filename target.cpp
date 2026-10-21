/* begin of the non-executable code-mine for later inlining */
/* end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;
int v2 ;

int vN0;
vN0 = 3;
v1 = vN0;
int vN1;
int vN2;
vN2 = v1;
int vN3;
vN3 = 1;
vN1 = vN2 + vN3;
v2 = vN1;
int vN4;
vN4 = v2;
std::cout << vN4;
/* end */
return 0;
}
/* end of executable C */
