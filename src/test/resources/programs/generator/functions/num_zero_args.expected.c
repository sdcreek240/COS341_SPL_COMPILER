/* begin of the non-executable code-mine for later inlining
begin of code-mine for Function f1
Parameters for inlining:
 
Body of the procedure:
 
(* end *)
f1vN0 = 42;
end of code-mine for Function f1
The result is found in the return value f1vN0
end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;

int vN1;
{ /* inlined call of f1 */
/* end */
int f1vN2 ;
f1vN2 = 42;
vN1 = f1vN2 ;
}
v1 = vN1;
int vN3;
vN3 = v1;
std::cout << vN3;
/* end */
return 0;
}
/* end of executable C */
