/* begin of the non-executable code-mine for later inlining
begin of code-mine for Function f1
Parameters for inlining:
int v2 ;
int v3 ;
 
Body of the procedure:
 
(* end *)
int vN1;
vN1 = v2;
int vN2;
vN2 = v3;
f1vN0 = vN1 + vN2;
end of code-mine for Function f1
The result is found in the return value f1vN0
end of the non-executable code-mine */
/* begin of executable C code */
#include <iostream>
int main()
{
int v1 ;

int vN3;
int vN4;
vN4 = 3;
int vN5;
vN5 = 4;
{ /* inlined call of f1 */
int v2 ;
int v3 ;
v2 = vN4 ;
v3 = vN5 ;
/* end */
int f1vN6 ;
int vN7;
vN7 = v2;
int vN8;
vN8 = v3;
f1vN6 = vN7 + vN8;
vN3 = f1vN6 ;
}
v1 = vN3;
int vN9;
vN9 = v1;
std::cout << vN9;
/* end */
return 0;
}
/* end of executable C */
