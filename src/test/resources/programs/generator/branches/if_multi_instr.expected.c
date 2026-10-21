/* if */
int vN0;
vN0 = v1;
int vN1;
vN1 = 1;
if ( vN0 == vN1 ) goto L0;
L1: ; /* else */
std::cout << "c";
std::cout << "d";
/* end */
goto L2;
L0: ; /* then */
std::cout << "a";
std::cout << "b";
/* end */
L2: ; /* continue */
/* end */