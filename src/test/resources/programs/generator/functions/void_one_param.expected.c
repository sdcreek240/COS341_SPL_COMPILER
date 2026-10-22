/* begin of the non-executable code-mine for later inlining
begin of code-mine for void-procedure f1
Parameters for inlining:
int v2 ;
 
Body of the procedure:
 
int vN0;
vN0 = v2;
std::cout << vN0;
(* end *)
end of code-mine for void-procedure f1
end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;

int vN1;
vN1 = 7;
v1 = vN1;
int vN2;
vN2 = v1;
{ /* inlined call of f1 */
int v2 ;
v2 = vN2 ;
int vN3;
vN3 = v2;
std::cout << vN3;
/* end */
}
/* end */
return 0;
}
/* end of executable C */
