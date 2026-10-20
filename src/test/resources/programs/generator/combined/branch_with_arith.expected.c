float vN0;
vN0 = 0;
v1 = vN0;
L0: /* until */
float vN1;
vN1 = v1;
float vN2;
vN2 = 3;
if ( vN1 < vN2 ) goto L2;
goto L1;
L2: /* do */
/* if */
float vN3;
float vN4;
vN4 = v1;
float vN5;
vN5 = 1;
vN3 = vN4 + vN5;
float vN6;
vN6 = 2;
if ( vN3 > vN6 ) goto L3;
L4: /* else */
float vN7;
vN7 = v1;
std::cout << vN7;
/* end */
goto L5;
L3: /* then */
std::cout << "big";
/* end */
L5: /* continue */
float vN8;
float vN9;
vN9 = v1;
float vN10;
vN10 = 1;
vN8 = vN9 + vN10;
v1 = vN8;
/* end */
goto L0;
L1:
/* exit */
/* end */