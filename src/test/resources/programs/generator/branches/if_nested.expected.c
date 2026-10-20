/* if */
float vN0;
vN0 = v1;
float vN1;
vN1 = 1;
if ( vN0 == vN1 ) goto L0;
L1: /* else */
std::cout << "c";
/* end */
goto L2;
L0: /* then */
/* if */
float vN2;
vN2 = v1;
float vN3;
vN3 = 2;
if ( vN2 == vN3 ) goto L3;
L4: /* else */
std::cout << "b";
/* end */
goto L5;
L3: /* then */
std::cout << "a";
/* end */
L5: /* continue */
/* end */
L2: /* continue */
/* end */