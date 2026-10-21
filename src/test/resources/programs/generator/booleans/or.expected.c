/* if */
int vN0;
vN0 = v1;
int vN1;
vN1 = 1;
if ( vN0 == vN1 ) goto L0;
goto L3;
L3: ;
int vN2;
vN2 = v1;
int vN3;
vN3 = 2;
if ( vN2 == vN3 ) goto L0;
goto L1;
L1: ; /* else */
std::cout << "no";
/* end */
goto L2;
L0: ; /* then */
std::cout << "yes";
/* end */
L2: ; /* continue */
/* end */