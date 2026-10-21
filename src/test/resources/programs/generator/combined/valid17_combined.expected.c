int vN0;
vN0 = 0;
v1 = vN0;
L0: ; /* until */
int vN1;
vN1 = v1;
int vN2;
vN2 = 2;
if ( vN1 < vN2 ) goto L2;
goto L1;
L2: ; /* do */
/* if */
int vN3;
vN3 = v1;
int vN4;
vN4 = 0;
if ( vN3 == vN4 ) goto L3;
L4: ; /* else */
int vN5;
vN5 = v1;
std::cout << vN5;
/* end */
goto L5;
L3: ; /* then */
std::cout << "zero";
/* end */
L5: ; /* continue */
int vN6;
int vN7;
vN7 = v1;
int vN8;
vN8 = 1;
vN6 = vN7 + vN8;
v1 = vN6;
/* end */
goto L0;
L1: ;
/* exit */
/* end */